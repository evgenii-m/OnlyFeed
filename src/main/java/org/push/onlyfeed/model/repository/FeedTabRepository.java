/**
 * 
 */
package org.push.onlyfeed.model.repository;

import java.util.List;

import org.push.onlyfeed.model.entity.FeedItemEntity;
import org.push.onlyfeed.model.entity.FeedTabEntity;
import org.push.onlyfeed.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author push
 *
 */
public interface FeedTabRepository extends JpaRepository<FeedTabEntity, Long> {
    List<FeedTabEntity> findByUser(UserEntity user);
    FeedTabEntity findByUserAndFeedItem(UserEntity user, FeedItemEntity feedItem);
    FeedTabEntity findByPrevFeedTab(FeedTabEntity prevFeedTab);
}
