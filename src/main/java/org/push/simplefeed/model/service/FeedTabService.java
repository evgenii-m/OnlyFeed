/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
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
        if (!userFeedTabs.isEmpty()) {
            feedTab.setPrevFeedTab(userFeedTabs.get(userFeedTabs.size()-1));
        }
        feedTabRepository.save(feedTab);
        userFeedTabs.add(feedTab);
    }
    
    
    @Override
    public void delete(FeedTabEntity feedTab) {
        List<FeedTabEntity> userFeedTabs = feedTab.getUser().getFeedTabs();
        int feedTabIndex = userFeedTabs.indexOf(feedTab);        
        if (feedTabIndex < (userFeedTabs.size()-1)) {  // if not last
            FeedTabEntity nextFeedTab = userFeedTabs.get(feedTabIndex+1);
            nextFeedTab.setPrevFeedTab(feedTab.getPrevFeedTab());
            feedTabRepository.save(nextFeedTab);
        }
        feedTabRepository.delete(feedTab);
        userFeedTabs.remove(feedTabIndex);
    }
    
    
    @Override
    public void delete(List<FeedTabEntity> feedTabs) {
        for (FeedTabEntity feedTab : feedTabs) {
            FeedTabEntity nextFeedTab = feedTabRepository.findByPrevFeedTab(feedTab);
            if (nextFeedTab != null) {
                nextFeedTab.setPrevFeedTab(feedTab.getPrevFeedTab());
                feedTabRepository.save(nextFeedTab);
            }
            feedTabRepository.delete(feedTab);
        }
    }
    
    
    @Override
    public void move(FeedTabEntity feedTab, int tabNewIndex, int tabOldIndex) {
        List<FeedTabEntity> userFeedTabs = feedTab.getUser().getFeedTabs();
        if (tabOldIndex < (userFeedTabs.size()-1)) {  // if not last
            FeedTabEntity oldNextFeedTab = userFeedTabs.get(tabOldIndex+1);
            oldNextFeedTab.setPrevFeedTab(feedTab.getPrevFeedTab());
            feedTabRepository.save(oldNextFeedTab);        
        }
        userFeedTabs.remove(tabOldIndex);
        userFeedTabs.add(tabNewIndex, feedTab);        
        if (tabNewIndex < (userFeedTabs.size()-1)) {
            FeedTabEntity newNextFeedTab = userFeedTabs.get(tabNewIndex+1);
            newNextFeedTab.setPrevFeedTab(feedTab);
            feedTabRepository.save(newNextFeedTab);
        }
        if (tabNewIndex != 0) {
            feedTab.setPrevFeedTab(userFeedTabs.get(tabNewIndex-1));
        } else {
            feedTab.setPrevFeedTab(null);
        }
        feedTabRepository.save(feedTab);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<FeedTabEntity> findByUser(UserEntity user) {
        List<FeedTabEntity> feedTabList = feedTabRepository.findByUser(user);
        if (feedTabList.isEmpty()) {
            return feedTabList;
        }
        
        List<FeedTabEntity> feedTabOrderedList = new ArrayList<>();
        feedTabOrderedList.add(IterableUtils.find(feedTabList, new Predicate<FeedTabEntity>() {
            @Override
            public boolean evaluate(FeedTabEntity feedTab) {
                return (feedTab.getPrevFeedTab() == null);
            }
        }));
        for (int i = 0; i < feedTabList.size()-1; i++) {
            final Long curFeedTabId = feedTabOrderedList.get(i).getId();
            feedTabOrderedList.add(IterableUtils.find(feedTabList, new Predicate<FeedTabEntity>() {
                @Override
                public boolean evaluate(FeedTabEntity feedTab) {
                    return (feedTab.getPrevFeedTab() == null) ? false :
                            (feedTab.getPrevFeedTab().getId().equals(curFeedTabId));
                }
            }));            
        }
        return feedTabOrderedList;
    }

    
    @Override
    @Transactional(readOnly = true)
    public FeedTabEntity findByUserAndFeedItem(UserEntity user, FeedItemEntity feedItem) {
        return feedTabRepository.findByUserAndFeedItem(user, feedItem);
    }
}
