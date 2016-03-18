/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedSourceEntity;


/**
 * @author push
 *
 */
public interface IFeedSourceService {
    void save(FeedSourceEntity feedSource);
    void delete(Long id);

    List<FeedSourceEntity> findAll();
    FeedSourceEntity findById(Long id);
    
    FeedSourceEntity getBlank();
    void fillBlank(FeedSourceEntity feedSource);
    boolean isSupported(String feedSourceUrl);
    
    void refresh(FeedSourceEntity feedSource);
    void refreshAll();
}
