/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.repository.FeedSourceRepository;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.push.simplefeed.util.xml.rsstypes.Image;
import org.push.simplefeed.util.xml.rsstypes.RssChannel;
import org.push.simplefeed.util.xml.rsstypes.RssChannelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author push
 *
 */
@Service
@Transactional
public class FeedSourceService implements IFeedSourceService {    
    private static Logger logger = LogManager.getLogger(FeedSourceService.class);
    private FeedSourceRepository feedSourceRepository;
    private IRssService rssService;
    private IFeedItemService feedItemService;
    private IFeedTabService feedTabService;


    @Autowired
    public void setFeedSourceRepository(FeedSourceRepository feedSourceRepository) {
        this.feedSourceRepository = feedSourceRepository;
    }

    @Autowired
    public void setRssService(IRssService rssService) {
        this.rssService = rssService;
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
            RssChannel rssChannel = rssService.getChannel(feedSource.getUrl());
            feedSource.setName(rssChannel.getTitle());
            Image rssChannelImage = rssChannel.getImage();
            if ((rssChannelImage == null) || (rssChannelImage.getUrl() == null)) {
                feedSource.setLogoUrl(FeedSourceEntity.DEFAULT_LOGO_URL);
            } else {
                feedSource.setLogoUrl(rssChannelImage.getUrl());
            }
            feedSource.setDescription(rssChannel.getDescription());
            return true;
        } catch (XmlMappingException | IOException e) {
            logger.error("Exception when form Feed Source from RSS service! (url=" 
                    + feedSource.getUrl() + ")");
            // TODO: modify for printStackTrace to logger
            e.printStackTrace();
            return false;
        }
    }
    

    @Override
    public boolean isSupported(String feedSourceUrl) {
        return rssService.isRssSource(feedSourceUrl);
    }

    
    @Override
    public boolean refresh(FeedSourceEntity feedSource) {
        logger.debug("Refresh feed source (id=" + feedSource.getId() + ", name=" + feedSource.getName()
                + ", url=" + feedSource.getUrl() + ")");
        try {
            List<RssChannelItem> rssItemList = rssService.getItems(feedSource.getUrl());
            feedItemService.save(rssItemList, feedSource);
            return true;
        } catch (XmlMappingException | IOException e) {
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
