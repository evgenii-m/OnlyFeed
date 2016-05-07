/**
 * 
 */
package org.push.onlyfeed.model.entity;

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
        RoleEntity other = (RoleEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
