/**
 * 
 */
package org.push.simplefeed.util.xmltypes;

import javax.xml.bind.annotation.*;


/**
 * @author push
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rssChannel", 
    propOrder = {
        "title",
        "link",
        "description"
})
public class RssChannel {
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String link;
    @XmlElement(required = true)
    protected String description;
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }


    
    @Override
    public String toString() {
        return "RssChannel [title=" + title + ", link=" + link
                + ", description=" + description + "]";
    }
}
