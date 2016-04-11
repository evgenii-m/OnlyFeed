/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.UserEntity;


/**
 * @author push
 *
 */
public interface IFeedSourceService {
    void save(FeedSourceEntity feedSource, UserEntity user);
    void delete(Long id);
    List<FeedSourceEntity> findAll();
    List<FeedSourceEntity> findByUser(UserEntity user);
    FeedSourceEntity findById(Long id);
    FeedSourceEntity findByUserAndUrl(UserEntity user, String url);
    FeedSourceEntity getBlank();
    void fillBlank(FeedSourceEntity feedSource);
    boolean isSupported(String feedSourceUrl);
    void refresh(FeedSourceEntity feedSource);
    void refreshAll();
}
