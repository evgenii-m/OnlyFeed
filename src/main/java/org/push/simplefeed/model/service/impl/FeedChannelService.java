/**
 * 
 */
package org.push.simplefeed.model.service.impl;

import java.util.List;

import org.push.simplefeed.model.entity.FeedChannelEntity;
import org.push.simplefeed.model.repository.FeedChannelRepository;
import org.push.simplefeed.model.service.IFeedChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author push
 *
 */
@Repository
@Transactional
@Service
public class FeedChannelService implements IFeedChannelService {
    private FeedChannelRepository feedChannelRepository;

    
    @Autowired
    public void setFeedChannelRepository(FeedChannelRepository feedChannelRepository) {
        this.feedChannelRepository = feedChannelRepository;
    }
    

    @Override
    public void save(FeedChannelEntity feedChannel) {
        feedChannelRepository.save(feedChannel);
    }


    @Override
    @Transactional(readOnly = true)
    public FeedChannelEntity findById(Long id) {
        return feedChannelRepository.findOne(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<FeedChannelEntity> getAll() {
        return feedChannelRepository.findAll();
    }
}
