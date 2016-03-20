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
    List<FeedItemEntity> save(List<RssChannelItem> rssItemList, FeedSourceEntity feedSource);
    
    List<FeedItemEntity> findAll();
    FeedItemEntity findById(Long id);
    List<FeedItemEntity> findByFeedSource(FeedSourceEntity feedSource);
    List<FeedItemEntity> findLatest(int count);
}
