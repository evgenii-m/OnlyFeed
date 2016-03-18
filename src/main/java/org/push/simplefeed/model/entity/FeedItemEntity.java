/**
 * 
 */
package org.push.simplefeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * @author push
 *
 */
@Entity
@Table(name = "feed_items")
public class FeedItemEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "link")
    private String link;
    @Column(name = "published_date")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date publishedDate;
    @Column(name = "author")
    private String author;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "feed_source_id")
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
    
    public String getSummary() {
        // TODO: modify for block only HTML tags (strings as "< str >" musn't block)
        return description.replaceAll("<.*?(/>|>)", "");
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
    
    public String getPublishedDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
        return dateFormat.format(publishedDate);
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
        if (imageUrl == null) {
            imageUrl = feedSource.getLogoUrl();
        }
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
                + ", imageUrl=" + imageUrl + ", feedSource=" + feedSource + "]";
    }

}
