/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author vasil
 */
@SessionScoped
public class SessionParams implements Serializable {

    private String sessionId;
    private String userId;

    public SessionParams() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SessionParams{" + "sessionId=" + sessionId + ", userId=" + userId + '}';
    }

    
    
}
