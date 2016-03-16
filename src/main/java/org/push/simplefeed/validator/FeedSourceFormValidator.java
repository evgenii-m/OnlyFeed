/**
 * 
 */
package org.push.simplefeed.validator;

import static org.push.simplefeed.model.entity.FeedSourceEntity.*;

import org.apache.commons.validator.routines.UrlValidator;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author push
 *
 */
@Component
public class FeedSourceFormValidator implements Validator {
    private IFeedSourceService feedSourceService;


    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }
    
    
        
    @Override
    public boolean supports(Class<?> clazz) {
        return FeedSourceEntity.class.equals(clazz);
    }
    
    
    public void validateFeedSourceUrl(String feedSourceUrl, Errors errors) {
        UrlValidator urlValidator = new UrlValidator();    
        if (!urlValidator.isValid(feedSourceUrl)) {
            errors.rejectValue("url", URL_INVALID_ERROR_CODE);
        } else if (feedSourceUrl.length() > URL_MAX_SIZE) {
            errors.rejectValue("url", URL_SIZE_ERROR_CODE);            
        } else if (!feedSourceService.isSupported(feedSourceUrl)) {
            errors.rejectValue("url", URL_UNSUPPORTED_ERROR_CODE);            
        }
    }
    

    @Override
    public void validate(Object target, Errors errors) {
        FeedSourceEntity feedSource = (FeedSourceEntity) target;
        
        String name = feedSource.getName();
        if ((name == null) || (name.trim().length() == 0)) {
            errors.rejectValue("name", NAME_EMPTY_ERROR_CODE);
        } else if ((name.length() < NAME_MIN_SIZE) || (name.length() > NAME_MAX_SIZE)) {
            errors.rejectValue("name", NAME_SIZE_ERROR_CODE);
        }
        
        String feedSourceUrl = feedSource.getUrl();
        if ((feedSourceUrl == null) || (feedSourceUrl.trim().length() == 0)) {
            errors.rejectValue("url", URL_EMPTY_ERROR_CODE);
        } else {
            validateFeedSourceUrl(feedSourceUrl, errors);
        }
        
        String logoUrl = feedSource.getLogoUrl();
        // TODO: uncomment URL validation for logoUrl after debug
//        UrlValidator urlValidator = new UrlValidator();  
        if ((logoUrl == null) || (logoUrl.trim().length() == 0)) {
            errors.rejectValue("logoUrl", LOGO_URL_EMPTY_ERROR_CODE);            
//        } else if (!urlValidator.isValid(logoUrl)) {
//            errors.rejectValue("logoUrl", LOGO_URL_INVALID_ERROR_CODE);
        } else if (logoUrl.length() > LOGO_URL_MAX_SIZE) {
            errors.rejectValue("logoUrl", LOGO_URL_SIZE_ERROR_CODE);            
        }
        
        if (feedSource.getDescription().length() > DESCRIPTION_MAX_SIZE) {
            errors.rejectValue("description", DESCRIPTION_SIZE_ERROR_CODE);
        }
    }
    
    
}
