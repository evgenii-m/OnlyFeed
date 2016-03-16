/**
 * 
 */
package org.push.simplefeed.validator;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.service.FeedSourceService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.Errors;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;


/**
 * @author push
 *
 */
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class FeedSourceFormValidatorTest extends AbstractTestNGSpringContextTests  {
    private FeedSourceFormValidator feedSourceFormValidator;
    private FeedSourceService feedSourceService;
    private Errors errors;
    
    
    @DataProvider
    public Object[][] testDataSet1() {
        return new Object[][] {
                { null, FeedSourceEntity.URL_INVALID_ERROR_CODE},  
                { "asd", FeedSourceEntity.URL_INVALID_ERROR_CODE },
                { "https://habrahabr.ru/rss/interesting/", null },
                { "https://habrahabr.ru/rss/", null },
                { "https://habrahabr.ru/", FeedSourceEntity.URL_UNSUPPORTED_ERROR_CODE },
                { "https://tjournal.ru/rss", null },
                { "https://tjournal.ru/", FeedSourceEntity.URL_UNSUPPORTED_ERROR_CODE }
        };
    }
    
    
   @BeforeMethod()
   public void setUpMethod() throws Exception {
       errors = mock(Errors.class);
       feedSourceService = mock(FeedSourceService.class);
       feedSourceFormValidator = new FeedSourceFormValidator();
       feedSourceFormValidator.setFeedSourceService(feedSourceService);
   }
    
    
    @Test(dataProvider = "testDataSet1")
    public void testValidateFeedSourceUrl(String feedSourceUrl, String errorCode) {
        when(feedSourceService.isSupported(feedSourceUrl)).thenReturn(errorCode == null);
        feedSourceFormValidator.validateFeedSourceUrl(feedSourceUrl, errors);
        if (errorCode == null) {
            verify(errors, never()).rejectValue("url", errorCode);
        } else {
            verify(errors).rejectValue("url", errorCode);            
        }
    }
    
}
