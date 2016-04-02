/**
 * 
 */
package org.push.simplefeed.model.service;

import org.push.simplefeed.model.entity.UserEntity;
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
    
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }
    
    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
    
    
    
    @Override
    public UserEntity findOne(Long id) {
        return userRepository.findOne(id);
    }
    
    @Override
    public UserEntity findOne(String email) {
        return userRepository.findByEmail(email);
    }
    
}
