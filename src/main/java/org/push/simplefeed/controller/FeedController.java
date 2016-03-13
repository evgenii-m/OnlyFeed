/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.repository.FeedSourceRepository;
import org.push.simplefeed.model.service.IFeedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author push
 *
 */
@Controller
@RequestMapping("/feed")
public class FeedController {
    private static Logger logger = LogManager.getLogger(FeedController.class);
    private IFeedItemService feedItemService;
    private FeedSourceRepository feedSourceRepository;

    
    @Autowired
    public void setFeedSourceRespository(FeedSourceRepository feedSourceRepository) {
        this.feedSourceRepository = feedSourceRepository;
    }
    
    @Autowired
    public void setFeedItemService(IFeedItemService feedItemService) {
        this.feedItemService = feedItemService;
    }
    
    
    @RequestMapping(method = GET)
    public String showFeed(Model uiModel) {
//        List<FeedItemEntity> feedItemList = feedItemService.getAll();
        FeedSourceEntity feedSource = feedSourceRepository.findByName("Хабрахабр / Интересные публикации");
        List<FeedItemEntity> feedItemList = feedItemService.getFromSource(feedSource);
        uiModel.addAttribute("feedItemList", feedItemList);
        return "feed";
    }
}
