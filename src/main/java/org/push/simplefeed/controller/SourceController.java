/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;


import javax.validation.Valid;

//import org.apache.commons.validator.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author push
 *
 */
@Controller
@RequestMapping("/source")
public class SourceController {
    private static Logger logger = LogManager.getLogger(SourceController.class);
    private IFeedSourceService feedSourceService;
    

    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }
    

    @RequestMapping(method = GET)
    public String showFeedSourceList(Model uiModel) {
        FeedSourceEntity newFeedSource = new FeedSourceEntity();
        uiModel.addAttribute("newFeedSource", newFeedSource);
        List<FeedSourceEntity> feedSourceList = feedSourceService.getAll();
        uiModel.addAttribute("feedSourceList", feedSourceList);
        logger.debug("Feed Sources count = " + feedSourceList.size());
        return "source/list";
    }
    
    
    @RequestMapping(method = POST)
    public String addFeedSource(@ModelAttribute("newFeedSource") @Valid FeedSourceEntity newFeedSource, 
            BindingResult bindingResult, Model uiModel, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.error("Error when bind newFeedSource - " + newFeedSource + ".\n" + bindingResult.toString());
            return "redirect:/source";
        }
        feedSourceService.formFeedSource(newFeedSource);
        redirectAttributes.addFlashAttribute("feedSource", newFeedSource);
        return "redirect:/source/add";
    }


    @RequestMapping(value = "/add", method = GET)
    public String showAddFeedSourceForm(Model uiModel) {
        if (!uiModel.containsAttribute("feedSource")) {
            FeedSourceEntity feedSource = new FeedSourceEntity();
            uiModel.addAttribute("feedSource", feedSource);
            logger.debug("Added blank feedSource to uiModel");
        }
        return "source/add";
    }
    
}
