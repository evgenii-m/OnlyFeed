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


    @Autowired
    public void setFeedItemService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }


    @RequestMapping(method = GET)
    public String showFeed(Model uiModel) {
        List<FeedItemEntity> feedItemList = new ArrayList<>();
        feedSourceService.refreshAll();
        List<FeedSourceEntity> feedSourceList = feedSourceService.getAll();
        for (FeedSourceEntity feedSource : feedSourceList) {
            feedItemList.addAll(feedSource.getFeedItemList());
        }
        logger.debug("Feed items count: " + feedItemList.size());
        uiModel.addAttribute("feedItemList", feedItemList);
        return "feed";
    }

}
