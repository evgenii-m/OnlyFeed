/**
 * 
 */
package org.push.simplefeed.validator;

import org.apache.commons.validator.routines.UrlValidator;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author push
 *
 */
@Component
public class FeedSourceFormValidator implements Validator {
    static String ERROR_CODE_INVALID_URL = "validation.InvalidURL.FeedSourceForm.url.message";
    static String ERROR_CODE_UNSUPPORTED = "validation.UnsupportedFeedSource.FeedSourceForm.url.message";
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
            errors.rejectValue("url", ERROR_CODE_INVALID_URL);
            return;
        }
        
        if (!feedSourceService.isSupported(feedSourceUrl)) {
            errors.rejectValue("url", ERROR_CODE_UNSUPPORTED);            
        }
    }
    

    @Override
    public void validate(Object target, Errors errors) {
        FeedSourceEntity feedSource = (FeedSourceEntity) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "validation.InvalidURL.FeedSourceForm.url.message");
        
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(feedSource.getUrl())) {
            errors.rejectValue("url", "validation.InvalidURL.FeedSourceForm.url.message");
        }
    }
    
    
}
