/**
 * 
 */
package org.push.onlyfeed.model.service;

import java.util.List;

import org.push.onlyfeed.model.entity.FeedItemEntity;
import org.push.onlyfeed.model.entity.FeedTabEntity;
import org.push.onlyfeed.model.entity.UserEntity;


/**
 * @author push
 *
 */
public interface IFeedTabService {
    void save(FeedTabEntity feedTab);
    void delete(FeedTabEntity feedTab);
    void delete(List<FeedTabEntity> feedTabs);
    void delete(UserEntity user);
    void move(FeedTabEntity feedTab, int tabNewIndex, int tabOldIndex);
    List<FeedTabEntity> findByUser(UserEntity user);
    FeedTabEntity findByUserAndFeedItem(UserEntity user, FeedItemEntity feedItem);
}
