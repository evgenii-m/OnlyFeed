/**
 * 
 */
package org.push.takerss.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.takerss.model.entity.RssChannelEntity;
import org.push.takerss.model.service.IRssChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author push
 * 
 * When a method is annotated with @ModelAttribute, Spring runs it before each handler method and adds the return value to the model.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private static Logger logger = LogManager.getLogger(HomeController.class);
    private IRssChannelService rssChannelService;
    
    
    @Autowired
    public void setRssChannelService(IRssChannelService rssChannelService) {
        this.rssChannelService = rssChannelService;
    }
    
    
    @RequestMapping(method = GET)
    public String index(Model uiModel) {
        List<RssChannelEntity> rssChannelList = rssChannelService.getAll();
        uiModel.addAttribute("rssChannelList", rssChannelList);
        logger.info("Rss Channels count = " + rssChannelList.size());
        RssChannelEntity newRssChannel = new RssChannelEntity();
        uiModel.addAttribute("newRssChannel", newRssChannel);
        return "index";
    }
    
    
    @RequestMapping(method = POST)
    public String addRssChanel(RssChannelEntity rssChannel, BindingResult bindingResult, Model uiModel) {
        if (bindingResult.hasErrors()) {
            logger.error("Binding Error!");
        } else {
            logger.info("Binding Success!");
            rssChannelService.save(rssChannel);
        }
        
        return "index";

    }
}
