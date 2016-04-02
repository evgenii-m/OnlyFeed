/**
 * 
 */
package org.push.simplefeed.model.entity;

import java.util.List;

import javax.persistence.*;

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
    @ManyToMany(mappedBy = "roles")
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
