/**
 * 
 */
package org.push.simplefeed.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author push
 *
 */
@Entity
@Table(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "role")
    private String role;
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<UserEntity> users;
    
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    
    public List<UserEntity> getUsers() {
        return users;
    }
    
    public void setUsers(List<UserEntity> userRoles) {
        this.users = userRoles;
    }
    
    
}
