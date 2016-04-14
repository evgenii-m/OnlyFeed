/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.List;

import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.entity.FeedTabEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.repository.FeedTabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author push
 *
 */
@Service
@Transactional
public class FeedTabService implements IFeedTabService {
    private FeedTabRepository feedTabRepository;
    
    
    @Autowired
    public void setFeedTabRepository(FeedTabRepository feedTabRepository) {
        this.feedTabRepository = feedTabRepository;
    }
    
    
    @Override
    public void save(FeedTabEntity feedTab) {
        List<FeedTabEntity> userFeedTabs = feedTab.getUser().getFeedTabs();
        if (userFeedTabs.size() != 0) {
            feedTab.setPrevTabId(userFeedTabs.get(userFeedTabs.size()-1).getId());
        } else {
            feedTab.setPrevTabId(-1l);
        }
        feedTabRepository.save(feedTab);
        userFeedTabs.add(feedTab);
    }
    
    
    @Override
    public void delete(Long id) {
        FeedTabEntity feedTab = feedTabRepository.findOne(id);
        List<FeedTabEntity> userFeedTabs = feedTab.getUser().getFeedTabs();
        int feedTabIndex = userFeedTabs.indexOf(feedTab);
        if (feedTabIndex != (userFeedTabs.size()-1)) {
            FeedTabEntity nextFeedTab = userFeedTabs.get(feedTabIndex+1);
            nextFeedTab.setPrevTabId(feedTab.getPrevTabId());
            feedTabRepository.save(nextFeedTab);
        }
        userFeedTabs.remove(feedTab);
        feedTabRepository.delete(id);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<FeedTabEntity> findByUser(UserEntity user) {
        return feedTabRepository.findByUser(user);
    }

    
    @Override
    @Transactional(readOnly = true)
    public FeedTabEntity findByUserAndFeedItemId(UserEntity user, FeedItemEntity feedItem) {
        return feedTabRepository.findByUserAndFeedItem(user, feedItem);
    }
}
