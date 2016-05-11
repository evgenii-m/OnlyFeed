/**
 * 
 */
package org.push.onlyfeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.security.Principal;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.onlyfeed.model.entity.FeedSourceEntity;
import org.push.onlyfeed.model.entity.UserEntity;
import org.push.onlyfeed.model.service.IFeedSourceService;
import org.push.onlyfeed.model.service.IUserService;
import org.push.onlyfeed.util.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private FileUploader fileUploader;

    
    @Autowired
    public void setFileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }
    
    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    

    @RequestMapping(method = GET)
    public String showSources(Model uiModel, Principal principal) {
        logger.debug("showSources");
        UserEntity user = userService.findByEmail(principal.getName());
        uiModel.addAttribute("user", user);
        uiModel.addAttribute("feedSources", user.getFeedSources());
        if (!uiModel.containsAttribute("newFeedSource")) {
            uiModel.addAttribute("newFeedSource", new FeedSourceEntity());
        }
        uiModel.addAttribute("feedSamples", feedSourceService.getFeedSamples());
        return "source/list";
    }

    
    private void validateFeedSourceUrl(String feedSourceUrl, Errors errors) {
        if (!UrlValidator.getInstance().isValid(feedSourceUrl)) {
            errors.rejectValue("url", "validation.url");       
        } else if (!feedSourceService.isSupported(feedSourceUrl)) {
            errors.rejectValue("url", "validation.unsupportedFeedSource");            
        }
    }
    
    private String findFeedSourceByUrl(String url) {
        UrlValidator urlValidator = UrlValidator.getInstance();
        String[] prefixes = { "http://", "https://" };
        Pattern prefixPattern = Pattern.compile("^(http://|https://).+");
        String[] suffixes = { "/rss", "/atom" };
        Pattern suffixPattern = Pattern.compile(".+(/rss|/atom)$");

        // if protocol is not specified, try to append "http://" or "https://"
        for (String prefix : prefixes) {
            String prefixUrl = (prefixPattern.matcher(url).matches()) ? url : prefix + url;
            if (!urlValidator.isValid(prefixUrl)) {
                continue;
            }

            // for valid URLs if feed suffix is not specified, try to append "/rss" or "/atom"
            if (!suffixPattern.matcher(prefixUrl).matches()) {
                for (String suffix : suffixes) {
                    String suffixUrl = prefixUrl + suffix;
                    if (feedSourceService.isSupported(suffixUrl)) {
                        return suffixUrl;
                    }                        
                }                    
            } else if (feedSourceService.isSupported(prefixUrl)) {
                return prefixUrl;
            }
        }
        return null;
    }

    @RequestMapping(method = POST)
    public String addFeedSource(@ModelAttribute("newFeedSource") FeedSourceEntity newFeedSource, 
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.debug("addFeedSource");
        validateFeedSourceUrl(newFeedSource.getUrl(), bindingResult);
        if (bindingResult.hasErrors()) {
            String modUrl = findFeedSourceByUrl(newFeedSource.getUrl());
            if (modUrl == null) {
                logger.error("Error when validate feed source (url=" + newFeedSource.getUrl() + ")\n"
                        + bindingResult.toString());
                redirectAttributes.addFlashAttribute("newFeedSource", newFeedSource);
                redirectAttributes.addFlashAttribute(
                        "org.springframework.validation.BindingResult.newFeedSource", bindingResult);
                return "redirect:/source";
            }
            newFeedSource.setUrl(modUrl);
        }

        feedSourceService.fillBlank(newFeedSource);
        redirectAttributes.addFlashAttribute("feedSource", newFeedSource);
        return "redirect:/source/add";
    }


    @RequestMapping(value = "/add", method = GET)
    public String showAddFeedSourceForm(Model uiModel, Principal principal) {
        logger.debug("showAddFeedSourceForm");
        UserEntity user = userService.findByEmail(principal.getName());
        uiModel.addAttribute("user", user);
        if (!uiModel.containsAttribute("feedSource")) {
            FeedSourceEntity feedSource = feedSourceService.getBlank();
            uiModel.addAttribute("feedSource", feedSource);
            logger.debug("Added blank feedSource to uiModel");
        }
        return "source/edit";
    }


    @RequestMapping(value = "/add/{feedSampleId}", method = GET)
    public String showAddFeedSampleForm(@PathVariable("feedSampleId") Long feedSampleId, Model uiModel, 
            Principal principal) {
        logger.debug("showAddFeedSampleForm");
        UserEntity user = userService.findByEmail(principal.getName());
        uiModel.addAttribute("user", user);
        FeedSourceEntity feedSource = feedSourceService.getBlank();
        feedSourceService.fillBlankFromSample(feedSource, feedSampleId);
        uiModel.addAttribute("feedSource", feedSource);
        return "source/edit";
    }
    
    
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String showEditFeedSourceForm(@PathVariable("id") Long id, Model uiModel, Principal principal) {
        logger.debug("showEditFeedSourceForm (id=" + id + ")");
        UserEntity user = userService.findByEmail(principal.getName());
        uiModel.addAttribute("user", user);
        FeedSourceEntity feedSource = feedSourceService.findById(id);
        if ((feedSource != null) && (feedSource.getUser().equals(user))) {
            uiModel.addAttribute("feedSource", feedSource);
        } else {
            logger.error("Feed source (feedSource.id=" + id + ") not found for user (user.id=" + 
                    user.getId() + ")");
        }
        return "source/edit";
    }
        
    
    @RequestMapping(value = {"/add", "/add/{id}", "/edit/{id}"}, method = POST)
    public String saveFeedSource(@ModelAttribute("feedSource") @Valid FeedSourceEntity feedSource,
            @RequestParam(value = "picture", required = false) MultipartFile picture, 
            BindingResult bindingResult, Model uiModel, Principal principal) {
        logger.debug("saveFeedSource");
        UserEntity user = userService.findByEmail(principal.getName());
        validateFeedSourceUrl(feedSource.getUrl(), bindingResult);
        if ((picture != null) && !picture.isEmpty()) {
            if (fileUploader.validateImage(picture)) {
                String pictureUrl = fileUploader.uploadJpeg(picture);
                if (pictureUrl != null) {
                    feedSource.setLogoUrl(pictureUrl);
                } else {
                    logger.error("Error when upload picture (feedSource.id=" + feedSource.getId() + ")");
                    bindingResult.rejectValue("logoUrl", "validation.invalidImage");
                }
            } else {
                logger.error("Invalid picture (feedSource.id=" + feedSource.getId() + ")");
                bindingResult.rejectValue("logoUrl", "validation.invalidImage");
            }
        } else {
            logger.debug("Logo not set");
        }
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate feed source (" + feedSource + ")\n"
                    + bindingResult.toString());
            uiModel.addAttribute("user", user);
            return "source/edit";
        }

        feedSourceService.save(feedSource, user);
        logger.debug("Added/updated feed source (" + feedSource + ")");
        return "redirect:/source";
    }

    
    @RequestMapping(value = "/delete/{id}", method = DELETE)
    @ResponseBody
    public boolean deleteFeedSource(@PathVariable("id") Long id) {
        logger.debug("deleteFeedSource (id=" + id + ")");
        return feedSourceService.delete(id);
    }
    
}
