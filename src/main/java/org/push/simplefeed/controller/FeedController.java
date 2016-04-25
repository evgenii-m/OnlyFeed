/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.entity.types.*;
import org.push.simplefeed.model.service.FeedItemService;
import org.push.simplefeed.model.service.IFeedItemService;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.push.simplefeed.model.service.IFeedTabService;
import org.push.simplefeed.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author push
 * 
 */
@Controller
@RequestMapping("/feed")
public class FeedController {
    private static Logger logger = LogManager.getLogger(FeedController.class);
    private IFeedSourceService feedSourceService;
    private IFeedItemService feedItemService;
    private IUserService userService;
    private IFeedTabService feedTabService;
 

    
    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }

    @Autowired
    public void setFeedItemService(IFeedItemService feedItemService) {
        this.feedItemService = feedItemService;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    public void setFeedTabService(IFeedTabService feedTabService) {
        this.feedTabService = feedTabService;
    }

    
    private void setFeedSettings(Model uiModel, UserEntity user) {
        uiModel.addAttribute("feedViewType", user.getFeedViewType().ordinal());
        uiModel.addAttribute("feedSortingType", user.getFeedSortingType().ordinal());
        uiModel.addAttribute("feedFilterType", user.getFeedFilterType().ordinal());
        uiModel.addAttribute("pageSize", FeedItemService.DEFAULT_PAGE_SIZE);
    }


    @RequestMapping(method = GET)
    public String showFeed(Model uiModel, Principal principal) {        
        logger.debug("showFeed");
        UserEntity user = userService.findByEmail(principal.getName());
        setFeedSettings(uiModel, user);
        return "feed";
    }
    
    
    @RequestMapping(value = "/{feedSourceId}", method = GET)
    public String showFeedFromSource(@PathVariable Long feedSourceId, Model uiModel, Principal principal) {
        logger.debug("showFeedFromSource");
        UserEntity user = userService.findByEmail(principal.getName());
        FeedSourceEntity feedSource = feedSourceService.findById(feedSourceId);
        if ((feedSource == null) || (!feedSource.getUser().equals(user))) {
            logger.error("Feed source (feedSource.id=" + feedSourceId 
                    + ") not found for user (user.id=" + user.getId() + ")");
            return "redirect:/feed";
        }
        
        uiModel.addAttribute("currentFeedSource", feedSource);
        setFeedSettings(uiModel, user);
        return "feed";
    }
    
    
    @RequestMapping(value = "/page/{pageIndex}", method = GET)
    @ResponseBody
    public List<FeedItemEntity> getFeedPage(@PathVariable int pageIndex, Principal principal) {
        logger.debug("getFeedPage");
        UserEntity user = userService.findByEmail(principal.getName());
        List<FeedItemEntity> feedItems = feedItemService.findPage(user.getFeedSources(), pageIndex);
        return feedItems;
    }
    
    
    @RequestMapping(value = "/{feedSourceId}/page/{pageIndex}", method = GET)
    @ResponseBody
    public List<FeedItemEntity> getFeedPageFromSource(@PathVariable Long feedSourceId, 
            @PathVariable int pageIndex, Principal principal) {
        logger.debug("getFeedPageFromSource");
        UserEntity user = userService.findByEmail(principal.getName());
        FeedSourceEntity feedSource = feedSourceService.findById(feedSourceId);
        if ((feedSource == null) || (!feedSource.getUser().equals(user))) {
            logger.error("Feed source (feedSource.id=" + feedSourceId 
                    + ") not found for user (user.id=" + user.getId() + ")");
            return null;
        }
        List<FeedItemEntity> feedItems = feedItemService.findPage(feedSource, pageIndex);
        return feedItems;
    }

    
    @RequestMapping(value = "/refresh", method = POST)
    @ResponseBody
    public boolean refreshFeed(Principal principal) {
        logger.debug("refreshFeed");
        UserEntity user = userService.findByEmail(principal.getName());
        if (user.getFeedSources().isEmpty()) {
            logger.debug("User doesn't have feed sources, nothing refresh (user.id=" + user.getId() + ")");
            return true;
        }
        return feedSourceService.refresh(user.getFeedSources());
    }
    

    @RequestMapping(value = "/{feedSourceId}/refresh", method = POST)
    @ResponseBody
    public boolean refreshFeedFromSource(@PathVariable Long feedSourceId, Principal principal) {
        logger.debug("refreshFeedFromSource");
        UserEntity user = userService.findByEmail(principal.getName());
        FeedSourceEntity feedSource = feedSourceService.findById(feedSourceId);
        if ((feedSource == null) || (!feedSource.getUser().equals(user))) {
            logger.error("Feed source (feedSource.id=" + feedSourceId 
                    + ") not found for user (user.id=" + user.getId() + ")");
            return false;
        }
        return feedSourceService.refresh(feedSource);
    }


    @RequestMapping(value = "/item/{feedItemId}", method = GET)
    @ResponseBody
    public FeedItemEntity getFeedItem(@PathVariable Long feedItemId, Principal principal) {
        logger.debug("getFeedDetail (feedItemId=" + feedItemId + ")");
        UserEntity user = userService.findByEmail(principal.getName());
        FeedItemEntity feedItem = feedItemService.findById(feedItemId);
        if ((feedItem == null) || (!user.getFeedSources().contains(feedItem.getFeedSource()))) {
            logger.error("Feed item (feedItem.id=" + feedItemId 
                    + ") not found for user (user.id=" + user.getId() + ")");
            return null;
        }
        return feedItem;
    }
    
    
    @RequestMapping(value = "/settings/view", method = POST)
    @ResponseBody
    public boolean changeFeedViewType(Byte feedViewType, Principal principal) {
        logger.debug("changeFeedViewType (feedViewType=" + feedViewType + ")");
        if ((feedViewType == null) || (feedViewType >= FeedViewType.LENGTH)) {
            logger.error("Unavaliable FeedViewType");
            return false;
        }
        UserEntity user = userService.findByEmail(principal.getName());
        if (user.getFeedViewType().equals(FeedViewType.value(feedViewType))) {
            logger.info("FeedViewType not changed");
            return false;
        }
        user.setFeedViewType(FeedViewType.value(feedViewType));
        userService.save(user);
        return true;
    }
    
    @RequestMapping(value = "/settings/sorting", method = POST)
    @ResponseBody
    public boolean changeFeedSortingType(Byte feedSortingType, Principal principal) {
        logger.debug("changeFeedSortingType (feedSortingType=" + feedSortingType + ")");
        if ((feedSortingType == null) || (feedSortingType >= FeedSortingType.LENGTH)) {
            logger.error("Unavaliable FeedSortingType");
            return false;
        }
        UserEntity user = userService.findByEmail(principal.getName());
        user.setFeedSortingType(FeedSortingType.value(feedSortingType));
        userService.save(user);
        return true;
    }
    
    @RequestMapping(value = "/settings/filter", method = POST)
    @ResponseBody
    public boolean changeFeedFilterType(Byte feedFilterType, Principal principal) {
        logger.debug("changeFeedFilterType (feedFilterType=" + feedFilterType + ")");
        if ((feedFilterType == null) || (feedFilterType >= FeedFilterType.LENGTH)) {
            logger.error("Unavaliable FeedFilterType");
            return false;
        }
        UserEntity user = userService.findByEmail(principal.getName());
        user.setFeedFilterType(FeedFilterType.value(feedFilterType));
        userService.save(user);
        return true;
    }
    
    
    @RequestMapping(value = "/tab", method = GET)
    @ResponseBody
    public List<FeedItemEntity> getFeedTabs(Principal principal) {
        logger.debug("getFeedTabs");
        UserEntity user = userService.findByEmailAndLoadFeedTabs(principal.getName());
        // TODO: replace on CollectionUtils.collect with transformer (?)
        List<FeedItemEntity> feedItems = new ArrayList<>();
        for (FeedTabEntity feedTab : user.getFeedTabs()) {
            feedItems.add(feedTab.getFeedItem());
        }
        return feedItems;
    }
    

    @RequestMapping(value = "/tab", method = POST)
    @ResponseBody
    public FeedItemEntity addFeedTab(Long feedItemId, Principal principal) {
        logger.debug("addFeedTab");
        UserEntity user = userService.findByEmailAndLoadFeedTabs(principal.getName());
        FeedItemEntity feedItem = feedItemService.findById(feedItemId);
        if ((feedItem == null) || (!user.getFeedSources().contains(feedItem.getFeedSource()))) {
            logger.error("Feed item (feedItem.id=" + feedItemId 
                    + ") not found for user (user.id=" + user.getId() + ")");
            return null;
        }
        for (FeedTabEntity feedTab : user.getFeedTabs()) {
            if (feedTab.getFeedItem().equals(feedItem)) {
                logger.error("Feed item (feedItem.id=" + feedItemId 
                        + ") already contained on user tabs (user.id=" + user.getId() + ")");
                return null;
            }
        }
        feedTabService.save(new FeedTabEntity(user, feedItem));
        logger.debug(user.getFeedTabs().get(user.getFeedTabs().size()-1));
        return feedItem;
    }
    
    
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/tab/{feedItemId}", method = DELETE)
    @ResponseBody
    public boolean deleteFeedTab(@PathVariable final Long feedItemId, Principal principal) {
        logger.debug("deleteFeedTab (feedItemId=" + feedItemId + ")");
        UserEntity user = userService.findByEmailAndLoadFeedTabs(principal.getName());
        FeedTabEntity feedTab = CollectionUtils.find(user.getFeedTabs(), new Predicate<FeedTabEntity>() {
            @Override
            public boolean evaluate(FeedTabEntity feedTab) {
                return feedTab.getFeedItem().getId().equals(feedItemId);
            }
        });
        if (feedTab == null) {
            logger.error("Feed tab (feedItemId=" + feedItemId + ") not found for user (user.id=" 
                    + user.getId() + ")");
            return false;            
        }
        
        logger.debug(feedTab);
        feedTabService.delete(feedTab);
        return true;
    }
    
    
    @RequestMapping(value = "/tab/move", method = POST)
    @ResponseBody
    public boolean moveFeedTab(int tabOldIndex, int tabNewIndex, Principal principal) {
        logger.debug("moveFeedTab (tabOldIndex=" + tabOldIndex + ", tabNewIndex=" + tabNewIndex + ")");
        UserEntity user = userService.findByEmailAndLoadFeedTabs(principal.getName());
        if ((tabOldIndex == tabNewIndex) || (tabOldIndex < 0) || (tabNewIndex < 0) || 
                (tabOldIndex >= user.getFeedTabs().size()) || (tabNewIndex >= user.getFeedTabs().size())) {
            logger.error("Invalid indexes (tabOldIndex=" + tabOldIndex + ", tabNewIndex=" + tabNewIndex
                    + ", user.feedTabs.size=" + user.getFeedTabs().size() + ")");
            return false;
        }
        logger.debug(user.getFeedTabs().get(tabOldIndex));
        feedTabService.move(user.getFeedTabs().get(tabOldIndex), tabNewIndex, tabOldIndex);
        return true;
    }

}
