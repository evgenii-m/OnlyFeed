/**
 * 
 */
package org.push.takerss.model.service;

import java.util.List;

import org.push.takerss.model.entity.RssChannelEntity;


/**
 * @author push
 *
 */
public interface IRssChannelService {
    void save(RssChannelEntity rssChannel); 
    RssChannelEntity findById(Long id);
    List<RssChannelEntity> getAll();
}
