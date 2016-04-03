/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.push.simplefeed.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
    private IUserService userService;


    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    
    
    private void validateFeedSourceUrl(String feedSourceUrl, Errors errors) {
        if (!UrlValidator.getInstance().isValid(feedSourceUrl)) {
            errors.rejectValue("url", "validation.url");       
        } else if (!feedSourceService.isSupported(feedSourceUrl)) {
            errors.rejectValue("url", "validation.unsupportedFeedSource");            
        }
    }
    
    

    @RequestMapping(method = GET)
    public String showFeedSourceList(Model uiModel, Principal principal) {
        // TODO: add test (user == null)
        UserEntity user = userService.findOne(principal.getName());
        List<FeedSourceEntity> feedSourceList = feedSourceService.findByUser(user);
        uiModel.addAttribute("feedSourceList", feedSourceList);
        if (!uiModel.containsAttribute("newFeedSource")) {
            uiModel.addAttribute("newFeedSource", new FeedSourceEntity());
        }
        return "source/list";
    }


    @RequestMapping(method = POST)
    public String addFeedSource(@ModelAttribute("newFeedSource") FeedSourceEntity newFeedSource, 
            BindingResult bindingResult, Model uiModel, RedirectAttributes redirectAttributes) {
        validateFeedSourceUrl(newFeedSource.getUrl(), bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate feed source (url=" + newFeedSource.getUrl() + ")\n"
                    + bindingResult.toString());
            redirectAttributes.addFlashAttribute("newFeedSource", newFeedSource);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.newFeedSource", bindingResult);
            return "redirect:/source";
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
            BindingResult bindingResult, Model uiModel, Principal principal) {
        validateFeedSourceUrl(feedSource.getUrl(), bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate feed source (" + feedSource + ")\n"
                    + bindingResult.toString());
            return "source/edit";
        }

        // TODO: add test (user == null)
        UserEntity user = userService.findOne(principal.getName());
        feedSourceService.save(feedSource, user);
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
