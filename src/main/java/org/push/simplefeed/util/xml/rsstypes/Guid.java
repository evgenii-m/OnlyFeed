/**
 * 
 */
package org.push.simplefeed.util.xml.rsstypes;

import javax.xml.bind.annotation.*;

/**
 * @author push
 *
 * <p>Java class for Guid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="guid">
 *   <simpleContent>
 *     <extension base="<http://www.w3.org/2001/XMLSchema>string">
 *       <attribute name="isPermaLink" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * </pre>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "guid", propOrder = {
    "value"
})
public class Guid {
    @XmlValue
    protected String value;
    @XmlAttribute(name = "isPermaLink")
    protected Boolean isPermaLink;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the isPermaLink property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsPermaLink() {
        if (isPermaLink == null) {
            return true;
        } else {
            return isPermaLink;
        }
    }

    /**
     * Sets the value of the isPermaLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPermaLink(Boolean value) {
        this.isPermaLink = value;
    }

    
    
    @Override
    public String toString() {
        return "Guid [value=" + value + ", isPermaLink=" + isPermaLink + "]";
    }
    
}
