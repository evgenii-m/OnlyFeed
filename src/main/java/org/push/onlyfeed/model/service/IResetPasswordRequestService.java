/**
 * 
 */
package org.push.onlyfeed.model.service;

import org.push.onlyfeed.model.entity.ResetPasswordRequest;


/**
 * @author push
 *
 */
public interface IResetPasswordRequestService {
    ResetPasswordRequest saveRequest(String email);
    ResetPasswordRequest findByToken(String token);
    void deleteRequest(String email);
}
