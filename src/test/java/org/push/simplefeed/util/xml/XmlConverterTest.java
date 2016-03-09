/**
 * 
 */
package org.push.simplefeed.util.xml;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.*;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.push.simplefeed.util.xml.rsstypes.*;


/**
 * @author push
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class XmlConverterTest {
    @Autowired
    private XmlConverter xmlConverter;
    private static final List<String> rssUrlDataSet = new ArrayList<>();
    private static final List<RssChannel> rssChannelDataSet = new ArrayList<>();
    private static final List<String> xmlStringDataSet = new ArrayList<>();
    
    
    @BeforeClass
    public static void setTestDataSet() {
        rssUrlDataSet.add("https://habrahabr.ru/rss/interesting/");
        
        RssChannel rssChannel = new RssChannel();
        rssChannel.setTitle("Хабрахабр / Интересные публикации");
        rssChannel.setLink("https://habrahabr.ru/");
        rssChannel.setDescription("Интересные публикации на Хабрахабре");
        rssChannelDataSet.add(rssChannel);
        
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<channel>"
                    + "<title>Хабрахабр / Интересные публикации</title>"
                    + "<link>https://habrahabr.ru/</link>"
                    + "<description>Интересные публикации на Хабрахабре</description>"
                + "</channel>";
        xmlStringDataSet.add(xmlString);
    }
    
    
    /**
     * Test method for {@link org.push.simplefeed.util.XmlConverter#toXml(java.lang.Object, java.io.File)}.
     * @throws IOException 
     * @throws XmlMappingException 
     */
    @Test
    public void testObjectToXml() throws XmlMappingException, IOException {
        for (int i = 0; i < rssChannelDataSet.size(); ++i) {
            RssChannel rssChannel = rssChannelDataSet.get(i);
            String xmlString = xmlStringDataSet.get(i);
            StringWriter stringWriter = new StringWriter();
            xmlConverter.objectToXml(rssChannel, stringWriter);
            assertNotNull("StringWriter object is null", stringWriter);
            assertEquals("Output XML and test data are not same", stringWriter.toString().trim(), xmlString);
        }
    }
    

    /**
     * Test method for {@link org.push.simplefeed.util.XmlConverter#fromXml(java.lang.String)}.
     * @throws IOException 
     * @throws XmlMappingException 
     */
    @Test
    public void testXmlToObject() throws XmlMappingException, IOException {
        for (int i = 0; i < rssUrlDataSet.size(); ++i) {
            Rss rss = (Rss) xmlConverter.xmlToObject(rssUrlDataSet.get(i));
            assertTrue("Rss Channel empty", rss.getChannel().size() > 0);
            for (int j = 0; j < rss.getChannel().size(); ++j) {
                RssChannel rssChannel = rss.getChannel().get(i);
                assertEquals("\"title\" not equal", rssChannel.getTitle(), rssChannelDataSet.get(0).getTitle());
                assertEquals("\"link\" not equal", rssChannel.getLink(), rssChannelDataSet.get(0).getLink());
                assertEquals("\"description\" not equal", rssChannel.getDescription(), rssChannelDataSet.get(0).getDescription());
            }
        }
    }
    
}
