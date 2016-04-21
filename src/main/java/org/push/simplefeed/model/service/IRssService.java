/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.util.List;

import org.push.simplefeed.util.xml.rsstypes.RssChannel;
import org.push.simplefeed.util.xml.rsstypes.RssChannelItem;
import org.springframework.oxm.XmlMappingException;

/**
 * @author push
 *
 */
public interface IRssService {
    List<RssChannelItem> getItems(String rssUrl) throws XmlMappingException, IOException;
    public RssChannel getChannel(String rssUrl) throws XmlMappingException, IOException;
    public boolean isRssSource(String sourceUrl);
}
