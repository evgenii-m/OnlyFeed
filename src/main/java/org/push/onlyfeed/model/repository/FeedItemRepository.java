/**
 * 
 */
package org.push.onlyfeed.model.repository;

import java.util.List;

import org.push.onlyfeed.model.entity.FeedItemEntity;
import org.push.onlyfeed.model.entity.FeedSourceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author push
 *
 */
public interface FeedItemRepository extends JpaRepository<FeedItemEntity, Long>, JpaSpecificationExecutor<FeedItemEntity> {
    List<FeedItemEntity> findByFeedSource(FeedSourceEntity feedSource);
    List<FeedItemEntity> findByFeedSource(FeedSourceEntity feedSource, Pageable pageable);
    FeedItemEntity findByFeedSourceAndLink(FeedSourceEntity feedSource, String link);
}
