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
import org.push.onlyfeed.model.entity.ResetPasswordRequest;
import org.push.onlyfeed.model.entity.UserEntity;
import org.push.onlyfeed.model.service.IResetPasswordRequestService;
import org.push.onlyfeed.model.service.IUserService;
import org.push.onlyfeed.util.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private IResetPasswordRequestService resetPasswordRequestService;
    private FileUploader fileUploader;
    private MailSender mailSender;
    @Value("${mail.username}")
    private String mailSenderUsername;
    @Value("${resources.img.baseUrl}")
    private String resourcesImgBaseUrl;
    @Value("${password.reset.url}")
    private String passwordResetBaseUrl;

    
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired    
    public void setResetPasswordRequestService(IResetPasswordRequestService resetPasswordRequestService) {
        this.resetPasswordRequestService = resetPasswordRequestService;
    }

    @Autowired
    public void setFileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }
    
    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    
    @RequestMapping(method = GET)
    public String index(Principal principal, Locale locale) {
        logger.debug("index");
        if (principal != null) {
            logger.debug("User is logged - redirect to feed page");
            return "redirect:/feed";
        }
        return "index";
    }
    
    
    @RequestMapping(value = "/help", method = GET) 
    public String help() {
        logger.debug("help");
        return "help";
    }
    
    
    @RequestMapping(value = "/password/reset", method = GET)
    public String showResetPasswordRequestForm(Model uiModel, Locale locale) {
        logger.debug("showResetPasswordRequestForm");
        uiModel.addAttribute("message", messageSource.getMessage(
                "password.reset.resetPasswordRequestMessage", new Object[]{}, locale));
        return "password/reset";
    }
    
    @RequestMapping(value = "/password/reset", method = POST)
    public String sendResetPasswordRequest(@RequestParam(value = "email", required = true) String email, 
            Model uiModel, Locale locale) {
        logger.debug("sendResetPasswordRequest (email=" + email + ")");
        UserEntity user = userService.findByEmail(email);
        if (user == null) {
            logger.error("User not found (email=" + email + ")");
            uiModel.addAttribute("message", messageSource.getMessage(
                    "password.reset.resetPasswordRequestMessage", new Object[]{}, locale));
            uiModel.addAttribute("errorMessage", messageSource.getMessage(
                    "password.reset.accountNotFoundMessage", new Object[]{}, locale));
            return "password/reset";
        }
        
        ResetPasswordRequest resetRequest = resetPasswordRequestService.saveRequest(email);
        logger.info("Create reset password request (" + resetRequest + ")");
        String passwordResetUrl = passwordResetBaseUrl + resetRequest.getToken();
        sendEmail(resetRequest.getEmail(), 
                messageSource.getMessage("mail.passwordReset.subject", new Object[]{}, locale),
                messageSource.getMessage("mail.passwordReset.requestMsg", new Object[]{ passwordResetUrl }, locale));
        
        uiModel.addAttribute("message", messageSource.getMessage(
                "password.reset.sendRequestSuccessMessage", new Object[]{ resetRequest.getEmail() }, locale));
        return "password/reset";
    }
    
    
    private boolean checkResetPasswordRequestToken(String requestToken) {
        ResetPasswordRequest resetRequest = resetPasswordRequestService.findByToken(requestToken);
        if (resetRequest == null) {
            logger.error("Invalid reset password request token (requestToken=" + requestToken + ")");
            return false;
        }
        
        UserEntity user = userService.findByEmail(resetRequest.getEmail());
        if ((user == null) || (user.getEnabled() == false)) {
            logger.error("Reset impossible, user not found or disabled (" + resetRequest + ")");
            return false;
        }
        
        return true;
    }
    
    @RequestMapping(value = "/password/reset/{token}", method = GET)
    public String showResetPasswordForm(@PathVariable("token") String requestToken, Model uiModel, Locale locale) {
        logger.debug("showResetPasswordForm");
        if (checkResetPasswordRequestToken(requestToken) != true) {
            return "redirect:/";
        }
        uiModel.addAttribute("message", messageSource.getMessage(
                "password.reset.resetPasswordMessage", new Object[]{}, locale));
        uiModel.addAttribute("passwordReset", true);
        return "password/reset";
    }
    
    @RequestMapping(value = "/password/reset/{token}", method = POST)
    public String resetPassword(@PathVariable("token") String requestToken, 
            @RequestParam(value = "newPassword", required = true) String newPassword, 
            Model uiModel, Locale locale, RedirectAttributes redirectAttributes) {
        logger.debug("resetPassword");
        if (checkResetPasswordRequestToken(requestToken) != true) {
            return "redirect:/";
        }
        
        if ((newPassword == null) || (newPassword.length() < 6) || (newPassword.length() > 64)) {
            uiModel.addAttribute("message", messageSource.getMessage(
                    "password.reset.resetPasswordMessage", new Object[]{}, locale));
            uiModel.addAttribute("passwordReset", true);
            uiModel.addAttribute("errorMessage", messageSource.getMessage(
                    "password.reset.incorrectPassword", new Object[]{}, locale));
            return "password/reset";
        }

        ResetPasswordRequest resetRequest = resetPasswordRequestService.findByToken(requestToken);
        UserEntity user = userService.findByEmail(resetRequest.getEmail());
        logger.info("Reset password (user.id=" + user.getId() + ", oldPassword=" + user.getPassword() + ")");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedNewPassword = encoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userService.save(user);
        logger.info("Set new password (user.id=" + user.getId() + ", newPassword=" + user.getPassword() + ")");

        resetPasswordRequestService.deleteRequest(user.getEmail());
        sendEmail(user.getEmail(), 
                messageSource.getMessage("mail.passwordReset.subject", new Object[]{}, locale),
                messageSource.getMessage("mail.passwordReset.successMsg", new Object[]{}, locale));
        
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage(
                "index.resetPasswordSuccessMessage", new Object[]{}, locale));
        return "redirect:/";
    }
    
    
    @RequestMapping(value = "/loginfail")
    public String loginFail(Model uiModel, Locale locale, RedirectAttributes redirectAttributes) {
        logger.info("Login fail");
        redirectAttributes.addFlashAttribute("loginFailMessage", 
                messageSource.getMessage("index.loginFailMessage", new Object[]{}, locale));
        return "redirect:/";
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
        sendEmail(user.getEmail(), 
                messageSource.getMessage("mail.registerSuccess.subject", new Object[]{}, locale),
                messageSource.getMessage("mail.registerSuccess.msg", new Object[]{}, locale));
        logger.info("Registered user (" + user + ")");
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage(
                "index.registerSuccessMessage", new Object[]{}, locale));
        return "redirect:/";
    }
    

    private void sendEmail(String recipientEmail, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipientEmail);
        msg.setFrom(mailSenderUsername);
        msg.setSubject(subject);
        msg.setText(text);
        try {
            mailSender.send(msg);
        } catch (MailException e) {
            logger.error("MailException when send email to " + recipientEmail
                    + ", exception: " + e + ")");
            e.printStackTrace();
        }
    }
    
    
}
