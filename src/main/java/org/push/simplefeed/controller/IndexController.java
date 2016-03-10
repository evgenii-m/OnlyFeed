/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedChannelEntity;
import org.push.simplefeed.model.service.IFeedChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author push
 * 
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private static Logger logger = LogManager.getLogger(IndexController.class);
    private IFeedChannelService feedChannelService;
    

    @Autowired
    public void setFeedChannelService(IFeedChannelService feedChannelService) {
        this.feedChannelService = feedChannelService;
    }
    
    
    @RequestMapping(method = GET)
    public String index(Model uiModel) {
        FeedChannelEntity feedChannelBlank = new FeedChannelEntity();
        uiModel.addAttribute("feedChannelBlank", feedChannelBlank);
        List<FeedChannelEntity> feedChannelList = feedChannelService.getAll();       
        uiModel.addAttribute("feedChannelList", feedChannelList);
        logger.info("Feed Channels count = " + feedChannelList.size());
        return "index";
    }
    
    @RequestMapping(method = POST)
    public String addFeedChannel(@ModelAttribute("feedChannelBlank") FeedChannelEntity feedChannel, 
            Model uiModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Failed to add new Feed Channel (" + feedChannel + ")");
        } else {
            feedChannelService.save(feedChannel);
        }
        return "redirect:/";
    }
    
}
