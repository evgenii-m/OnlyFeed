/**
 * 
 */
package org.push.simplefeed.model.entity;

import java.util.Date;

//import javax.persistence.*;

/**
 * @author push
 *
 */
//@Entity
public class FeedItemEntity {
//    @Id
//    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Date publishedDate;
    private String link;
    private FeedChannelEntity feedChannel;


    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    
    public FeedChannelEntity getFeedChannel() {
        return feedChannel;
    }

    public void setFeedChannel(FeedChannelEntity feedChannel) {
        this.feedChannel = feedChannel;
    }

    
    @Override
    public String toString() {
        return "FeedItemEntity [id=" + id + ", title=" + title + ", description=" + description + 
                ", publishedDate=" + publishedDate + ", link=" + link + ", feedChannel=" + feedChannel + "]";
    }
    
}
