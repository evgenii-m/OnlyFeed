/**
 * 
 */
package org.push.simplefeed.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.push.simplefeed.model.entity.FeedChannelEntity;


/**
 * @author push
 *
 */
public interface FeedChannelRepository extends JpaRepository<FeedChannelEntity, Long> {

}
