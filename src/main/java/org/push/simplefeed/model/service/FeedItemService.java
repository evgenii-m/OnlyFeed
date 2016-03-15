/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.repository.FeedSourceRepository;
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
    private static String RSS_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z";
    private static String IMG_TAG_PATTERN = "<img .*src=\".+\\.(jpeg|jpg|bmp|gif|png)\".*/>";
    private static String BRIEF_DESC_PATTERN = "<.*?(/>|>)";  // TODO: modify for block only HTML tags ( strings as "< str >" musn't block)
    
    private static Logger logger = LogManager.getLogger(FeedItemService.class);
    private FeedSourceRepository feedSourceRepository;
    private RssService rssService;
    

    @Autowired
    public void setFeedSourceRespository(FeedSourceRepository feedSourceRepository) {
        this.feedSourceRepository = feedSourceRepository;
    }

    @Autowired
    public void setRssService(RssService rssService) {
        this.rssService = rssService;
    }
    
    
    private FeedItemEntity formFeedItem(RssChannelItem rssItem) {
        FeedItemEntity feedItem = new FeedItemEntity();
        feedItem.setTitle(rssItem.getTitle());
        feedItem.setLink(rssItem.getLink());
        feedItem.setAuthor(rssItem.getAuthor());
        feedItem.setDescription(rssItem.getDescription());
        
        // TODO: if no images on description, then set image url from FeedSourceEntity
        Pattern pattern = Pattern.compile(IMG_TAG_PATTERN);
        Matcher matcher = pattern.matcher(rssItem.getDescription());
        if (matcher.find()) {
            String imgTagStr = matcher.group();
            int imgUrlBeginIndex = imgTagStr.indexOf("src=\"") + 5;
            int imgUrlEndIndex = imgTagStr.indexOf("\"", imgUrlBeginIndex);
            String imgUrl = imgTagStr.substring(imgUrlBeginIndex, imgUrlEndIndex);
            feedItem.setImageUrl(imgUrl);
        }
        
        String briefDescription = rssItem.getDescription().replaceAll(BRIEF_DESC_PATTERN , "");
        feedItem.setBriefDescription(briefDescription);
        
        Date rssPubDate;
        try {
            SimpleDateFormat rssDateFormat = new SimpleDateFormat(RSS_DATE_PATTERN, Locale.ENGLISH);
            rssPubDate = rssDateFormat.parse(rssItem.getPubDate());
        } catch (ParseException e) {
            logger.error("RSS published date parse exception! " + rssItem);
            e.printStackTrace();
            rssPubDate = new Date();
        }
        feedItem.setPublishedDate(rssPubDate);
        
        return feedItem;
    }
    
    
    @Override
    public List<FeedItemEntity> getAll() {
        List<FeedItemEntity> feedItemList = new ArrayList<>();
        List<FeedSourceEntity> feedSourceList = feedSourceRepository.findAll();
        for (FeedSourceEntity feedSource : feedSourceList) {
            feedItemList.addAll(getFromSource(feedSource));
        }
        return feedItemList;
    }
    
    
    @Override
    public List<FeedItemEntity> getFromSource(FeedSourceEntity feedSource) {
        List<FeedItemEntity> feedItemList = new ArrayList<>();
        try {
            List<RssChannelItem> rssItemList = rssService.getItems(feedSource.getUrl());
            for (RssChannelItem rssItem : rssItemList) {
                FeedItemEntity feedItem = formFeedItem(rssItem);
                feedItem.setFeedSource(feedSource);
                feedItemList.add(feedItem);                    
            }
        } catch (XmlMappingException | IOException e1) {
            logger.fatal("Exception when get items from RSS service! RSS source url - " 
                    + feedSource.getUrl() + ". " + rssService);
            e1.printStackTrace();
        }
        return feedItemList;
    }
    
}
