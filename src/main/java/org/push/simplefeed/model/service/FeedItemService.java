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

import javax.persistence.criteria.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.repository.FeedItemRepository;
import org.push.simplefeed.util.xml.rsstypes.RssChannelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author push
 *
 */
@Service
@Transactional
public class FeedItemService implements IFeedItemService {
    public static final int DEFAULT_PAGE_SIZE = 10;
    private static final String RSS_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z";
    private static final String IMG_TAG_PATTERN = "<img .*src=\".+\\.(jpeg|jpg|bmp|gif|png)\".*/>";
    
    private static Logger logger = LogManager.getLogger(FeedItemService.class);
    private FeedItemRepository feedItemRepository;
    
    
    @Autowired
    public void setFeedItemRepository(FeedItemRepository feedItemRepository) {
        this.feedItemRepository = feedItemRepository;
    }
    

    
    private FeedItemEntity formFeedItem(RssChannelItem rssItem, FeedSourceEntity feedSource) {
        FeedItemEntity feedItem = new FeedItemEntity();
        feedItem.setTitle(rssItem.getTitle());
        feedItem.setDescription(rssItem.getDescription());
        feedItem.setLink(rssItem.getLink());
        if (rssItem.getAuthor() != null) {
            feedItem.setAuthor(rssItem.getAuthor());
        }

        Date rssPubDate;
        try {
            SimpleDateFormat rssDateFormat = new SimpleDateFormat(RSS_DATE_PATTERN, Locale.ENGLISH);
            rssPubDate = rssDateFormat.parse(rssItem.getPubDate());
        } catch (ParseException e) {
            logger.error("RSS published date parse exception! (" + rssItem + ")");
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
        
        feedItem.setFeedSource(feedSource);
        return feedItem;
    }
    

    
    @Override
    public List<FeedItemEntity> save(List<RssChannelItem> rssItems, FeedSourceEntity feedSource) {
        logger.debug("Save feed items form: " + feedSource.getUrl());
        logger.debug("Source items count: " + rssItems.size());
        List<FeedItemEntity> feedItems = new ArrayList<>();        
        for (RssChannelItem rssItem : rssItems) {
            if (feedItemRepository.findByFeedSourceAndLink(feedSource, rssItem.getLink()) == null) {
                FeedItemEntity feedItem = formFeedItem(rssItem, feedSource);
                feedItems.add(feedItem);
            } else {
                logger.debug("Feed item already saved (feedSource.id=" + feedSource.getId()
                        + ", feedSource.url=" + feedSource.getUrl()
                        + ", feedItem.link=" + rssItem.getLink() + ")");
            }
        }
        feedItems = feedItemRepository.save(feedItems);
        return feedItems;
    } 



    @Override
    @Transactional(readOnly = true)
    public FeedItemEntity findById(Long id) {
        return feedItemRepository.findOne(id);
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findAll(FeedSourceEntity feedSource) {
        return feedItemRepository.findByFeedSource(feedSource);
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findAll(List<FeedSourceEntity> feedSources) {
        List<FeedItemEntity> feedItems = new ArrayList<>();
        for (FeedSourceEntity feedSource : feedSources) {
            feedItems.addAll(findAll(feedSource));
        }
        return feedItems;
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findPage(FeedSourceEntity feedSource, int pageIndex, int count) {
        if (feedSource == null) {
            logger.debug("feedSource is null");
            return null;
        }
        PageRequest pageRequest = new PageRequest(pageIndex, count, Sort.Direction.DESC, "publishedDate");
        return feedItemRepository.findByFeedSource(feedSource, pageRequest);
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findPage(final List<FeedSourceEntity> feedSources, int pageIndex, int count) {
        if ((feedSources == null) || feedSources.isEmpty()) {
            logger.debug("feedSource is null or empty");
            return null;
        }
        Specification<FeedItemEntity> sp = new Specification<FeedItemEntity>() {
            @Override
            public Predicate toPredicate(Root<FeedItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {              
                Expression<FeedSourceEntity> exp = root.get("feedSource");
                return exp.in(feedSources);
            }
        }; 
        PageRequest pageRequest = new PageRequest(pageIndex, count, Sort.Direction.DESC, "publishedDate");
        return feedItemRepository.findAll(sp, pageRequest).getContent();        
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findPage(FeedSourceEntity feedSource, int pageIndex) {
        return findPage(feedSource, pageIndex, DEFAULT_PAGE_SIZE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findPage(final List<FeedSourceEntity> feedSources, int pageIndex) {
        return findPage(feedSources, pageIndex, DEFAULT_PAGE_SIZE);        
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findLatest(FeedSourceEntity feedSource, int count) {
        return findPage(feedSource, 0, count);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findLatest(final List<FeedSourceEntity> feedSources, int count) {
        return findPage(feedSources, 0, count);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findLatest(FeedSourceEntity feedSource) {
        return findLatest(feedSource, DEFAULT_PAGE_SIZE);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findLatest(final List<FeedSourceEntity> feedSources) {
        return findLatest(feedSources, DEFAULT_PAGE_SIZE);
    }
    
}
