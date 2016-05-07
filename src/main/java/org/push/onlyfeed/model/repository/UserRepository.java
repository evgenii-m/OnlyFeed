/**
 * 
 */
package org.push.onlyfeed.model.repository;

import org.push.onlyfeed.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author push
 *
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName(String name);
    UserEntity findByEmail(String email);
}
