/**
 * 
 */
package org.push.simplefeed.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;



/**
 * @author push
 *
 */
public class XmlConverter {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }
    
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
    
    
    
    public void objectToXml(Object object, Result result) throws XmlMappingException, IOException {
        marshaller.marshal(object, result);
    }
    
    public void objectToXml(Object object, File file) throws XmlMappingException, IOException {
        objectToXml(object, new StreamResult(file));
    }
    
    public void objectToXml(Object object, Writer writer) throws XmlMappingException, IOException {
        objectToXml(object, new StreamResult(writer));
    }
    

    
    public Object xmlToObject(Source source) throws XmlMappingException, IOException {
        return unmarshaller.unmarshal(source);
    }
    
    public Object xmlToObject(File file) throws XmlMappingException, IOException {
        return xmlToObject(new StreamSource(file));
    }
    
    public Object xmlToObject(Reader reader) throws XmlMappingException, IOException {
        return xmlToObject(new StreamSource(reader));
    }
    
    public Object xmlToObject(String url) throws XmlMappingException, IOException {
        return xmlToObject(new StreamSource(url));
    }
}
