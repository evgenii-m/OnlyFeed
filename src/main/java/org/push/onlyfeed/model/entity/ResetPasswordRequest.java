/**
 * 
 */
package org.push.onlyfeed.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author push
 *
 */
@Entity
@Table(name="reset_password_requests")
public class ResetPasswordRequest {
    @Id
    @Column(name = "token")
    private String token;
    
    @Column(name = "email")
    private String email;


    public ResetPasswordRequest() {
    }
    
    public ResetPasswordRequest(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    @Override
    public String toString() {
        return "ResetPasswordRequest [token=" + token + ", email=" + email + "]";
    }
    
}
