/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.repository.FeedSourceRepository;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;

/**
 * @author push
 * 
 * @warning com.rometools.fetcher.FeedFetcher is deprecated
 * ROME Fetcher will be dropped in the next major version of ROME (version 2). For more information and some migration hints, 
 * please have a look at our <a href="https://github.com/rometools/rome/issues/276">detailed explanation</a>
 * 
 */
@Service
@Transactional
public class FeedSourceService implements IFeedSourceService {    
    private static Logger logger = LogManager.getLogger(FeedSourceService.class);
    private FeedSourceRepository feedSourceRepository;
    private IFeedItemService feedItemService;
    private IFeedTabService feedTabService;
    private IFeedFetchService feedFetchService;

    
    @Autowired
    public void setFeedSourceRepository(FeedSourceRepository feedSourceRepository) {
        this.feedSourceRepository = feedSourceRepository;
    }
    
    @Autowired
    public void setFeedItemService(IFeedItemService feedItemService) {
        this.feedItemService = feedItemService;
    }
    
    @Autowired
    public void setFeedTabService(IFeedTabService feedTabService) {
        this.feedTabService = feedTabService;
    }
    
    @Autowired
    public void setFeedFetchService(IFeedFetchService feedFetchService) {
        this.feedFetchService = feedFetchService;
    }
    


    @Override
    public void save(FeedSourceEntity feedSource, UserEntity user) {
        feedSource.setUser(user);
        feedSourceRepository.save(feedSource);
        if (user.getFeedSources().contains(feedSource) != true) {
            user.getFeedSources().add(feedSource);
        }
    }

    @Override
    public boolean delete(Long id) {
        FeedSourceEntity feedSource = feedSourceRepository.findOne(id);
        if (feedSource != null) {
            List<FeedTabEntity> feedTabs = new ArrayList<>();
            for (FeedTabEntity feedTab : feedTabService.findByUser(feedSource.getUser())) {
                if (feedTab.getFeedItem().getFeedSource().equals(feedSource)) {
                    feedTabs.add(feedTab);
                }
            }
            feedTabService.delete(feedTabs);
            feedSource.getUser().getFeedSources().remove(feedSource);
            feedSourceRepository.delete(id);
            return true;
        }
        return false;
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<FeedSourceEntity> findAll() {
        return feedSourceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedSourceEntity> findByUser(UserEntity user) {
        return feedSourceRepository.findByUser(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public FeedSourceEntity findById(Long id) {
        return feedSourceRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public FeedSourceEntity findByUserAndUrl(UserEntity user, String url) {
        return feedSourceRepository.findByUserAndUrl(user, url);
    }

    
    @Override
    public FeedSourceEntity getBlank() {
        FeedSourceEntity blankFeedSource = new FeedSourceEntity();
        return blankFeedSource;
    }
    
    
    @Override
    public boolean fillBlank(FeedSourceEntity feedSource) {
        SyndFeed syndFeed = feedFetchService.retrieveFeed(feedSource.getUrl());
        if (syndFeed == null) {
            logger.error("Exception when fetch feed from source (feedSource.url="
                    + feedSource.getUrl() + ")");
            return false;
        }

        feedSource.setName(syndFeed.getTitle());
        SyndImage syndImage = syndFeed.getImage();
        if ((syndImage == null) || (syndImage.getUrl() == null)) {
            feedSource.setLogoUrl(FeedSourceEntity.DEFAULT_LOGO_URL);
        } else {
            feedSource.setLogoUrl(syndImage.getUrl());
        }
        feedSource.setDescription(syndFeed.getDescription());
        return true;
    }
    

    @Override
    public boolean isSupported(String feedSourceUrl) {
        SyndFeed syndFeed = feedFetchService.retrieveFeed(feedSourceUrl);
        if (syndFeed == null) {
            logger.error("Unsupported feed source (feedSourceUrl=" + feedSourceUrl + ")");
            return false;
        }
        return true;
    }

    
    @Override
    public boolean refresh(FeedSourceEntity feedSource) {
        logger.debug("Refresh feed source (id=" + feedSource.getId() + ", name=" + feedSource.getName()
                + ", url=" + feedSource.getUrl() + ")");
        SyndFeed syndFeed = feedFetchService.retrieveFeed(feedSource.getUrl());
        if (syndFeed == null) {
            logger.error("Exception when fetch feed from source (feedSource.url="
                    + feedSource.getUrl() + ")");
            return false;
        }
        feedItemService.save(syndFeed.getEntries(), feedSource);
        logger.debug("Refresh end");
        return true;
    }
    
    
    @Override
    public boolean refresh(List<FeedSourceEntity> feedSources) {
        logger.debug("Refresh all feed sources of user (user.id=" + feedSources.get(0).getUser().getId() + ")");
        boolean refreshResult = true;
        String[] sourcesUrl = new String[feedSources.size()];
        for (int i = 0; i < feedSources.size(); ++i) {
            sourcesUrl[i] = feedSources.get(i).getUrl();
        }
        Map<String, SyndFeed> syndFeedsMap = feedFetchService.retrieveFeeds(sourcesUrl);
        for (final FeedSourceEntity feedSource : feedSources) {
            SyndFeed syndFeed = syndFeedsMap.get(feedSource.getUrl());
            if (syndFeed != null) {
                feedItemService.save(syndFeed.getEntries(), feedSource);
            } else {
                logger.error("Error occured when refresh source (feedSource.url=" + feedSource.getUrl() 
                        + "), see HttpFeedFetcherThread log for details.");
                refreshResult = false;                
            }
        }
        logger.debug("Refresh end");
        return refreshResult;
    }

}
