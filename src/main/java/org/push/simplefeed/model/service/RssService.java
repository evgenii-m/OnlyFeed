/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.util.xml.XmlConverter;
import org.push.simplefeed.util.xml.rsstypes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;

/**
 * @author push
 *
 */
@Service
public class RssService implements IRssService {
    private static Logger logger = LogManager.getLogger(RssService.class);
    private XmlConverter xmlConverter;
    

    @Autowired
    public void setXmlConverter(XmlConverter xmlConverter) {
        this.xmlConverter = xmlConverter;
    }
 
    
    @Override
    public List<RssChannelItem> getItems(String rssUrl) throws XmlMappingException, IOException {
        logger.debug("getItems");
        List<RssChannelItem> rssItemList = new ArrayList<>();
        RssChannel rssChannel = getChannel(rssUrl);
        rssItemList.addAll(rssChannel.getItem());
        return rssItemList;
    }

    @Override
    public RssChannel getChannel(String rssUrl) throws XmlMappingException, IOException {
        logger.debug("getChannel");
        Rss rss = (Rss) xmlConverter.xmlToObject(rssUrl);
        logger.debug("convertation end");
        return rss.getChannel();
    }

    @Override
    public boolean isRssSource(String sourceUrl) {
        try {
            Rss rss = (Rss) xmlConverter.xmlToObject(sourceUrl);
            if (rss.getChannel() == null) {
                return false;
            }
            return true;
        } catch (XmlMappingException | IOException ex) {
            return false;
        }
    }
}


