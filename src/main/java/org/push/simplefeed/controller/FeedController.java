/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IFeedItemService;
import org.push.simplefeed.model.service.IFeedSourceService;
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



    @RequestMapping(method = GET)
    public String showFeeds(Model uiModel, Principal principal) {        
        logger.debug("showFeed");
        UserEntity user = userService.findByEmail(principal.getName());
        feedSourceService.refresh(user.getFeedSources());
        List<FeedItemEntity> feedItems = feedItemService.findLatest(user.getFeedSources(), 6);
        uiModel.addAttribute("feedItems", feedItems);
        return "feed";
    }
    
    
    @RequestMapping(value = "/tab", method = GET)
    @ResponseBody
    public List<FeedItemEntity> getFeedTabs(Principal principal) {
        logger.debug("getFeedTabs");
        UserEntity user = userService.findByEmail(principal.getName());
        if (user == null) {
            logger.error("Invalid user (principal.name=" + principal.getName() + ")");
            return null;
        }
        
        List<FeedTabEntity> feedTabs = new ArrayList<>(user.getFeedTabs());
        Collections.sort(feedTabs);
        
        List<FeedItemEntity> feedItems = new ArrayList<>();
        for (FeedTabEntity feedTab : feedTabs) {
            feedItems.add(feedTab.getFeedItem());
        }
        return feedItems;
    }
    

    @RequestMapping(value = "/tab", method = POST)
    @ResponseBody
    public FeedItemEntity addFeedTab(Model uiModel, Long id, Principal principal) {
//        if (id == null) {
//            logger.error("id null");
//            return null;
//        }
//
//        UserEntity user = userService.findByEmail(principal.getName());
//        if (user == null) {
//            logger.error("Invalid user (principal.name=" + principal.getName() + ")");
//            return null;
//        }
//        
//        FeedItemEntity feedItem = feedItemService.findById(id);
//        if (feedItem == null) {
//            logger.error("Feed item not found (id=" + id + ")");
//            return null;
//        }
//        
//        List<FeedTabEntity> feedTabList = user.getFeedTabList();
//        for (FeedTabEntity feedTab : feedTabList) {
//            if (feedTab.getFeedItem().getId() == id) {
//                logger.debug("Feed tab list already contain feed item (id=" + id + ")");
//                return feedItem;
//            }
//        }
//
//        user.addFeedTab(new FeedTabEntity(feedItem));
//        logger.debug("Feed item added to tabs");
//        return feedItem;
        return null;
    }
    
    
    @RequestMapping(value = "/tab/{id}", method = GET)
    @ResponseBody
    public FeedItemEntity getFeedTab(@PathVariable Long id) {
//        logger.debug("Get feed tab (id=" + id + ")");
//        FeedItemEntity feedItem = feedItemService.findById(id);
//        if (feedItem == null) {
//            logger.error("Feed tab not found (id=" + id + ")");
//        }
//        return feedItem;
        return null;
    }

}
