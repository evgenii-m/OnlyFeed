/**
 * 
 */
package org.push.onlyfeed.model.service;


import java.util.UUID;

import org.push.onlyfeed.model.entity.ResetPasswordRequest;
import org.push.onlyfeed.model.repository.ResetPasswordRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author push
 *
 */
@Service
@Transactional
public class ResetPasswordRequestService implements IResetPasswordRequestService {
    private ResetPasswordRequestRepository resetPasswordRequestRepository;
    
    @Autowired
    public void setResetPasswordRequestRepository(ResetPasswordRequestRepository resetPasswordRequestRepository) {
        this.resetPasswordRequestRepository = resetPasswordRequestRepository;
    }

    
    @Override
    public ResetPasswordRequest saveRequest(String email) {
        UUID randomUuid = UUID.randomUUID();
        String token = randomUuid.toString().replaceAll("-", "");
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(token, email);
        return resetPasswordRequestRepository.save(resetPasswordRequest);
    }

    
    @Override
    @Transactional(readOnly = true)
    public ResetPasswordRequest findByToken(String token) {
        return resetPasswordRequestRepository.findOne(token);
    }


    @Override
    public void deleteRequest(String email) {
        resetPasswordRequestRepository.deleteByEmail(email);
    }
    
}
