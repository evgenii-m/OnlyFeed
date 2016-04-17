/**
 * 
 */
package org.push.simplefeed.model.service;

import org.push.simplefeed.model.entity.UserEntity;

/**
 * @author push
 *
 */
public interface IUserService {
    void save(UserEntity user);
    void delete(Long id);
    UserEntity findById(Long id);
    UserEntity findByEmail(String email);
    UserEntity findByEmailAndLoadFeedTabs(String email);
}
