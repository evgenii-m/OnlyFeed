/**
 * 
 */
package org.push.simplefeed.model.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileReader;
import java.io.IOException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.util.xml.XmlConverter;
import org.push.simplefeed.util.xml.rsstypes.Rss;
import org.push.simplefeed.util.xml.rsstypes.RssChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author push
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class FeedSourceServiceTest {
    @Autowired
    private XmlConverter xmlConverter;
    private FeedSourceService feedSourceService;
    private static Object[][] testDataList = new Object[][] {
        { 
            "src/test/resources/rss1.xml",
            "Хабрахабр / Интересные публикации", true,      // name
            "https://habrahabr.ru/images/logo.png", true,   // logoUrl
            "Интересные публикации на Хабрахабре", true     // description
        }
//        },
//        { 
//            "src/test/resources/rss1.xml",
//            "Хабрахабр", false
//        }
    };
    

    @Before
    public void prepareTestEnvironment() throws XmlMappingException, IOException {
        RssService rssService = mock(RssService.class);
        for (Object[] testData : testDataList) {
            String testDataFileName = (String) testData[0];
            FileReader testDataFileReader = new FileReader(testDataFileName);
            Rss rss = (Rss) xmlConverter.xmlToObject(testDataFileReader);
            RssChannel rssChannel = rss.getChannel();
            when(rssService.getChannel(testDataFileName)).thenReturn(rssChannel);
        }
        
        feedSourceService = new FeedSourceService();
        feedSourceService.setRssService(rssService);
    }
    
    
    @Test
    public void testFormFeedSource() {
        for (Object[] testData : testDataList) {
            FeedSourceEntity feedSourceEntity = new FeedSourceEntity();
            String testDataFileName = (String) testData[0];
            feedSourceEntity.setUrl(testDataFileName);
            feedSourceService.formFeedSource(feedSourceEntity);

            assertNotNull("Name field null", feedSourceEntity.getName());
            String testNameField = (String) testData[1];
            boolean testNameFieldResult = (boolean) testData[2];
            assertEquals("Names do not match\n"
                    + "feedSourceEntity.name: \"" + feedSourceEntity.getName() + "\"\n", 
                    feedSourceEntity.getName().matches(testNameField), testNameFieldResult);

            assertNotNull("LogoUrl field null", feedSourceEntity.getLogoUrl());
            String testLogoUrlField = (String) testData[3];
            boolean testLogoUrlFieldResult = (boolean) testData[4];
            assertEquals("Logo URLs do not match\n"
                    + "feedSourceEntity.logoUrl: \"" + feedSourceEntity.getLogoUrl() + "\"\n", 
                    feedSourceEntity.getLogoUrl().matches(testLogoUrlField), testLogoUrlFieldResult);

            assertNotNull("Description field null", feedSourceEntity.getDescription());
            String testDescriptionField = (String) testData[5];
            boolean testDescriptionFieldResult = (boolean) testData[6];
            assertEquals("Descriptions do not match\n"
                    + "feedSourceEntity.description: \"" + feedSourceEntity.getDescription() + "\"\n", 
                    feedSourceEntity.getDescription().matches(testDescriptionField), testDescriptionFieldResult);
        }
    }
    
}
