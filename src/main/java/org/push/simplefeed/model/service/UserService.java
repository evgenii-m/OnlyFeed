/**
 * 
 */
package org.push.simplefeed.model.service;

import org.push.simplefeed.model.entity.RoleEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.repository.RoleRepository;
import org.push.simplefeed.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private IFeedTabService feedTabService;
    
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Autowired
    public void setFeedTabService(IFeedTabService feedTabService) {
        this.feedTabService = feedTabService;
    }
    
    
    @Override
    public void save(UserEntity user) {
        if (user.getId() == null) {
            user.setDefaultSettings();
            RoleEntity role = roleRepository.findByRole("ROLE_USER");
            user.getRoles().add(role);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
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
    
    @Override
    @Transactional(readOnly = true)
    public UserEntity findByEmailAndLoadFeedTabs(String email) {
        UserEntity user = findByEmail(email);
        user.setFeedTabs(feedTabService.findByUser(user));
        return user;
    }
    
}
