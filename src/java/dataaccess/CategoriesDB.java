package dataaccess;

import util.DBUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Categories;

public class CategoriesDB {
    
    private EntityManager em;
    private EntityTransaction trans;
    
    public List<Categories> getAllCategories() throws Exception {
         em = DBUtil.getEmFactory().createEntityManager();
         try {
         List<Categories> list = em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
            
            return list;
            
        } finally {
            em.close();
        }
    }

    public Categories get(int categoryID) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        try {
            Categories category = em.find(Categories.class, categoryID);
            return category;
        } finally {
            em.close();
        }
    }

    public boolean insert(Categories category) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(category);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error inserting item");
        } finally {
            em.close();
        }
    }

    public boolean update(Categories category) throws Exception {
            em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(category);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error updating category");
        } finally {
            em.close();
        }
    }

    public boolean delete(Categories category) throws Exception{
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();

        try {
           trans.begin();
           em.remove(em.merge(category));
           trans.commit();
           return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error deleting Category");
        } finally {
            em.close();
            
        }
    
    }
}
