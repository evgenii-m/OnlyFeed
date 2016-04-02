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
@Table(name = "users")
public class UserEntity {
    public static final String DEFAULT_PICTURE_URL = "http://localhost:8080/SimpleFeed/resources/img/no_picture.jpg";
        
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @Size(min = 2, max = 100, message = "{validation.lengthRange}")
    @NotNull
    private String name;
    
    @Column(name = "password")
    @Size(min = 6, max = 50, message = "{validation.lengthRange}")
    @NotNull
    private String password;
    
    @Column(name = "email")
    @Size(min = 3, max = 100, message = "{validation.lengthRange}")
    @Email(message = "{validation.email}")
    @NotNull
    private String email;
    
    @Column(name = "picture_url")
    @Size(min = 1, max = 256, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    @NotNull
    private String pictureUrl;
    
    @Column(name = "enabled")
    private boolean enabled;
    
    @ManyToMany
    @JoinTable(name = "user_roles")
    private List<RoleEntity> roles;

    
    
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
        if (pictureUrl == null) {
            pictureUrl = DEFAULT_PICTURE_URL;
        }
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
    
    
    public List<RoleEntity> getRoles() {
        return roles;
    }
 
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    
    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", name=" + name + ", password="
                + password + ", email=" + email + ", pictureUrl=" + pictureUrl + 
                ", enabled=" + enabled + ", roles=" + roles + "]";
    } 
       
}
