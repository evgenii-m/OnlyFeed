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
import org.push.simplefeed.model.entity.FeedChannelEntity;


/**
 * @author push
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class XmlConverterTest {
    @Autowired
    private XmlConverter xmlConverter;
    private List<FeedChannelEntity> testData;
    private String testDataString;
    
        
    @Before
    public void setTestDataList() {        
        FeedChannelEntity feedChannel1 = new FeedChannelEntity();
        feedChannel1.setId(1l);
        feedChannel1.setName("channel ONE name");
        feedChannel1.setUrl("channel ONE url");
        
        FeedChannelEntity feedChannel2 = new FeedChannelEntity();
        feedChannel2.setId(2l);
        feedChannel2.setName("channel TWO name");
        feedChannel2.setUrl("channel TWO url");

        testData = new ArrayList<>();
        testData.add(feedChannel1);
        testData.add(feedChannel2);
        
        testDataString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    }
    
    
    /**
     * Test method for {@link org.push.simplefeed.util.XmlConverter#toXml(java.lang.Object, java.io.File)}.
     * @throws IOException 
     * @throws XmlMappingException 
     */
    @Test
    public void testObjectToXml() throws XmlMappingException, IOException {
        StringWriter stringWriter = new StringWriter();
        xmlConverter.objectToXml(testData, stringWriter);
        assertNotNull("StringWriter object is null", stringWriter);
        String xmlString = stringWriter.toString().trim();
        assertNotEquals("Output XML is Blank", xmlString, "");
        assertEquals("Output XML and test data are not same", xmlString, testDataString);
    }
    

    /**
     * Test method for {@link org.push.simplefeed.util.XmlConverter#fromXml(java.lang.String)}.
     */
    @Test
    public void testXmlToObject() {
        fail("Not yet implemented");
    }
    
}
