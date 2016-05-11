/**
 * 
 */
package org.push.onlyfeed.model.repository;

import org.push.onlyfeed.model.entity.FeedSampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author push
 *
 */
public interface FeedSampleRepository extends JpaRepository<FeedSampleEntity, Long> {
    
}
