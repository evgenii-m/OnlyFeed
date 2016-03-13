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
    private String link;
    private Date publishedDate;
    private String author;
    private String imageUrl;
    private String briefDescription;
    private FeedSourceEntity feedSource;
    
    
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


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getBriefDescription() {
        return briefDescription;
    }
    
    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }
    

    public FeedSourceEntity getFeedSource() {
        return feedSource;
    }

    public void setFeedSource(FeedSourceEntity feedSource) {
        this.feedSource = feedSource;
    }

    

    @Override
    public String toString() {
        return "FeedItemEntity [id=" + id + ", title=" + title
                + ", description=" + description + ", link=" + link
                + ", publishedDate=" + publishedDate + ", author=" + author
                + ", feedSource=" + feedSource + "]";
    }

}
