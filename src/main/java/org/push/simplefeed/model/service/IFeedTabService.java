/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;


/**
 * @author push
 *
 */
public interface IFeedTabService {
    void save(FeedTabEntity feedTab);
    void delete(Long id);
    List<FeedTabEntity> findByUser(UserEntity user);
    FeedTabEntity findByUserAndFeedItemId(UserEntity user, FeedItemEntity feedItem);
}
