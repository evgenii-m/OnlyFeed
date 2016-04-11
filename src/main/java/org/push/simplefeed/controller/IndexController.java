/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Locale;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    
    
    @RequestMapping(method = GET)
    public String index() {
        return "index";
    }
    
    
    @RequestMapping(value = "/loginfail")
    public String loginFail(Model uiModel, Locale locale) {
        logger.info("Login fail");
        uiModel.addAttribute("loginFailMessage", messageSource.getMessage("index.loginFailMessage",
                new Object[]{}, locale));
        return "index";
    }
    
    
    @RequestMapping(value = "/register", method = GET)
    public String showRegisterForm(Model uiModel) {
        UserEntity user = new UserEntity();
        uiModel.addAttribute("user", user);
        return "register";
    }
    

    @RequestMapping(value = "/register", method = POST)
    public String register(@ModelAttribute("user") @Valid UserEntity user, BindingResult bindingResult, 
            Model uiModel, Locale locale) {
        if (userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "validation.emailAlreadyUsed");
        }        
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate user (" + user + ")\n" 
                    + bindingResult.toString());
            return "register";
        }

        userService.save(user);
        logger.debug("Added user (" + user + ")");
        uiModel.addAttribute("registerSuccessMessage", messageSource.getMessage("index.registerSuccessMessage", 
                new Object[]{}, locale));
        return "index";
    }
    
    
}
