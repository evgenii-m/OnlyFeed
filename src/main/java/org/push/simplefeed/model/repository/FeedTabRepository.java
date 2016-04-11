/**
 * 
 */
package org.push.simplefeed.model.repository;

import java.util.List;

import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author push
 *
 */
public interface FeedTabRepository extends JpaRepository<FeedTabEntity, Long> {
    List<FeedTabEntity> findByUser(UserEntity user);
    FeedTabEntity findByUserAndFeedItem(UserEntity user, FeedItemEntity feedItem);
}
