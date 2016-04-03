/**
 * 
 */
package org.push.simplefeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;


/**
 * @author push
 *
 */
@Entity
@Table(name = "feed_sources")
public class FeedSourceEntity {
    public static final String DEFAULT_LOGO_URL = "http://localhost:8080/SimpleFeed/resources/img/no_logo.gif";
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @Size(min = 2, max = 100, message = "{validation.lengthRange}")
    @NotNull
    private String name;
    
    @Column(name = "url")
    @Size(min = 1, max = 256, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    @NotNull
    private String url;
    
    @Column(name = "logo_url")
    @Size(min = 1, max = 256, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    private String logoUrl;
    
    @Column(name = "description")
    @Size(min = 1, max = 1000, message = "{validation.lengthRange}")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    // TODO: replace FetchType.EAGER on FetchType.LAZY
    @OneToMany(mappedBy = "feedSource", cascade = CascadeType.ALL)
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
        if (logoUrl == null) {
            logoUrl = DEFAULT_LOGO_URL;
        }
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
    
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
        
    
    @Override
    public String toString() {
        return "FeedSourceEntity [id=" + id + ", name=" + name + ", url=" + url
                + ", logoUrl=" + logoUrl + ", description=" + description
                + ", user=" + user + "]";
    }
    
}
