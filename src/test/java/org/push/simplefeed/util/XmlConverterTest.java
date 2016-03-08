/**
 * 
 */
package org.push.simplefeed.util;

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
import org.push.simplefeed.util.XmlConverter;
import org.push.simplefeed.util.xmltypes.*;


/**
 * @author push
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class XmlConverterTest {
    @Autowired
    private XmlConverter xmlConverter;
    private static final List<RssChannel> rssChannelDataSet = new ArrayList<>();
    private static final List<String> xmlStringDataSet = new ArrayList<>();
    
    
    @BeforeClass
    public static void setTestDataSet() {        
        RssChannel rssChannel = new RssChannel();
        rssChannel.setTitle("title");
        rssChannel.setLink("link");
        rssChannel.setDescription("description");
        rssChannelDataSet.add(rssChannel);
        
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<rssChannel>"
                    + "<title>title</title>"
                    + "<link>link</link>"
                    + "<description>description</description>"
                + "</rssChannel>";
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
//        RssChannel rssChannel = (RssChannel)xmlConverter.xmlToObject("https://habrahabr.ru/rss/interesting/");
//        assertEquals("Output XML and test data are not same", rssChannel.toString(), testDataString);        
    }
    
}
