/**
 * 
 */
package org.push.simplefeed.model.repository;

import org.push.simplefeed.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author push
 *
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName(String name);
    UserEntity findByEmail(String email);
}
