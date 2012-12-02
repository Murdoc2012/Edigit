/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author peter
 */
public class AuthenticationPhaseListener implements PhaseListener {

    public static String USER_LOGIN_OUTCOME = "/login.xhtml";
    
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
       
        if (userExists(context)) {
            // allow processing of the requested view
            return;
        } else {            
            // send the user to the login view
            if (requestingSecureView(context)) {
                context.responseComplete();   
                try {
                    context.getExternalContext().redirect("../login.xhtml");
                    /*context.getApplication().
                            getNavigationHandler().handleNavigation(context, 
                                                                    null, 
                                                                    USER_LOGIN_OUTCOME);
                                                                    **/
                } catch (IOException ex) {
                    Logger.getLogger(AuthenticationPhaseListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
    
    private boolean userExists(FacesContext context) {
        ExternalContext extContext = context.getExternalContext();
        return (extContext.getSessionMap().containsKey(Authenticator.USER_SESSION_KEY));
    }
    
     private boolean requestingSecureView(FacesContext context) {
        ExternalContext extContext = context.getExternalContext();       
        String path = extContext.getRequestPathInfo();
        return (!"/login.xhtml".equals(path) && !"/index.xhtml".equals(path));              
    }
    
}
