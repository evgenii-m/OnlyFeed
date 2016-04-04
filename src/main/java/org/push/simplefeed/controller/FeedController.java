/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

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
    private List<FeedItemEntity> feedTabList;
    
    
    public FeedController() {
        feedTabList = new ArrayList<>();
    }
    
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
        
        if (feedTabList.size() == 0) {
            feedTabList.add(feedItemList.get(0));
            feedTabList.add(feedItemList.get(1));
        }
        uiModel.addAttribute("feedTabList", feedTabList);
        return "feed";
    }
    

    @RequestMapping(value = "/tab", method = POST)
    public String addFeedTab(Model uiModel, Long id) {
        if (id == null) {
            logger.error("feedItemId = null!");
            return null;
        }
        
        FeedItemEntity feedItem = feedItemService.findById(id);
        if (feedItem == null) {
            logger.error("Feed item not found (id=" + id + ")");
            return null;
        }
        
//        if (feedTabList.contains(feedItem)) {
//            logger.debug("Feed tab list already contain feed item (id=" + id + ")");
//            return null;
//        }
        
        feedTabList.add(feedItem);
        uiModel.addAttribute("feedTab", feedItem);
        logger.debug("Feed item added to feed tab list (id=" + id + ")");
        return "feed/tab";
    }
    
    
//    @RequestMapping(value = "/tab", method = GET)
//    public String showTabPanel(Model uiModel) {
//        
//        FeedItemEntity feedTab = feedItemService.findById(40l);
//        uiModel.addAttribute("feedTab", feedTab);
//        return "feed/tab";
//    }
    
    
//    @RequestMapping(value = "/tab", method = POST)
//    @ResponseBody
//    public FeedItemEntity addFeedTab(Long feedItemId) {
//        if (feedItemId == null) {
//            logger.error("feedItemId = null!");
//            return null;
//        }
//        
//        FeedItemEntity feedItem = feedItemService.findById(feedItemId);
//        if (feedItem == null) {
//            logger.error("Feed item not found (id=" + feedItemId + ")");
//            return null;
//        }
//        
//        if (feedTabList.contains(feedItem)) {
//            logger.info("Feed tab list already contain feed item (id=" + feedItemId + ")");
//            return feedItem;
//        }
//        
//        feedTabList.add(feedItem);
//        logger.debug("Feed item added to feed tab list (id=" + feedItemId + ")");
//        return feedItem;
//    }

}
