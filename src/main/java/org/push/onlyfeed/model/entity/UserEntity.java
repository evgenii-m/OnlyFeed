/**
 * 
 */
package org.push.onlyfeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.apache.commons.collections4.list.TreeList;
import org.hibernate.validator.constraints.*;
import org.push.onlyfeed.model.entity.types.*;;


/**
 * @author push
 *
 */
@Entity
@Table(name = "users")
public class UserEntity {
    public static final String DEFAULT_PICTURE_NAME = "no_picture_3.jpg";
    public static final Integer DEFAULT_NEWS_STORAGE_TIME = 168;
        
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @Size(min = 2, max = 100, message = "{validation.lengthRange}")
    @NotNull
    private String name;
    
    @Column(name = "password")
    @Size(min = 6, max = 64, message = "{validation.lengthRange}")
    @NotNull
    private String password;
    
    @Column(name = "email")
    @Size(min = 3, max = 64, message = "{validation.lengthRange}")
    @Email(message = "{validation.email}")
    @NotNull
    private String email;
    
    @Column(name = "picture_url")
    @Size(min = 1, max = 512, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    @NotNull
    private String pictureUrl;
    
    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "feed_view_type")
    @Enumerated(EnumType.ORDINAL)
    private FeedViewType feedViewType;
    
    @Column(name = "feed_sorting_type")
    @Enumerated(EnumType.ORDINAL)
    private FeedSortingType feedSortingType;

    @Column(name = "feed_filter_type")
    @Enumerated(EnumType.ORDINAL)
    private FeedFilterType feedFilterType;
    
    @Column(name = "feed_panel_pos")
    private boolean feedPanelPosition;   // true = pinned panel, false = unpinned panel
    
    @Column(name = "news_storage_time_hours")
    private Integer newsStorageTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id DESC")
    private List<FeedSourceEntity> feedSources = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedTabEntity> feedTabs = new TreeList<>();
    
    
    
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
    
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getPictureUrl() {
        return pictureUrl;
    }
    
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    
    
    public boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
        
    public FeedViewType getFeedViewType() {
        return feedViewType;
    }

    public void setFeedViewType(FeedViewType feedViewType) {
        this.feedViewType = feedViewType;
    }

    public FeedSortingType getFeedSortingType() {
        return feedSortingType;
    }

    public void setFeedSortingType(FeedSortingType feedSortingType) {
        this.feedSortingType = feedSortingType;
    }

    public FeedFilterType getFeedFilterType() {
        return feedFilterType;
    }

    public void setFeedFilterType(FeedFilterType feedFilterType) {
        this.feedFilterType = feedFilterType;
    }
    
    public Integer getNewsStorageTime() {
        return newsStorageTime;
    }

    public void setNewsStorageTime(Integer newsStorageTime) {
        this.newsStorageTime = newsStorageTime;
    }
    
    public Date getNewsRelevantDate() {
        // news storage time specified in hours - need cast to milliseconds (1h = 3600000ms)
        Date currentDate = new Date();
        return new Date(currentDate.getTime() - (newsStorageTime * 3600000));        
    }

    public boolean getFeedPanelPosition() {
        return feedPanelPosition;
    }

    public void setFeedPanelPosition(boolean feedPanelPosition) {
        this.feedPanelPosition = feedPanelPosition;
    }
    

    public Set<RoleEntity> getRoles() {
        return roles;
    }
 
    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
    
    
    public List<FeedSourceEntity> getFeedSources() {
        return feedSources;
    }
    
    public void setFeedSources(List<FeedSourceEntity> feedSources) {
        this.feedSources = feedSources;
    }
        
    
    public List<FeedTabEntity> getFeedTabs() {
        return feedTabs;
    }
    
    public void setFeedTabs(List<FeedTabEntity> feedTabs) {
        this.feedTabs = feedTabs;
    }
    
    
    public void setDefaultSettings() {
        this.setEnabled(true);
        this.setFeedViewType(FeedViewType.EXTENDED);
        this.setFeedSortingType(FeedSortingType.NEWEST_FIRST);
        this.setFeedFilterType(FeedFilterType.ALL);
        this.setNewsStorageTime(DEFAULT_NEWS_STORAGE_TIME);
        this.setFeedPanelPosition(false);
    }



    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", name=" + name + ", password=" + password 
                + ", email=" + email + ", pictureUrl=" + pictureUrl + ", enabled=" + enabled 
                + ", feedViewType=" + feedViewType + ", feedSortingType=" + feedSortingType 
                + ", feedFilterType=" + feedFilterType + ", newsStorageTime=" + newsStorageTime 
                + ", roles=" + roles + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserEntity other = (UserEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
