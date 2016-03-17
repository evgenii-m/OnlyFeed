/**
 * 
 */
package org.push.simplefeed.model.service;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileReader;

import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.util.xml.XmlConverter;
import org.push.simplefeed.util.xml.rsstypes.Rss;
import org.push.simplefeed.util.xml.rsstypes.RssChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

/**
 * @author push
 *
 */
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class FeedSourceServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private XmlConverter xmlConverter;
    private FeedSourceService feedSourceService;
    
    
    @DataProvider
    public Object[][] testDataSet1() {
        return new Object[][] {
                { 
                    "src/test/resources/rss1.xml",              // fileName
                    "Хабрахабр / Интересные публикации",        // name
                    "https://habrahabr.ru/images/logo.png",     // logoUrl
                    "Интересные публикации на Хабрахабре"       // description
                },  
                { 
                    "src/test/resources/rss2.xml",              // fileName
                    "TJ: популярное",                           // name
                    FeedSourceService.DEFAULT_LOGO_URL,         // logoUrl
                    ""                                          // description
                }  
        };
    }
    

    @DataProvider
    public Object[][] testWrongDataSet1() {
        return new Object[][] {
                { 
                    "src/test/resources/rss1.xml",              // fileName
                    "Хабрахабр",                                // name
                    "https://habrahabr.ru/images/logo.jpg",     // logoUrl
                    "Интересные публикации на Хабрахабре!"      // description
                }     
        };
    }
    
    

    public void prepareEnvironment(String testDataFileName) throws Exception {
        feedSourceService = new FeedSourceService();
        RssService rssService = mock(RssService.class);
        FileReader testDataFileReader = new FileReader(testDataFileName);
        Rss rss = (Rss) xmlConverter.xmlToObject(testDataFileReader);
        RssChannel rssChannel = rss.getChannel();
        when(rssService.getChannel(testDataFileName)).thenReturn(rssChannel);
        
        feedSourceService.setRssService(rssService);
    }
    
    
    @Test(dataProvider = "testDataSet1")
    public void testFillBlank(String testDataFileName, String testNameField, String testLogoUrlField,
            String testDescriptionField) throws Exception {
        prepareEnvironment(testDataFileName);
        
        FeedSourceEntity feedSource = new FeedSourceEntity();
        feedSource.setUrl(testDataFileName);
        feedSourceService.fillBlank(feedSource);
        
        assertNotNull(feedSource.getName(), "Name field is null\n");
        assertEquals(feedSource.getName(), testNameField, "Names do not match\n"
                + "feedSource.name: \"" + feedSource.getName() + "\"\n");
        
        assertNotNull(feedSource.getLogoUrl(), "LogoUrl field is null\n");
        assertEquals(feedSource.getLogoUrl(), testLogoUrlField, "Logo URLs do not match\n"
                + "feedSource.logoUrl: \"" + feedSource.getLogoUrl() + "\"\n");
        
        assertNotNull(feedSource.getDescription(), "Description field is null\n");
        assertEquals(feedSource.getDescription(), testDescriptionField, "Descriptions do not match\n"
                + "feedSource.description: \"" + feedSource.getDescription() + "\"\n");
    }
    
    
    @Test(dataProvider = "testWrongDataSet1")
    public void testWrongFillBlank(String testDataFileName, String testNameField, String testLogoUrlField,
            String testDescriptionField) throws Exception {
        prepareEnvironment(testDataFileName);
        
        FeedSourceEntity feedSource = new FeedSourceEntity();
        feedSource.setUrl(testDataFileName);
        feedSourceService.fillBlank(feedSource);

        assertNotNull(feedSource.getName(), "Name field is null\n");
        assertNotEquals(feedSource.getName(), testNameField, "Names must not match\n"
                + "feedSource.name: \"" + feedSource.getName() + "\"\n");

        assertNotNull(feedSource.getLogoUrl(), "LogoUrl field is null\n");
        assertNotEquals(feedSource.getLogoUrl(), testLogoUrlField, "Logo URLs must not match\n"
                + "feedSource.logoUrl: \"" + feedSource.getLogoUrl() + "\"\n");

        assertNotNull(feedSource.getDescription(), "Description field is null\n");
        assertNotEquals(feedSource.getDescription(), testDescriptionField, "Descriptions must not match\n"
                + "feedSource.description: \"" + feedSource.getDescription() + "\"\n");
    }
    
}
