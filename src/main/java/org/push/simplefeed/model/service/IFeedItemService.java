/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;

import com.rometools.rome.feed.synd.SyndEntry;

/**
 * @author push
 *
 */
public interface IFeedItemService {
    List<FeedItemEntity> save(List<SyndEntry> syndEntries, FeedSourceEntity feedSource);
    FeedItemEntity findById(Long id);
    List<FeedItemEntity> findAll(FeedSourceEntity feedSource);
    List<FeedItemEntity> findAll(List<FeedSourceEntity> feedSources);
    List<FeedItemEntity> findPage(FeedSourceEntity feedSource, int pageIndex, int count);
    List<FeedItemEntity> findPage(final List<FeedSourceEntity> feedSources, int pageIndex, int count);
    List<FeedItemEntity> findPage(FeedSourceEntity feedSource, int pageIndex);
    List<FeedItemEntity> findPage(final List<FeedSourceEntity> feedSources, int pageIndex);
    List<FeedItemEntity> findLatest(FeedSourceEntity feedSource, int count);
    List<FeedItemEntity> findLatest(final List<FeedSourceEntity> feedSources, int count);
    List<FeedItemEntity> findLatest(FeedSourceEntity feedSource);
    List<FeedItemEntity> findLatest(final List<FeedSourceEntity> feedSources);
}
