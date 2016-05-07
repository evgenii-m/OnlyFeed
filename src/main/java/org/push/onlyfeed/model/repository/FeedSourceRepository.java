/**
 * 
 */
package org.push.onlyfeed.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.push.onlyfeed.model.entity.FeedSourceEntity;
import org.push.onlyfeed.model.entity.UserEntity;


/**
 * @author push
 *
 */
public interface FeedSourceRepository extends JpaRepository<FeedSourceEntity, Long> {
    List<FeedSourceEntity> findByUser(UserEntity user);
    FeedSourceEntity findByUserAndUrl(UserEntity user, String url);
}
