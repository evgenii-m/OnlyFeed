/**
 * 
 */
package org.push.takerss.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.push.takerss.model.entity.RssChannelEntity;


/**
 * @author push
 *
 */
public interface RssChannelRepository extends JpaRepository<RssChannelEntity, Long> {

}
