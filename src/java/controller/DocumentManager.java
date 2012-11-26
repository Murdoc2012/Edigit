/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Document;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author peter
 */
@ManagedBean(name = "documentManager")
@SessionScoped
public class DocumentManager extends AbstractManager implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Document currentDocument;
    
    private DataModel<Document> documents;
    
    private List<Document> documentList;
    
    
    @PostConstruct
    public void construct() {
        currentDocument = new Document();
        init();
    }
    
    @PreDestroy
    public void destroy() {
        
        documents = null;
        if (documentList != null){ 
            documentList.clear();
            documentList = null;
        }
        
        currentDocument = null;
    }

    private void init() {
        try {
            setDocumentList(doInTransaction(new PersistenceAction<List<Document>>(){

                @Override
                public List<Document> execute(EntityManager em) {
                    Query query = em.createQuery("SELECT d FROM Document d");
                    return query.getResultList();
                }
            }));
        } catch (ManagerException ex) {
            Logger.getLogger(DocumentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (getDocumentList() != null){
            documents = new ListDataModel<Document>(getDocumentList());
        }
        
    }
    
    public String create(){
        setCurrentDocument(new Document());
        return "create";
    }
    
    public String edit(){
        setCurrentDocument(documents.getRowData());
        return "edit";
    }
    
    public String cancelEdit(){
        return "show";
    }
    
    public String save(){
        if (getCurrentDocument() != null){
            try {
                Document merged =  doInTransaction(new PersistenceAction<Document>(){

                    @Override
                    public Document execute(EntityManager em) {
                        if (getCurrentDocument().isNew()){
                            em.persist(getCurrentDocument());
                        }
                        else if (!em.contains(currentDocument)){
                            return em.merge(getCurrentDocument());
                        }
                        return getCurrentDocument();
                                
                    }
                });
                
//                if (!getCurrentDocument().equals(merged)){
//                    setCurrentDocument(merged);
//                }
            } catch (ManagerException ex) {
                Logger.getLogger(DocumentManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        init();
        return "show";
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document getCurrentDocument() {
        return currentDocument;
    }

    public void setCurrentDocument(Document currentDocument) {
        this.currentDocument = currentDocument;
    }

    public DataModel<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(DataModel<Document> documents) {
        this.documents = documents;
    }
    
    
    
    
    
}
