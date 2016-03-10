/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedChannelEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.repository.FeedChannelRepository;
import org.push.simplefeed.model.service.IFeedItemService;
import org.push.simplefeed.util.xml.rsstypes.RssChannelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;

/**
 * @author push
 *
 */
@Service
public class FeedItemService implements IFeedItemService {
    private static Logger logger = LogManager.getLogger(FeedItemService.class);
    private SimpleDateFormat rssDataFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    private FeedChannelRepository feedChannelRepository;
    private RssService rssService;
    

    @Autowired
    public void setFeedChannelRespository(FeedChannelRepository feedChannelRepository) {
        this.feedChannelRepository = feedChannelRepository;
    }

    @Autowired
    public void setRssService(RssService rssService) {
        this.rssService = rssService;
    }
    
    
    @Override
    public List<FeedItemEntity> findAll() {
        List<FeedItemEntity> feedItemList = new ArrayList<>();
        List<FeedChannelEntity> feedChannelList = feedChannelRepository.findAll();
        for (FeedChannelEntity feedChannel : feedChannelList) {
            try {
                List<RssChannelItem> rssItemList = rssService.getItems(feedChannel.getUrl());
                for (RssChannelItem rssItem : rssItemList) {
                    FeedItemEntity feedItem = new FeedItemEntity();
                    feedItem.setTitle(rssItem.getTitle());
                    feedItem.setLink(rssItem.getLink());
                    feedItem.setDescription(rssItem.getDescription());
                    Date rssPubDate;
                    try {
                        rssPubDate = rssDataFormat.parse(rssItem.getPubDate());
                    } catch (ParseException e) {
                        logger.error("Rss published date parse exception! " + rssItem);
                        e.printStackTrace();
                        rssPubDate = new Date();
                    }
                    feedItem.setPublishedDate(rssPubDate);
                    feedItemList.add(feedItem);
                }
            } catch (XmlMappingException | IOException e1) {
                logger.fatal("Exception when get items from RSS service! RSS source url - " 
                        + feedChannel.getUrl() + ". " + rssService);
                e1.printStackTrace();
            }
        }
        return feedItemList;
    }
    
}
