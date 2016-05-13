/**
 * 
 */
package org.push.onlyfeed.model.repository;

import org.push.onlyfeed.model.entity.ResetPasswordRequest;
import org.springframework.data.repository.CrudRepository;

/**
 * @author push
 *
 */
public interface ResetPasswordRequestRepository extends CrudRepository<ResetPasswordRequest, String> {
    void deleteByEmail(String email);
}
