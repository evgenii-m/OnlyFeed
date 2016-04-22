/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.FetcherException;
import com.rometools.fetcher.impl.FeedFetcherCache;
import com.rometools.fetcher.impl.HashMapFeedInfoCache;
import com.rometools.fetcher.impl.HttpURLFeedFetcher;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.io.FeedException;

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
@SuppressWarnings("deprecation")
public class FeedSourceService implements IFeedSourceService {    
    private static Logger logger = LogManager.getLogger(FeedSourceService.class);
    private FeedSourceRepository feedSourceRepository;
    private IFeedItemService feedItemService;
    private IFeedTabService feedTabService;
    private FeedFetcher feedFetcher;


    public FeedSourceService() {
        // TODO: replace on com.rometools.fetcher.impl.DiskFeedInfoCache (?) or establish periodic cache cleaning
        FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
        feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
    }
    
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
        try {
            SyndFeed syndFeed = feedFetcher.retrieveFeed(new URL(feedSource.getUrl()));
            feedSource.setName(syndFeed.getTitle());
            SyndImage syndImage = syndFeed.getImage();
            if ((syndImage == null) || (syndImage.getUrl() == null)) {
                feedSource.setLogoUrl(FeedSourceEntity.DEFAULT_LOGO_URL);
            } else {
                feedSource.setLogoUrl(syndImage.getUrl());
            }
            feedSource.setDescription(syndFeed.getDescription());
            return true;
        } catch (IOException | IllegalArgumentException | FeedException | FetcherException e) {
            logger.error("Exception when form Feed Source from RSS service! (url=" 
                    + feedSource.getUrl() + ")");
            // TODO: modify for printStackTrace to logger
            e.printStackTrace();
            return false;
        }
    }
    

    @Override
    public boolean isSupported(String feedSourceUrl) {
        try {
            SyndFeed syndFeed = feedFetcher.retrieveFeed(new URL(feedSourceUrl));
            return true;
        } catch (IllegalArgumentException | IOException | FeedException| FetcherException e) {
            logger.error("Unsupported feed source (feedSourceUrl=" + feedSourceUrl + ")");
            return false;
        }
    }

    
    @Override
    public boolean refresh(FeedSourceEntity feedSource) {
        logger.debug("Refresh feed source (id=" + feedSource.getId() + ", name=" + feedSource.getName()
                + ", url=" + feedSource.getUrl() + ")");
        try {
            SyndFeed syndFeed = feedFetcher.retrieveFeed(new URL(feedSource.getUrl()));
            feedItemService.save(syndFeed.getEntries(), feedSource);
            return true;
        } catch (IOException | IllegalArgumentException | FeedException | FetcherException e) {
            logger.error("Exception when fetch Feed Items from RSS service! RSS source url " 
                    + "(id=" + feedSource.getId() + ", name=" + feedSource.getName() 
                    + ", url=" + feedSource.getUrl() + ")");
            // TODO: modify for printStackTrace to logger
            e.printStackTrace();
            return false;
        }
    }
    
    
    @Override
    public boolean refresh(List<FeedSourceEntity> feedSources) {
        for (FeedSourceEntity feedSource : feedSources) {
            if (refresh(feedSource) != true)
                return false;
        }
        return true;
    }

}
