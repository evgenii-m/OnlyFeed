/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.service.IFeedItemService;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    
    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }

    @Autowired
    public void setFeedItemService(IFeedItemService feedItemService) {
        this.feedItemService = feedItemService;
    }

    

    @RequestMapping(method = GET)
    public String showFeed(Model uiModel) {
        feedSourceService.refreshAll();
        List<FeedItemEntity> feedItemList = feedItemService.findLatest(10);
        logger.debug("Feed items count: " + feedItemList.size());
        uiModel.addAttribute("feedItemList", feedItemList);
        return "feed";
    }

}
