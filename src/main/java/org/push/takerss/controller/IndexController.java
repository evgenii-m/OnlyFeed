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
import org.push.takerss.model.service.impl.RssChannelService;
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
    private IRssChannelService rssChannelService;
    

    @Autowired
    public void setRssChannelService(IRssChannelService rssChannelService) {
        this.rssChannelService = rssChannelService;
    }
    
    
    @RequestMapping(method = GET)
    public String index(Model uiModel) {
        RssChannelEntity rssChannelBlank = new RssChannelEntity();
        uiModel.addAttribute("rssChannelBlank", rssChannelBlank);
        List<RssChannelEntity> rssChannelList = rssChannelService.getAll();       
        uiModel.addAttribute("rssChannelList", rssChannelList);
        logger.info("Rss Channels count = " + rssChannelList.size());
        return "index";
    }
    
    @RequestMapping(method = POST)
    public String addRssChannel(@ModelAttribute("rssChannelBlank") RssChannelEntity rssChannel, 
            Model uiModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Failed to add new RSS channel (" + rssChannel + ")");
        } else {
            rssChannelService.save(rssChannel);
        }
        return "redirect:/";
    }
    
}
