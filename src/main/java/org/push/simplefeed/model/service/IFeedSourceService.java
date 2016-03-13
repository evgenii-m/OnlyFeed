/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedSourceEntity;


/**
 * @author push
 *
 */
public interface IFeedSourceService {
    void save(FeedSourceEntity feedSource); 
    FeedSourceEntity findById(Long id);
    List<FeedSourceEntity> getAll();
    void formFeedSource(FeedSourceEntity feedSource);
}
