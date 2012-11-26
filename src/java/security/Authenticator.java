/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entities.EUser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author peter
 */
@ManagedBean(name = "authenticator")
@RequestScoped
public class Authenticator {

    public static final String USER_SESSION_KEY = "user";
    
    @PersistenceContext 
    private EntityManager em;
    
  
    @Resource 
    private UserTransaction utx;
    
  
    private String username;
    private String password;
    private String passwordv;
    private String fname;
    private String lname;   
   
    
    public Authenticator() {
    }
    
    
     public String validateUser() {   
        FacesContext context = FacesContext.getCurrentInstance();
        EUser user = getUser();
        if (user != null) {
            if (!user.getPassword().equals(password)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                           "Login Failed!",
                                           "The password specified is not correct.");
                context.addMessage(null, message);
                return null;
            }
            
            context.getExternalContext().getSessionMap().put(USER_SESSION_KEY, user);
            return "/view/home";
        } else {           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Failed!",
                    "Username '"
                    + username
                    +
                    "' does not exist.");
            context.addMessage(null, message);
            return null;
        }
    }
    
    
    public String logout() {
        HttpSession session = (HttpSession)
             FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/login.xhtml";
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordv() {
        return passwordv;
    }

    public void setPasswordv(String passwordv) {
        this.passwordv = passwordv;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    private EUser getUser() {
        try {
            EUser user = (EUser) em.createQuery("SELECT u FROM EUser u where u.username = :username").setParameter("username",username).getSingleResult();
            return user;
        } catch (NoResultException nre) {
            return null;
        }
    }
     
    
    
    
    public void createDummyUser(){
        try {
           EUser selectedUser = new EUser();
            selectedUser.setUsername("peter");
            selectedUser.setFirstname("C");
            selectedUser.setLastname("P");
            selectedUser.setPassword("peter");
            utx.begin();
            em.persist(selectedUser);
            utx.commit();
        } catch (RollbackException ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     
     
     
     
     
     
}
