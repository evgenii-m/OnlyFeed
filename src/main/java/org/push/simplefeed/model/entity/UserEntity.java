/**
 * 
 */
package org.push.simplefeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.apache.commons.collections4.list.TreeList;
import org.hibernate.validator.constraints.*;
import org.push.simplefeed.model.entity.types.*;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author push
 *
 */
@Entity
@Table(name = "users")
public class UserEntity {
    public static final String DEFAULT_PICTURE_NAME = "no_picture_3.jpg";
        
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @Size(min = 2, max = 100, message = "{validation.lengthRange}")
    @NotNull
    private String name;
    
    @Column(name = "password")
    @Size(min = 6, max = 60, message = "{validation.lengthRange}")
    @NotNull
    private String password;
    
    @Column(name = "email")
    @Size(min = 3, max = 100, message = "{validation.lengthRange}")
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
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
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



    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", name=" + name + ", password=" + password 
                + ", email=" + email + ", pictureUrl=" + pictureUrl + ", enabled=" + enabled 
                + ", feedViewType=" + feedViewType + ", feedSortingType=" + feedSortingType 
                + ", feedFilterType=" + feedFilterType + ", roles=" + roles + "]";
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
