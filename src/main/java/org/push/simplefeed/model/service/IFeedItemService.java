/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.util.xml.rsstypes.RssChannelItem;

/**
 * @author push
 *
 */
public interface IFeedItemService {
    List<FeedItemEntity> save(List<RssChannelItem> rssItems, FeedSourceEntity feedSource);
    FeedItemEntity findById(Long id);
    List<FeedItemEntity> findAll(FeedSourceEntity feedSource);
    List<FeedItemEntity> findAll(List<FeedSourceEntity> feedSources);
    List<FeedItemEntity> findLatest(FeedSourceEntity feedSource, int count);
    List<FeedItemEntity> findLatest(final List<FeedSourceEntity> feedSources, int count);
}
