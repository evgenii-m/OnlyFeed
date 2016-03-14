/**
 * 
 */
package org.push.simplefeed.model.service;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileReader;
import java.io.IOException;

import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.util.xml.XmlConverter;
import org.push.simplefeed.util.xml.rsstypes.Rss;
import org.push.simplefeed.util.xml.rsstypes.RssChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
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
                    "https://localhost:8080/SimpleFeed/img/no_logo.gif",   // logoUrl
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
    
    
        
    @BeforeMethod()
    public void setUpMethod() throws Exception {
        feedSourceService = new FeedSourceService();        
    }
    
    

    public void prepareRssService(String testDataFileName) throws XmlMappingException, IOException {
        RssService rssService = mock(RssService.class);
        FileReader testDataFileReader = new FileReader(testDataFileName);
        Rss rss = (Rss) xmlConverter.xmlToObject(testDataFileReader);
        RssChannel rssChannel = rss.getChannel();
        when(rssService.getChannel(testDataFileName)).thenReturn(rssChannel);
        
        feedSourceService.setRssService(rssService);
    }
    
    
    @Test(dataProvider = "testDataSet1")
    public void testFormFeedSource(String testDataFileName, String testNameField, String testLogoUrlField,
            String testDescriptionField) throws XmlMappingException, IOException {
        prepareRssService(testDataFileName);
        
        FeedSourceEntity feedSourceEntity = new FeedSourceEntity();
        feedSourceEntity.setUrl(testDataFileName);
        feedSourceService.formFeedSource(feedSourceEntity);
        
        assertNotNull(feedSourceEntity.getName(), "Name field is null\n");
        assertEquals(feedSourceEntity.getName(), testNameField, "Names do not match\n"
                + "feedSourceEntity.name: \"" + feedSourceEntity.getName() + "\"\n");
        
        assertNotNull(feedSourceEntity.getLogoUrl(), "LogoUrl field is null\n");
        assertEquals(feedSourceEntity.getLogoUrl(), testLogoUrlField, "Logo URLs do not match\n"
                + "feedSourceEntity.logoUrl: \"" + feedSourceEntity.getLogoUrl() + "\"\n");
        
        assertNotNull(feedSourceEntity.getDescription(), "Description field is null\n");
        assertEquals(feedSourceEntity.getDescription(), testDescriptionField, "Descriptions do not match\n"
                + "feedSourceEntity.description: \"" + feedSourceEntity.getDescription() + "\"\n");
    }
    
    
    @Test(dataProvider = "testWrongDataSet1")
    public void testWrongFormFeedSource(String testDataFileName, String testNameField, String testLogoUrlField,
            String testDescriptionField) throws XmlMappingException, IOException {
        prepareRssService(testDataFileName);
        
        FeedSourceEntity feedSourceEntity = new FeedSourceEntity();
        feedSourceEntity.setUrl(testDataFileName);
        feedSourceService.formFeedSource(feedSourceEntity);

        assertNotNull(feedSourceEntity.getName(), "Name field is null\n");
        assertNotEquals(feedSourceEntity.getName(), testNameField, "Names must not match\n"
                + "feedSourceEntity.name: \"" + feedSourceEntity.getName() + "\"\n");

        assertNotNull(feedSourceEntity.getLogoUrl(), "LogoUrl field is null\n");
        assertNotEquals(feedSourceEntity.getLogoUrl(), testLogoUrlField, "Logo URLs must not match\n"
                + "feedSourceEntity.logoUrl: \"" + feedSourceEntity.getLogoUrl() + "\"\n");

        assertNotNull(feedSourceEntity.getDescription(), "Description field is null\n");
        assertNotEquals(feedSourceEntity.getDescription(), testDescriptionField, "Descriptions must not match\n"
                + "feedSourceEntity.description: \"" + feedSourceEntity.getDescription() + "\"\n");
    }
    
}
