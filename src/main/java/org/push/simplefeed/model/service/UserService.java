/**
 * 
 */
package org.push.simplefeed.model.service;

import org.push.simplefeed.model.entity.RoleEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.repository.RoleRepository;
import org.push.simplefeed.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author push
 *
 */
@Service
@Transactional
public class UserService implements IUserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    
    @Override
    public void save(UserEntity user) {
        user.setEnabled(true);
        RoleEntity role = roleRepository.findByRole("ROLE_USER");
        user.getRoles().add(role);
        userRepository.save(user);
    }
    
    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
    
    
    
    @Override
    @Transactional(readOnly = true)
    public UserEntity findById(Long id) {
        return userRepository.findOne(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
