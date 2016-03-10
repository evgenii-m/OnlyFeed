/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedItemEntity;

/**
 * @author push
 *
 */
public interface IFeedItemService {
    public List<FeedItemEntity> findAll();
}
