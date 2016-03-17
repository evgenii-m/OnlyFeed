/**
 * 
 */
package org.push.simplefeed.model.repository;

import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author push
 *
 */
public interface FeedItemRepository extends JpaRepository<FeedItemEntity, Long> {
    FeedItemEntity findByFeedSourceAndLink(FeedSourceEntity feedSource, String link);
}
