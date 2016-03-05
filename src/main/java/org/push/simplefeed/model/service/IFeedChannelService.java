/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedChannelEntity;


/**
 * @author push
 *
 */
public interface IFeedChannelService {
    void save(FeedChannelEntity feedChannel); 
    FeedChannelEntity findById(Long id);
    List<FeedChannelEntity> getAll();
}
