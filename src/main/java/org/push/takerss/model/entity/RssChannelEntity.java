/**
 * 
 */
package org.push.takerss.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;


/**
 * @author push
 *
 */
@Entity
public class RssChannelEntity {
    private Long id;
    private String url;
    private String name;
    
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    @Override
    public String toString() {
        return "RssChanelEntity " + 
            "[id: " + id + "; url: " + url + "; name: " + name + "]";
    }
}
