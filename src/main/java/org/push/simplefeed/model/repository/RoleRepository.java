/**
 * 
 */
package org.push.simplefeed.model.repository;

import org.push.simplefeed.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author push
 *
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}
