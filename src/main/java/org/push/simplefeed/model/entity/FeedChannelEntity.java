/**
 * 
 */
package org.push.simplefeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * @author push
 *
 */
@Entity
@Table(name = "channels")
public class FeedChannelEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @Size(min = 1, max = 100, message = "{validation.name.Size.message}")
    private String name;
    
    @Column(name = "url")
    @NotEmpty(message = "{validation.lastname.NotEmpty.message}")
    @Size(max = 255, message = "{validation.url.Size.message}")
    private String url;
    
    
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

    
    @Override
    public String toString() {
        return "FeedChannelEntity [id=" + id + ", name=" + name + ", url=" + url + "]";
    }
    
}
