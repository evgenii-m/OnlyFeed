/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.types.FeedFilterType;
import org.push.simplefeed.model.entity.types.FeedSortingType;
import org.push.simplefeed.model.repository.FeedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rometools.rome.feed.synd.SyndEntry;

/**
 * @author push
 *
 */
@Service
@Transactional
public class FeedItemService implements IFeedItemService {
    public static final int DEFAULT_PAGE_SIZE = 10;
    private static final String IMG_TAG_PATTERN = "<img .*src=\".+\\.(jpeg|jpg|bmp|gif|png)\".*>";
    
    private static Logger logger = LogManager.getLogger(FeedItemService.class);
    private FeedItemRepository feedItemRepository;
    
    
    @Autowired
    public void setFeedItemRepository(FeedItemRepository feedItemRepository) {
        this.feedItemRepository = feedItemRepository;
    }
    
    
    
    public FeedItemEntity save(FeedItemEntity feedItem) {
        return feedItemRepository.save(feedItem);
    }

    private FeedItemEntity formFeedItem(SyndEntry syndEntry, FeedSourceEntity feedSource) {
        FeedItemEntity feedItem = new FeedItemEntity();
        feedItem.setTitle(syndEntry.getTitle());
        feedItem.setDescription(syndEntry.getDescription().getValue());
        feedItem.setLink(syndEntry.getLink());
        if (syndEntry.getAuthor() != null) {
            feedItem.setAuthor(syndEntry.getAuthor());
        }
        feedItem.setPublishedDate(syndEntry.getPublishedDate());
        
        Pattern pattern = Pattern.compile(IMG_TAG_PATTERN);
        Matcher matcher = pattern.matcher(syndEntry.getDescription().getValue());
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
    public List<FeedItemEntity> save(List<SyndEntry> syndEntries, FeedSourceEntity feedSource) {
        logger.debug("Save feed items from: " + feedSource.getUrl() + " (entriesCount=" + syndEntries.size() + ")");
        List<FeedItemEntity> feedItems = new ArrayList<>();        
        for (SyndEntry syndEntry : syndEntries) {
            if (feedItemRepository.findByFeedSourceAndLink(feedSource, syndEntry.getLink()) == null) {
                FeedItemEntity feedItem = formFeedItem(syndEntry, feedSource);
                feedItems.add(feedItem);
            } else {
//                logger.debug("Feed item already saved (feedSource.id=" + feedSource.getId()
//                        + ", feedSource.url=" + feedSource.getUrl()
//                        + ", feedItem.link=" + rssItem.getLink() + ")");
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
    public List<FeedItemEntity> findPage(final FeedSourceEntity feedSource, int pageIndex, 
            FeedSortingType feedSortingType, final FeedFilterType feedFilterType) {
        if (feedSource == null) {
            logger.debug("feedSource is null");
            return null;
        }
        
        Sort.Direction sort = (feedSortingType == FeedSortingType.NEWEST_FIRST) ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        Specification<FeedItemEntity> sp = new Specification<FeedItemEntity>() {
            @Override
            public Predicate toPredicate(Root<FeedItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("feedSource"), feedSource));
                Predicate filterCriteria = buildFeedFilterCriteria(feedFilterType, root, cb);
                if (filterCriteria != null) {
                    predicates.add(filterCriteria);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        
        PageRequest pageRequest = new PageRequest(pageIndex, DEFAULT_PAGE_SIZE, sort, "publishedDate");
        return feedItemRepository.findAll(sp, pageRequest).getContent();   
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<FeedItemEntity> findPage(final List<FeedSourceEntity> feedSources, int pageIndex, 
            FeedSortingType feedSortingType, final FeedFilterType feedFilterType) {
        if ((feedSources == null) || feedSources.isEmpty()) {
            logger.debug("feedSource is null or empty");
            return null;
        }
        
        Sort.Direction sort = (feedSortingType == FeedSortingType.NEWEST_FIRST) ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        Specification<FeedItemEntity> sp = new Specification<FeedItemEntity>() {
            @Override
            public Predicate toPredicate(Root<FeedItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(root.get("feedSource").in(feedSources));
                Predicate filterCriteria = buildFeedFilterCriteria(feedFilterType, root, cb);
                if (filterCriteria != null) {
                    predicates.add(filterCriteria);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        
        PageRequest pageRequest = new PageRequest(pageIndex, DEFAULT_PAGE_SIZE, sort, "publishedDate");
        return feedItemRepository.findAll(sp, pageRequest).getContent();   
    }
    

    private Predicate buildFeedFilterCriteria(FeedFilterType feedFilterType, Root<FeedItemEntity> root, CriteriaBuilder cb) {
        if (feedFilterType == FeedFilterType.READ) {
            return cb.equal(root.get("viewed"), true);
        } else if (feedFilterType == FeedFilterType.UNREAD) {
            return cb.equal(root.get("viewed"), false);
        } else if (feedFilterType == FeedFilterType.LATEST_DAY) {
            Date currentDate = new Date();
            Date yesterdayDate = new Date(currentDate.getTime() - 86400000);
            return cb.greaterThanOrEqualTo(root.get("publishedDate").as(Date.class), yesterdayDate);
        }
        return null;
    }
}
