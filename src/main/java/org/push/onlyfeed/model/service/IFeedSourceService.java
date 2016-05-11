/**
 * 
 */
package org.push.onlyfeed.model.service;

import java.util.Date;
import java.util.List;

import org.push.onlyfeed.model.entity.FeedSampleEntity;
import org.push.onlyfeed.model.entity.FeedSourceEntity;
import org.push.onlyfeed.model.entity.UserEntity;


/**
 * @author push
 *
 */
public interface IFeedSourceService {
    void save(FeedSourceEntity feedSource, UserEntity user);
    boolean delete(Long id);
    List<FeedSourceEntity> findAll();
    List<FeedSourceEntity> findByUser(UserEntity user);
    FeedSourceEntity findById(Long id);
    FeedSourceEntity findByUserAndUrl(UserEntity user, String url);
    FeedSourceEntity getBlank();
    boolean fillBlank(FeedSourceEntity feedSource);
    boolean isSupported(String feedSourceUrl);
    boolean refresh(FeedSourceEntity feedSource, Date relevantDate);
    boolean refresh(List<FeedSourceEntity> feedSources, Date relevantDate);
    List<FeedSampleEntity> getFeedSamples();
}
