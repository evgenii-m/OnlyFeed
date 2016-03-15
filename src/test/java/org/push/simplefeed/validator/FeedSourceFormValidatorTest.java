/**
 * 
 */
package org.push.simplefeed.validator;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import org.push.simplefeed.model.service.FeedSourceService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.BindException;
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
                { null, FeedSourceFormValidator.ERROR_CODE_INVALID_URL},  
                { "asd", FeedSourceFormValidator.ERROR_CODE_INVALID_URL },
                { "https://habrahabr.ru/rss/interesting/", null },
                { "https://habrahabr.ru/rss/", null },
                { "https://habrahabr.ru/", FeedSourceFormValidator.ERROR_CODE_UNSUPPORTED },
                { "https://tjournal.ru/rss", null },
                { "https://tjournal.ru/", FeedSourceFormValidator.ERROR_CODE_UNSUPPORTED }
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
