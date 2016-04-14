/**
 * 
 */
package org.push.simplefeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * @author push
 *
 */
@Entity
@Table(name = "user_feed_tabs")
public class FeedTabEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "feed_item_id")
    @NotNull
    private FeedItemEntity feedItem;
    
    @Column(name = "prev_tab_id")
    @NotNull
    private Long prevTabId;

    
    public FeedTabEntity() { }
    
    public FeedTabEntity(UserEntity user, FeedItemEntity feedItem){
        this.user = user;
        this.feedItem = feedItem;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    
    public FeedItemEntity getFeedItem() {
        return feedItem;
    }

    public void setFeedItem(FeedItemEntity feedItem) {
        this.feedItem = feedItem;
    }

    
    public Long getPrevTabId() {
        return prevTabId;
    }

    public void setPrevTabId(Long prevTabId) {
        this.prevTabId = prevTabId;
    }

    
    
    @Override
    public String toString() {
        return "FeedTabEntity [id=" + id + ", user.id=" + user.getId() + ", feedItem.id="
                + feedItem.getId() + ", prevTabId=" + prevTabId + "]";
    }

    public boolean equals(FeedTabEntity e) {
        return (this.id.equals(e.getId()));
    }
}
