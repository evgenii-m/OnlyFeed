/**
 * 
 */
package org.push.simplefeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.*;


/**
 * @author push
 *
 */
@Entity
@Table(name = "feed_sources")
public class FeedSourceEntity {
    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 100;
    public static final int URL_MAX_SIZE = 256;
    public static final int LOGO_URL_MAX_SIZE = 256;
    public static final int DESCRIPTION_MAX_SIZE = 1000;

    public static final String NAME_EMPTY_ERROR_CODE = "validation.FeedSourceEntity.name.NotEmpty";
    public static final String NAME_SIZE_ERROR_CODE = "validation.FeedSourceEntity.name.Size";
    public static final String URL_EMPTY_ERROR_CODE = "validation.FeedSourceEntity.url.NotEmpty";
    public static final String URL_INVALID_ERROR_CODE = "validation.FeedSourceEntity.url.InvalidURL";
    public static final String URL_SIZE_ERROR_CODE = "validation.FeedSourceEntity.url.Size";
    public static final String URL_UNSUPPORTED_ERROR_CODE = "validation.FeedSourceEntity.url.UnsupportedFeedSource";
    public static final String LOGO_URL_EMPTY_ERROR_CODE = "validation.FeedSourceEntity.logoUrl.NotEmpty";
    public static final String LOGO_URL_INVALID_ERROR_CODE = "validation.FeedSourceEntity.logoUrl.InvalidURL";
    public static final String LOGO_URL_SIZE_ERROR_CODE = "validation.FeedSourceEntity.logoUrl.Size";
    public static final String DESCRIPTION_SIZE_ERROR_CODE = "validation.FeedSourceEntity.logoUrl.NotEmpty";
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "feedSource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FeedItemEntity> feedItemList;
    
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
        
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }


    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public List<FeedItemEntity> getFeedItemList() {
        return feedItemList;
    }
    
    public void setFeedItemList(List<FeedItemEntity> feedItemList) {
        this.feedItemList = feedItemList;
    }
    
//    public void addFeedItems(List<FeedItemEntity> feedItems) {
//        feedItemList.addAll(feedItems);
//    }
    
    
    @Override
    public String toString() {
        return "FeedSourceEntity [id=" + id + ", name=" + name + ", url=" + url
                + ", logoUrl=" + logoUrl + ", description=" + description
                + ", feedItemList.size=" + feedItemList.size() + "]";
    }
    
}
