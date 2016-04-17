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
    void delete(FeedTabEntity feedTab);
    void move(FeedTabEntity feedTab, int tabNewIndex, int tabOldIndex);
    List<FeedTabEntity> findByUser(UserEntity user);
    FeedTabEntity findByUserAndFeedItem(UserEntity user, FeedItemEntity feedItem);
}
