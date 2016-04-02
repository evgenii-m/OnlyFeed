/**
 * 
 */
package org.push.simplefeed.validator;

import static org.push.simplefeed.model.entity.UserEntity.*;

import org.apache.commons.validator.routines.EmailValidator;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author push
 *
 */
@Component
public class UserFormValidator implements Validator {
    private IUserService userService;
    
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }
  
    
    @Override
    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;
        
        String name = user.getName();
        if ((name == null) || (name.trim().length() == 0)) {
            errors.rejectValue("name", NAME_EMPTY_ERROR_CODE);
        } else if ((name.length() < NAME_MIN_SIZE) || (name.length() > NAME_MAX_SIZE)) {
            errors.rejectValue("name", NAME_SIZE_ERROR_CODE);
        }
        
        String email = user.getEmail();
        EmailValidator emailValidator = EmailValidator.getInstance();
        if ((email == null) || (email.trim().length() == 0)) {
            errors.rejectValue("email", EMAIL_EMPTY_ERROR_CODE);
        } else if (!emailValidator.isValid(email)) {
            errors.rejectValue("email", EMAIL_INVALID_ERROR_CODE);            
        } else if (email.length() > EMAIL_MAX_SIZE) {
            errors.rejectValue("email", EMAIL_SIZE_ERROR_CODE);
        } else if (userService.findOne(email) != null) {
            errors.rejectValue("email", EMAIL_ALREADY_TAKEN_ERROR_CODE);
        }
        
        String password = user.getPassword();
        // TODO: add password format validation
        if ((password == null) || (password.trim().length() == 0)) {
            errors.rejectValue("password", PASSWORD_EMPTY_ERROR_CODE);
        } else if ((password.length() < PASSWORD_MIN_SIZE) || (password.length() > PASSWORD_MAX_SIZE)) {
            errors.rejectValue("password", PASSWORD_SIZE_ERROR_CODE);
        }

        String pictureUrl = user.getPictureUrl();
        // TODO: uncomment URL validation for pictureUrl after debug
//        UrlValidator urlValidator = UrlValidator.getInstance(); 
        if ((pictureUrl == null) || (pictureUrl.trim().length() == 0)) {
            errors.rejectValue("pictureUrl", PICTURE_URL_EMPTY_ERROR_CODE);            
//        } else if (!urlValidator.isValid(pictureUrl)) {
//            errors.rejectValue("pictureUrl", PICTURE_URL_INVALID_ERROR_CODE);
        } else if (pictureUrl.length() > PICTURE_URL_MAX_SIZE) {
            errors.rejectValue("pictureUrl", PICTURE_URL_SIZE_ERROR_CODE);            
        }
        
    }
    
}
