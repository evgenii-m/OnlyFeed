/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IUserService;
import org.push.simplefeed.util.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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


    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    

    @RequestMapping(value = "/settings", method = GET)
    public String showEditForm(Model uiModel, Principal principal) {
        logger.debug("showEditForm");
        UserEntity user = userService.findByEmail(principal.getName());
        logger.debug(user);
        uiModel.addAttribute("user", user);
        return "user/settings";
    }
    

//    @RequestMapping(value = "/edit", method = POST)
//    public String updateUser(@ModelAttribute("user") @Valid UserEntity user, BindingResult bindingResult, 
//            Model uiModel, Locale locale, @RequestParam(value = "image", required=false) MultipartFile image) {
//        logger.debug("updateUser");
//        logger.debug(user);
//        if (userService.findByEmail(user.getEmail()) != null) {
//            bindingResult.rejectValue("email", "validation.emailAlreadyUsed");
//        }        
//        if (bindingResult.hasErrors()) {
//            logger.error("Error when validate user (" + user + ")\n" 
//                    + bindingResult.toString());
//            uiModel.addAttribute("editPage", true);
//            return "user/edit";
//        }
//        
//        if (!image.isEmpty() && FileUploader.getInstance().validateImage(image)) {
//            String path = FileUploader.getInstance().uploadImage(image);
//            logger.debug("Path: " + path);
//        }
//
//        userService.save(user);
//        logger.debug("Updated user (" + user + ")");
//        uiModel.addAttribute("updateSuccessMessage", messageSource.getMessage(
//                "user.edit.updateSuccessMessage", new Object[]{}, locale));
//        return "user/edit";
//    }
   
}
