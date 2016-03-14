/**
 * 
 */
package org.push.simplefeed.util.xml;

import static org.testng.Assert.*;

import java.io.FileReader;
import java.io.IOException;
//import java.io.StringWriter;
//import java.nio.file.Files;
//import java.nio.file.Paths;

import org.push.simplefeed.util.xml.rsstypes.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;


/**
 * @author push
 *
 */
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class XmlConverterTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private XmlConverter xmlConverter;
    
    
    @DataProvider
    public Object[][] testDataSet1() {
        RssChannel rssChannel1 = new RssChannel();
        rssChannel1.setTitle("Хабрахабр / Интересные публикации");
        rssChannel1.setLink("https://habrahabr.ru/");
        rssChannel1.setDescription("Интересные публикации на Хабрахабре");
        
        return new Object[][] {
                {
                    "src/test/resources/rss1.xml",      // fileName
                    rssChannel1                         // rssChannel
                }
        };
    }
    
    @DataProvider
    public Object[][] testWrongDataSet1() {
        RssChannel rssChannel1 = new RssChannel();
        rssChannel1.setTitle("Хабрахабр");
        rssChannel1.setLink("https://habrahabr/");
        rssChannel1.setDescription("Интересные публикации");
        
        return new Object[][] {
                {
                    "src/test/resources/rss1.xml",      // fileName
                    rssChannel1                         // rssChannel
                }
        };
    }
    
    

    @Test(dataProvider = "testDataSet1")
    public void testXmlConverter(String testDataFileName, RssChannel testRssChannel) 
            throws XmlMappingException, IOException {
        FileReader testDataFileReader = new FileReader(testDataFileName);
        Rss rss = (Rss) xmlConverter.xmlToObject(testDataFileReader);
        assertNotNull(rss, "Rss is null\n");
        
        RssChannel rssChannel = rss.getChannel();
        assertNotNull(rssChannel, "RssChannel is null\n");
        
        assertEquals(rssChannel.getTitle(), testRssChannel.getTitle(), "Titles do not match\n");
        assertEquals(rssChannel.getLink(), testRssChannel.getLink(), "Links do not match\n");
        assertEquals(rssChannel.getDescription(), testRssChannel.getDescription(), "Descriptions do not match\n");
        
//        StringWriter xmlStringWriter = new StringWriter();
//        xmlConverter.objectToXml(rss, xmlStringWriter);
//        assertNotNull(xmlStringWriter, "XmlStringWriter is null\n");
//        String expectedXml = new String(Files.readAllBytes(Paths.get(testDataFileName)));
//        assertEquals(xmlStringWriter.toString().trim(), expectedXml, "Xml strings do not match\n");
    }

    
    @Test(dataProvider = "testWrongDataSet1")
    public void testWrongXmlConverter(String testDataFileName, RssChannel testRssChannel) 
            throws XmlMappingException, IOException {
        FileReader testDataFileReader = new FileReader(testDataFileName);
        Rss rss = (Rss) xmlConverter.xmlToObject(testDataFileReader);
        assertNotNull(rss, "Rss is null\n");
        
        RssChannel rssChannel = rss.getChannel();
        assertNotNull(rssChannel, "RssChannel is null\n");
        
        assertNotEquals(rssChannel.getTitle(), testRssChannel.getTitle(), "Titles must not match\n");
        assertNotEquals(rssChannel.getLink(), testRssChannel.getLink(), "Links must not match\n");
        assertNotEquals(rssChannel.getDescription(), testRssChannel.getDescription(), "Descriptions must not match\n");
    }
    
}
