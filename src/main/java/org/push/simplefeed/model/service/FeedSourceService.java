/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.repository.FeedSourceRepository;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.push.simplefeed.util.xml.rsstypes.RssChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author push
 *
 */
@Repository
@Transactional
@Service
public class FeedSourceService implements IFeedSourceService {
    private static Logger logger = LogManager.getLogger(FeedSourceService.class);
    private FeedSourceRepository feedSourceRepository;
    private RssService rssService;

    
    @Autowired
    public void setFeedSourceRepository(FeedSourceRepository feedSourceRepository) {
        this.feedSourceRepository = feedSourceRepository;
    }

    @Autowired
    public void setRssService(RssService rssService) {
        this.rssService = rssService;
    }
    
    

    @Override
    public void save(FeedSourceEntity feedSource) {
        feedSourceRepository.save(feedSource);
    }


    @Override
    @Transactional(readOnly = true)
    public FeedSourceEntity findById(Long id) {
        return feedSourceRepository.findOne(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<FeedSourceEntity> getAll() {
        return feedSourceRepository.findAll();
    }
    
    @Override
    public void formFeedSource(FeedSourceEntity feedSource) {
        try {
            RssChannel rssChannel = rssService.getChannel(feedSource.getUrl());
            feedSource.setName(rssChannel.getTitle());
            feedSource.setLogoUrl(rssChannel.getImage().getUrl());
            feedSource.setDescription(rssChannel.getDescription());
        } catch (XmlMappingException | IOException e) {
            logger.fatal("Exception when form FeedSource from RSS service! RSS source url - " 
                    + feedSource.getUrl() + ". " + rssService);
            e.printStackTrace();
        }
    }
    
}
