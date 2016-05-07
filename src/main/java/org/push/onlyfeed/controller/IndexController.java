/**
 * 
 */
package org.push.onlyfeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.onlyfeed.model.entity.UserEntity;
import org.push.onlyfeed.model.service.IUserService;
import org.push.onlyfeed.util.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author push
 * 
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private static Logger logger = LogManager.getLogger(IndexController.class);
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
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    public void setFileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }
    
    
    @RequestMapping(method = GET)
    public String index(Principal principal) {
        logger.debug("index");
        if (principal != null) {
            logger.debug("User is logged - redirect to feed page");
            return "redirect:/feed";
        }
        return "index";
    }
    
    
    @RequestMapping(value = "/help", method = GET) 
    public String help() {
        return "help";
    }
    
    
    @RequestMapping(value = "/loginfail")
    public String loginFail(Model uiModel, Locale locale) {
        logger.info("Login fail");
        uiModel.addAttribute("loginFailMessage", messageSource.getMessage("index.loginFailMessage",
                new Object[]{}, locale));
        return "index";
    }
    
    
    @RequestMapping(value = "/register", method = GET)
    public String showRegisterForm(Model uiModel, Principal principal) {
        if (principal != null) {
            logger.debug("User is logged - redirect to feed page");
            return "redirect:/feed";
        }
        logger.debug("showRegisterForm");
        UserEntity user = new UserEntity();
        user.setPictureUrl(resourcesImgBaseUrl + UserEntity.DEFAULT_PICTURE_NAME);
        uiModel.addAttribute("user", user);
        return "register";
    }
    

    @RequestMapping(value = "/register", method = POST)
    public String register(@ModelAttribute("user") @Valid UserEntity user, BindingResult bindingResult, 
            Model uiModel, @RequestParam(value = "picture", required = false) MultipartFile picture,
            Locale locale, RedirectAttributes redirectAttributes) {
        logger.debug("register");
        if (userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "validation.emailAlreadyUsed");
        }        
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate user (" + user + ")\n" 
                    + bindingResult.toString());
            return "register";
        }
        
        if ((picture != null) && !picture.isEmpty()) {
            if (!fileUploader.validateImage(picture)) {
                logger.debug("Invalid image");
                bindingResult.rejectValue("pictureUrl", "validation.invalidImage");
                return "register";
            }
            String pictureUrl = fileUploader.uploadJpeg(picture);
            if (pictureUrl != null) {
                user.setPictureUrl(pictureUrl);
            }
        }
        
        userService.save(user);
        logger.debug("Registered user (" + user + ")");
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage(
                "index.registerSuccessMessage", new Object[]{}, locale));
        return "redirect:/";
    }
    
    
}
