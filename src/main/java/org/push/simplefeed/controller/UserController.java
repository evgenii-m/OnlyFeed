/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.security.CustomUser;
import org.push.simplefeed.model.service.IUserService;
import org.push.simplefeed.util.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author push
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LogManager.getLogger(UserController.class);
    private MessageSource messageSource;
    private IUserService userService;
    private FileUploader fileUploader;
    @Value("${resources.img.baseUrl}")
    private String resourcesImgBaseUrl;


    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Autowired
    public void setFileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
      

    
    private Map<Long, String> formNewsStorageTimeList(Locale locale) {
        Map<Long, String> newsStorageTimeList = new LinkedHashMap<>();
        newsStorageTimeList.put(3l,
                messageSource.getMessage("user.settings.3hOptionLabel", new Object[]{}, locale));
        newsStorageTimeList.put(12l,
                messageSource.getMessage("user.settings.12hOptionLabel", new Object[]{}, locale));
        newsStorageTimeList.put(24l,
                messageSource.getMessage("user.settings.1dOptionLabel", new Object[]{}, locale));
        newsStorageTimeList.put(48l,
                messageSource.getMessage("user.settings.2dOptionLabel", new Object[]{}, locale));
        newsStorageTimeList.put(72l,
                messageSource.getMessage("user.settings.3dOptionLabel", new Object[]{}, locale));
        newsStorageTimeList.put(168l,
                messageSource.getMessage("user.settings.1wOptionLabel", new Object[]{}, locale));
        newsStorageTimeList.put(336l,
                messageSource.getMessage("user.settings.2wOptionLabel", new Object[]{}, locale));
        return newsStorageTimeList;
    }

    
    @RequestMapping(value = "/settings", method = GET)
    public String showSettingsForm(Model uiModel, Principal principal, Locale locale) {
        logger.debug("showSettingsForm");
        UserEntity user = userService.findByEmail(principal.getName());
        uiModel.addAttribute("user", user);
        Map<Long, String> newsStorageTimeList = formNewsStorageTimeList(locale);
        uiModel.addAttribute("newsStorageTimeList", newsStorageTimeList);
        return "user/settings";
    }
    

    @RequestMapping(value = "/settings/picture", method = POST)
    public String updateUserPicture(@RequestParam(value = "picture", required = true) MultipartFile picture, 
            Principal principal, RedirectAttributes redirectAttributes) {
        logger.debug("updateUserPicture");
        boolean updatePictureResult = false;
        UserEntity user = userService.findByEmail(principal.getName());
        if ((picture != null) && !picture.isEmpty() && fileUploader.validateImage(picture)) {
            String pictureUrl = fileUploader.uploadJpeg(picture);
            if (pictureUrl != null) {
                user.setPictureUrl(pictureUrl);
                userService.save(user);
                updatePictureResult = true;
            } else {
                logger.error("Error when upload picture (user.id=" + user.getId() + ")");
            }
        } else {
            logger.error("Picture empty or invalid (user.id=" + user.getId() + ")");
        }
        redirectAttributes.addFlashAttribute("updatePictureResult", updatePictureResult);
        logger.debug("Picture sucessfully updated (user.id" + user.getId() + ")");
        return "redirect:/user/settings";
    }

    @RequestMapping(value = "/settings/picture", method = DELETE)
    @ResponseBody
    public String deleteUserPicture(Principal principal) {
        logger.debug("deleteUserPicture");
        UserEntity user = userService.findByEmail(principal.getName());
        user.setPictureUrl(resourcesImgBaseUrl + UserEntity.DEFAULT_PICTURE_NAME);
        userService.save(user);
        logger.debug("Picture sucessfully deleted (user.id" + user.getId() + ")");
        return user.getPictureUrl();
    }
    
    
    private boolean validateUserInfo(UserEntity userInfo) {
        String name = userInfo.getName();
        if ((name == null) || (name.length() < 2) || (name.length() > 100)) {
            return false;
        }
        String email = userInfo.getEmail();
        if ((email == null) || !EmailValidator.getInstance().isValid(email) || (email.length() < 3)
                || (email.length() > 64) || (userService.findByEmail(email) != null)) {
            return false;
        }
        Integer newsStorageTime = userInfo.getNewsStorageTime();
        if (newsStorageTime == null) {
            return false;
        }
        return true;
//        (userService.findByEmail(email) != null)
    }
    

    @RequestMapping(value = "/settings/info", method = POST)
    public String updateUserInfo(@ModelAttribute("user") UserEntity userInfo, Principal principal, 
            RedirectAttributes redirectAttributes) {
        logger.debug("updateUserInfo");
        boolean updateInfoResult = false;
        UserEntity user = userService.findByEmail(principal.getName());
        if (!validateUserInfo(userInfo)) {
            logger.error("Invalid user info (name=" + userInfo.getName() + ", email=" + userInfo.getEmail() 
                    + ", newsStorageTime=" + userInfo.getNewsStorageTime() + ")");
        } else {
            user.setName(userInfo.getName());
            user.setEmail(userInfo.getEmail());
            user.setNewsStorageTime(userInfo.getNewsStorageTime());
            userService.save(user);
            updateInfoResult = true;
            CustomUser principalUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            principalUser.setUsername(user.getEmail());
        }
        redirectAttributes.addFlashAttribute("updateInfoResult", updateInfoResult);
        logger.debug("User info sucessfully updated (user.id" + user.getId() + ")");
        return "redirect:/user/settings";
    }

   
}
