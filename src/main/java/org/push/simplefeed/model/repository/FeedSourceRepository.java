/**
 * 
 */
package org.push.simplefeed.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.push.simplefeed.model.entity.FeedSourceEntity;


/**
 * @author push
 *
 */
public interface FeedSourceRepository extends JpaRepository<FeedSourceEntity, Long> {
        
}
