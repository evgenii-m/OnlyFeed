/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IFeedItemService;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.push.simplefeed.model.service.IFeedTabService;
import org.push.simplefeed.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author push
 *
 * TODO: add script for display brief description on feed item view
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



    @RequestMapping(method = GET)
    public String showFeeds(Model uiModel, Principal principal) {        
        logger.debug("showFeed");
        UserEntity user = userService.findByEmail(principal.getName());
        feedSourceService.refresh(user.getFeedSources());
        List<FeedItemEntity> feedItems = feedItemService.findLatest(user.getFeedSources(), 10);
        uiModel.addAttribute("feedItems", feedItems);
        return "feed";
    }
    
    
    @RequestMapping(value = "/{feedSourceId}", method = GET)
    public String showFeedsFromSource(Model uiModel, Principal principal, @PathVariable Long feedSourceId) {
        logger.debug("showFeedsFromSource");
        UserEntity user = userService.findByEmail(principal.getName());
        FeedSourceEntity feedSource = feedSourceService.findById(feedSourceId);
        if ((feedSource != null) && (feedSource.getUser().equals(user))) {
            feedSourceService.refresh(feedSource);
            List<FeedItemEntity> feedItems = feedItemService.findLatest(feedSource, 10);
            uiModel.addAttribute("feedItems", feedItems);
            return "feed";
        } else {
            logger.error("Feed source (feedSource.id=" + feedSourceId + ") not found for user (user.id=" 
                    + user.getId() + ")");
            return "redirect:/feed";
        }
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
        // TODO: add test for feedItem source
        FeedItemEntity feedItem = feedItemService.findById(feedItemId);
        if (feedItem == null) {
            logger.error("Feed item not found (feedItemId=" + feedItemId + ")");
            return null;
        }
        UserEntity user = userService.findByEmailAndLoadFeedTabs(principal.getName());
        feedTabService.save(new FeedTabEntity(user, feedItem));
        logger.debug(user.getFeedTabs().get(user.getFeedTabs().size()-1));
        return feedItem;
    }
    
    
    @RequestMapping(value = "/tab/{tabIndex}", method = GET)
    @ResponseBody
    public FeedItemEntity getFeedTab(@PathVariable int tabIndex, Principal principal) {
        logger.debug("deleteFeedTab (tabIndex=" + tabIndex + ")");
        UserEntity user = userService.findByEmailAndLoadFeedTabs(principal.getName());
        if ((tabIndex < 0) || (tabIndex >= user.getFeedTabs().size())) {
            logger.error("Invalid index (tabIndex=" + tabIndex + ", user.feedTabs.size=" 
                    + user.getFeedTabs().size() + ")");
            return null;
        }
        logger.debug(user.getFeedTabs().get(tabIndex));
        return user.getFeedTabs().get(tabIndex).getFeedItem();
    }
    
    
    @RequestMapping(value = "/tab/{tabIndex}", method = DELETE)
    @ResponseBody
    public boolean deleteFeedTab(@PathVariable int tabIndex, Principal principal) {
        logger.debug("deleteFeedTab (tabIndex=" + tabIndex + ")");
        UserEntity user = userService.findByEmailAndLoadFeedTabs(principal.getName());
        if ((tabIndex < 0) || (tabIndex >= user.getFeedTabs().size())) {
            logger.error("Invalid index (tabIndex=" + tabIndex + ", user.feedTabs.size=" 
                    + user.getFeedTabs().size() + ")");
            return false;
        }
        logger.debug(user.getFeedTabs().get(tabIndex));
        feedTabService.delete(user.getFeedTabs().get(tabIndex));
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
