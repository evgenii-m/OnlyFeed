/**
 * 
 */
package org.push.onlyfeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.URL;

/**
 * @author push
 *
 */
@Entity
@Table(name = "feed_samples")
public class FeedSampleEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @Size(min = 2, max = 100, message = "{validation.lengthRange}")
    @NotNull
    private String name;
    
    @Column(name = "url")
    @Size(min = 1, max = 512, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    @NotNull
    private String url;
    
    @Column(name = "logo_url")
    @Size(min = 1, max = 512, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    private String logoUrl;

    
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

    
    
    @Override
    public String toString() {
        return "FeedSampleEntity [id=" + id + ", name=" + name + ", url=" + url
                + ", logoUrl=" + logoUrl + "]";
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
        FeedSampleEntity other = (FeedSampleEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
