/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.push.simplefeed.validator.FeedSourceFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private FeedSourceFormValidator feedSourceFromValidator;
    

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(feedSourceFromValidator);
    }
    
    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }

    

    @RequestMapping(method = GET)
    public String showFeedSourceList(Model uiModel) {     
        uiModel.addAttribute("newFeedSource", new FeedSourceEntity());
        uiModel.addAttribute("feedSourceList", feedSourceService.findAll());
        return "source/list";
    }
    
    
    @RequestMapping(method = POST)
    public String addFeedSource(@ModelAttribute("newFeedSource") FeedSourceEntity newFeedSource, 
            BindingResult bindingResult, Model uiModel, RedirectAttributes redirectAttributes) {
        feedSourceFromValidator.validateFeedSourceUrl(newFeedSource.getUrl(), bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate feed source (url=" + newFeedSource.getUrl() + ")\n"
                    + bindingResult.toString());
            uiModel.addAttribute("feedSourceList", feedSourceService.findAll());
            return "source/list";
        }
        
        feedSourceService.fillBlank(newFeedSource);
        redirectAttributes.addFlashAttribute("feedSource", newFeedSource);
        return "redirect:/source/add";
    }


    @RequestMapping(value = "/add", method = GET)
    public String showAddFeedSourceForm(Model uiModel) {
        if (!uiModel.containsAttribute("feedSource")) {
            FeedSourceEntity feedSource = feedSourceService.getBlank();
            uiModel.addAttribute("feedSource", feedSource);
            logger.debug("Added blank feedSource to uiModel");
        }
        return "source/edit";
    }
    
    
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String showEditFeedSourceForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("feedSource", feedSourceService.findById(id));
        return "source/edit";
    }
        
    
    @RequestMapping(value = {"/add", "/edit/{id}"}, method = POST)
    public String saveFeedSource(@ModelAttribute("feedSource") @Valid FeedSourceEntity feedSource,
            BindingResult bindingResult, Model uiModel) {
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate feed source (" + feedSource + ")\n"
                    + bindingResult.toString());
            return "source/edit";
        }
        
        feedSourceService.save(feedSource);
        logger.debug("Added/updated feed source (" + feedSource + ")");
        return "redirect:/source";
    }
    
    
    @RequestMapping(value = "/delete/{id}", method = POST)
    public String deleteFeedSource(@PathVariable("id") Long id) {
        feedSourceService.delete(id);
        logger.debug("Delete feed source (id=" + id + ")");
        return "redirect:/source";
    }
    
}
