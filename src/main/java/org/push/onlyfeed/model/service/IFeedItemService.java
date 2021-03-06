/**
 * 
 */
package org.push.onlyfeed.model.service;

import java.util.Date;
import java.util.List;

import org.push.onlyfeed.model.entity.FeedItemEntity;
import org.push.onlyfeed.model.entity.FeedSourceEntity;
import org.push.onlyfeed.model.entity.FeedTabEntity;
import org.push.onlyfeed.model.entity.types.FeedFilterType;
import org.push.onlyfeed.model.entity.types.FeedSortingType;

import com.rometools.rome.feed.synd.SyndEntry;

/**
 * @author push
 *
 */
public interface IFeedItemService {
    FeedItemEntity save(FeedItemEntity feedItem);
    List<FeedItemEntity> save(List<SyndEntry> syndEntries, FeedSourceEntity feedSource);
    void deleteOld(FeedSourceEntity feedSource, Date relevantDate, List<FeedTabEntity> feedTabs);
    void deleteOld(List<FeedSourceEntity> feedSources, Date relevantDate, List<FeedTabEntity> feedTabs);
    FeedItemEntity findById(Long id);
    List<FeedItemEntity> findPage(FeedSourceEntity feedSource, int pageIndex, 
            FeedSortingType feedSortingType, FeedFilterType feedFilterType);
    List<FeedItemEntity> findPage(List<FeedSourceEntity> feedSources, int pageIndex, 
            FeedSortingType feedSortingType, FeedFilterType feedFilterType);
}
