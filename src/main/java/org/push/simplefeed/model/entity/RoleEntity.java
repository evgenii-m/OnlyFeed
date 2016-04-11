/**
 * 
 */
package org.push.simplefeed.model.entity;

import java.util.HashSet;
import java.util.Set;

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
    
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users = new HashSet<>();
    
    
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
    
    
    public Set<UserEntity> getUsers() {
        return users;
    }
    
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }


    @Override
    public String toString() {
        return "RoleEntity [id=" + id + ", role=" + role + "]";
    }
    
}
