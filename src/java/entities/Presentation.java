/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author peter
 */
@Entity
@Table(name = "presentation")
public class Presentation extends AbstractEntity implements Serializable {

    private String name;
    
    private boolean activeOne;
    
    @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL)
    private List<PresentationItem> presItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActiveOne() {
        return activeOne;
    }

    public void setActiveOne(boolean activeOne) {
        this.activeOne = activeOne;
    }

    
    public List<PresentationItem> getPresItems() {
        return presItems;
    }

    public void setPresItems(List<PresentationItem> presItems) {
        this.presItems = presItems;
    }

 
    
    
    
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EUser)) {
            return false;
        }
        EUser other = (EUser) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Presentation[ id=" + getId() + " ]";
    }
    
}
