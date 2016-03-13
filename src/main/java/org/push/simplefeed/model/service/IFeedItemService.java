/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;

/**
 * @author push
 *
 */
public interface IFeedItemService {
    public List<FeedItemEntity> getAll();
    public List<FeedItemEntity> getFromSource(FeedSourceEntity feedSource);
}
