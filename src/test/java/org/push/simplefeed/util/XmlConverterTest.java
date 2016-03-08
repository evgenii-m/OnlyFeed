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
import org.push.simplefeed.util.XmlConverter;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.castor.CastorMarshaller;
import org.push.simplefeed.model.entity.FeedChannelEntity;


/**
 * @author push
 *
 */
public class XmlConverterTest {
    private static XmlConverter xmlConverter;
    private List<FeedChannelEntity> testData;
    private String testDataString;
    
    
    @BeforeClass
    public static void setXmlConverter() {
        xmlConverter = new XmlConverter();
        
        CastorMarshaller marshaller = new CastorMarshaller();
        // uncomment if there is custom XML mapping scheme 
        // marshaller.setMappingLocation(mappingLocation);
        xmlConverter.setMarshaller(marshaller);
        
        CastorMarshaller unmarshaller = new CastorMarshaller();
        // uncomment if there is custom XML mapping scheme 
        // unmarshaller.setMappingLocation(mappingLocation);
        xmlConverter.setUnmarshaller(unmarshaller);
    }
    
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
