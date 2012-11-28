/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author peter
 */
@Entity
@Table(name = "presitem")
public class PresentationItem extends AbstractEntity implements Serializable {
    
    private int position;
    
    @ManyToOne
    @JoinColumn(name = "doc_id")
    private Document document;
    
    @ManyToOne
    @JoinColumn(name = "pres_id")
    private Presentation presentation;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
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
        return "entities.PresentationItem[ id=" + getId() + " ]";
    }
}
