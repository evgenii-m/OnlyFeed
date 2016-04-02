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
@Table(name = "users")
public class UserEntity {
    public static final String DEFAULT_PICTURE_URL = "http://localhost:8080/SimpleFeed/resources/img/no_picture.jpg";
    
    public static final int NAME_MIN_SIZE = 2;
    public static final int NAME_MAX_SIZE = 100;
    public static final int PASSWORD_MIN_SIZE = 6;
    public static final int PASSWORD_MAX_SIZE = 50;
    public static final int EMAIL_MAX_SIZE = 100;
    public static final int PICTURE_URL_MAX_SIZE = 256;
    
    public static final String NAME_EMPTY_ERROR_CODE = "validation.UserEntity.name.NotEmpty";
    public static final String NAME_SIZE_ERROR_CODE = "validation.UserEntity.name.Size";
    public static final String PASSWORD_EMPTY_ERROR_CODE = "validation.UserEntity.password.NotEmpty";
    public static final String PASSWORD_SIZE_ERROR_CODE = "validation.UserEntity.password.Size";
    public static final String EMAIL_EMPTY_ERROR_CODE = "validation.UserEntity.email.NotEmpty";
    public static final String EMAIL_SIZE_ERROR_CODE = "validation.UserEntity.email.Size";
    public static final String EMAIL_INVALID_ERROR_CODE = "validation.UserEntity.email.InvalidEmail";
    public static final String EMAIL_ALREADY_TAKEN_ERROR_CODE = "validation.UserEntity.email.AlreadyTaken";
    public static final String PICTURE_URL_EMPTY_ERROR_CODE = "validation.UserEntity.pictureUrl.NotEmpty";
    public static final String PICTURE_URL_INVALID_ERROR_CODE = "validation.UserEntity.pictureUrl.InvalidURL";
    public static final String PICTURE_URL_SIZE_ERROR_CODE = "validation.UserEntity.pictureUrl.Size";
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "picture_url")
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
