/**
 * 
 */
package org.push.simplefeed.util.xml.rsstypes;

import javax.xml.bind.annotation.*;


/**
 * @author push
 *
 */
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rssChannelItem", propOrder = {
    "title",
    "description",
    "link",
    "category",
    "comments",
    "enclosure",
    "guid",
    "pubDate",
    "source",
    "any"
})
public class RssChannelItem {
    
}
