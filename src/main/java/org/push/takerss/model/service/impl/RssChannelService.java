/**
 * 
 */
package org.push.takerss.model.service.impl;

import java.util.List;

import org.push.takerss.model.service.IRssChannelService;
import org.push.takerss.model.entity.RssChannelEntity;
import org.push.takerss.model.repository.RssChannelRepository;
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
public class RssChannelService implements IRssChannelService {
    private RssChannelRepository rssChannelRepository;

    
    @Autowired
    public void setRssChannelRepository(RssChannelRepository rssChannelRepository) {
        this.rssChannelRepository = rssChannelRepository;
    }
    

    @Override
    public void save(RssChannelEntity rssChannel) {
        rssChannelRepository.save(rssChannel);
    }


    @Override
    @Transactional(readOnly = true)
    public RssChannelEntity findById(Long id) {
        return rssChannelRepository.findOne(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<RssChannelEntity> getAll() {
        return rssChannelRepository.findAll();
    }
}
