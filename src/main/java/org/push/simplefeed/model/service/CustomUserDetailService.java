/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.push.simplefeed.model.entity.RoleEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author push
 *
 */
@Component
public class CustomUserDetailService implements UserDetailsService {
    private IUserService userService;

    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
        
    
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("User with email = '" + userEmail + "' not found!");
        }
        
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (RoleEntity role : user.getRoles()) {
            setAuths.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> authorities = new ArrayList<>(setAuths);
        return new User(user.getEmail(), user.getPassword(), user.getEnabled(), 
                true, true, true, authorities);
    }
    
}
