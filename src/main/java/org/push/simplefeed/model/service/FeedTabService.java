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
    private IFeedItemService feedItemService;
    
    
    @Autowired
    public void setFeedTabRepository(FeedTabRepository feedTabRepository) {
        this.feedTabRepository = feedTabRepository;
    }

    @Autowired
    public void setFeedItemService(IFeedItemService feedItemService) {
        this.feedItemService = feedItemService;
    }
    
    
    @Override
    public void save(UserEntity user, FeedItemEntity feedItem) {
        FeedTabEntity feedTab = feedTabRepository.save(new FeedTabEntity(user, feedItem));
        if (feedItem.getFeedTab() == null) {
            feedItem.setFeedTab(feedTab);
        }
        if (user.getFeedTabs().contains(feedTab) != true) {
            user.getFeedTabs().add(feedTab);
        }
    }
    
    
    @Override
    public void delete(Long id) {
        FeedTabEntity feedTab = feedTabRepository.findOne(id);
        feedTab.getFeedItem().setFeedTab(null);
        feedTab.getUser().getFeedTabs().remove(feedTab);
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
