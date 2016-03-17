/**
 * 
 */
package org.push.simplefeed.model.service;

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
import org.push.simplefeed.model.repository.FeedItemRepository;
import org.push.simplefeed.util.xml.rsstypes.RssChannelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author push
 *
 */
@Service
public class FeedItemService implements IFeedItemService {
    private static final String RSS_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z";
    private static final String IMG_TAG_PATTERN = "<img .*src=\".+\\.(jpeg|jpg|bmp|gif|png)\".*/>";
    // TODO: modify for block only HTML tags ( strings as "< str >" musn't block)
//    private static final String BRIEF_DESC_PATTERN = "<.*?(/>|>)";
    
    private static Logger logger = LogManager.getLogger(FeedSourceService.class);
    private FeedItemRepository feedItemRepository;
    
    
    @Autowired
    public void setFeedItemRepository(FeedItemRepository feedItemRepository) {
        this.feedItemRepository = feedItemRepository;
    }
    

    
    private FeedItemEntity formFeedItem(RssChannelItem rssItem) {
        FeedItemEntity feedItem = new FeedItemEntity();
        feedItem.setTitle(rssItem.getTitle());
        feedItem.setDescription(rssItem.getDescription());
        feedItem.setLink(rssItem.getLink());
        feedItem.setAuthor(rssItem.getAuthor());

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
        
        Pattern pattern = Pattern.compile(IMG_TAG_PATTERN);
        Matcher matcher = pattern.matcher(rssItem.getDescription());
        if (matcher.find()) {
            String imgTagStr = matcher.group();
            int imgUrlBeginIndex = imgTagStr.indexOf("src=\"") + 5;
            int imgUrlEndIndex = imgTagStr.indexOf("\"", imgUrlBeginIndex);
            String imgUrl = imgTagStr.substring(imgUrlBeginIndex, imgUrlEndIndex);
            feedItem.setImageUrl(imgUrl);
        }
        
        return feedItem;
    }
    
    
    @Override
    public void save(List<RssChannelItem> rssItemList, FeedSourceEntity feedSource) {
        logger.debug("Rss items count - " + rssItemList.size());
        ArrayList<FeedItemEntity> feedItemList = new ArrayList<>();
        for (RssChannelItem rssItem : rssItemList) {
            if (feedItemRepository.findByFeedSourceAndLink(feedSource, rssItem.getLink()) == null) {
                FeedItemEntity feedItem = formFeedItem(rssItem);
                feedItem.setFeedSource(feedSource);
                feedItemList.add(feedItem);
            } else {
                logger.debug("Feed item (FeedSource: " + feedSource.getId() + ", " + 
                        feedSource.getName() + ", " + feedSource.getUrl() + "; ItemLink: " + 
                        rssItem.getLink() + ") already saved");
            }
        }
        logger.debug("Feed items count - " + feedItemList.size());
        feedItemRepository.save(feedItemList);
    } 
    

    @Override
    public List<FeedItemEntity> getAll() {
        return feedItemRepository.findAll();
    }
}
