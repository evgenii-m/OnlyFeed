/**
 * 
 */
package org.push.simplefeed.util.xml.rsstypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;


/**
 * @author push
 *
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rss", 
    propOrder = {
        "channel",
        "any"
})
public class Rss {
    @XmlElement(required = true)
    protected List<RssChannel> channel;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "version", required = true)
    protected BigDecimal version;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();
    
    
    public List<RssChannel> getChannel() {
        if (channel == null) {
            channel = new ArrayList<>();
        }
        return channel;
    }
    
    
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<>();
        }
        return any;
    }
    
    
    public BigDecimal getVersion() {
        return version;
    }
    
    public void setVersion(BigDecimal version) {
        this.version = version;
    }
    
    
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }
}
