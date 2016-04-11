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
public class FeedTabEntity implements Comparable<FeedTabEntity> {
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
    
    @Column(name = "position")
    @NotNull
    private Integer position;

    
    public FeedTabEntity() { }
    
    public FeedTabEntity(UserEntity user, FeedItemEntity feedItem){
        this.user = user;
        this.feedItem = feedItem;
        this.position = user.getFeedTabs().size();
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

    
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    
    
    @Override
    public String toString() {
        return "FeedTabEntity [id=" + id + ", user.id=" + user.getId() + ", feedItem.id="
                + feedItem.getId() + ", position=" + position + "]";
    }

    @Override
    public int compareTo(FeedTabEntity e) {
        return this.position.compareTo(e.getPosition());
    }
    
}
