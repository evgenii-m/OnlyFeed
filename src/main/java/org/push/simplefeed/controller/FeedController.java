/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
            logger.error("Feed source (feedSource.id=" + feedSourceId + ") not found for user (user.id=" + 
                    user.getId() + ")");
            return "redirect:/feed";
        }
    }
    
    
    @RequestMapping(value = "/tab", method = GET)
    @ResponseBody
    public List<FeedItemEntity> getFeedTabs(Principal principal) {
        logger.debug("getFeedTabs");
        UserEntity user = userService.findByEmail(principal.getName());
        List<FeedItemEntity> feedItems = new ArrayList<>();
        for (FeedTabEntity feedTab : user.getFeedTabs()) {
            feedItems.add(feedTab.getFeedItem());
        }
        return feedItems;
    }
    

    @RequestMapping(value = "/tab", method = POST)
    @ResponseBody
    public FeedItemEntity addFeedTab(Model uiModel, Long id, Principal principal) {
        logger.debug("addFeedTab");
        FeedItemEntity feedItem = feedItemService.findById(id);
        if (feedItem == null) {
            logger.error("Feed item not found (id=" + id + ")");
            return null;
        }
        UserEntity user = userService.findByEmail(principal.getName());
        for (FeedTabEntity feedTab : user.getFeedTabs()) {
            if (feedTab.getFeedItem().getId().equals(id)) {
                logger.debug("Feed tab list already contain feed item (id=" + id + ")");
                return feedItem;
            }
        }
        feedTabService.save(new FeedTabEntity(user, feedItem));
        logger.debug("Feed item (feedItem.id" + id + ") added to tabs");
        return feedItem;
    }
    
    
    @RequestMapping(value = "/tab/{id}", method = GET)
    @ResponseBody
    public FeedItemEntity getFeedTab(@PathVariable Long id, Principal principal) {
        logger.debug("Get feed tab (feedItem.id=" + id + ")");
        UserEntity user = userService.findByEmail(principal.getName());
        for (FeedTabEntity feedTab : user.getFeedTabs()) {
            if (feedTab.getFeedItem().getId().equals(id)) {
                return feedTab.getFeedItem();
            }
        }
        logger.error("Feed tab not found (feedItem.id=" + id + ")");
        return null;
    }
    
    
    @RequestMapping(value = "/tab/{id}", method = DELETE)
    @ResponseBody
    public boolean deleteFeedTab(@PathVariable Long id, Principal principal) {
        logger.debug("deleteFeedTab");
        UserEntity user = userService.findByEmail(principal.getName());
        logger.debug(user.getFeedTabs());
        for (FeedTabEntity feedTab : user.getFeedTabs()) {
            if (feedTab.getFeedItem().getId().equals(id)) {
                feedTabService.delete(feedTab.getId());
                logger.debug("Delete feed tab (feedItem.id=" + id + ")");
                return true;
            }
        }
        logger.error("Feed tab not found (feedItem.id=" + id + ")");
        return false;
    }

}
