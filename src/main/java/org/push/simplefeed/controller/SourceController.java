/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

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
        FeedSourceEntity newFeedSource = new FeedSourceEntity();
        uiModel.addAttribute("newFeedSource", newFeedSource);
        List<FeedSourceEntity> feedSourceList = feedSourceService.getAll();
        uiModel.addAttribute("feedSourceList", feedSourceList);
        logger.debug("Feed Sources count = " + feedSourceList.size());
        return "source/list";
    }
    
    
    @RequestMapping(method = POST)
    public String addFeedSource(@ModelAttribute("newFeedSource") FeedSourceEntity newFeedSource, 
            BindingResult bindingResult, Model uiModel, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.error("Error when bind newFeedSource - \"" + newFeedSource + "\".\n" 
                    + bindingResult.toString());
            // TODO: add an error message on view
            return "source/list";
        }
        
        feedSourceFromValidator.validateFeedSourceUrl(newFeedSource.getUrl(), bindingResult);
        if (bindingResult.hasErrors()) {
            logger.info("Error when validate Feed Source URL - \"" + newFeedSource.getUrl() + "\".\n"
                    + bindingResult.toString());
            return "source/list";
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
