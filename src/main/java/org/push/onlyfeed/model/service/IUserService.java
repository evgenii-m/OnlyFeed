/**
 * 
 */
package org.push.onlyfeed.model.service;

import org.push.onlyfeed.model.entity.UserEntity;

/**
 * @author push
 *
 */
public interface IUserService {
    void save(UserEntity user);
    void delete(UserEntity user);
    UserEntity findById(Long id);
    UserEntity findByEmail(String email);
    UserEntity findByEmailAndLoadFeedTabs(String email);
}
