package dataaccess;

import util.DBUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Categories;
import models.Items;
import models.Users;

public class ItemsDB {
       
    private EntityManager em;
    private EntityTransaction trans;
    
    public List<Items> getAllItems(String owner) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        Users user = em.find(Users.class, owner);
        String qString = "SELECT i FROM Items i WHERE i.owner = :owner";
        TypedQuery<Items> q = em.createQuery(qString, Items.class);
        q.setParameter("owner", user);
        try {
            List<Items> items = q.getResultList();
            return items;
        } finally {
            em.close();   
        }
    }

    public boolean insert(Items item) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        try {
            Categories category = item.getCategory();
            category.getItemsList().add(item);
            trans.begin();
            em.persist(item);
            em.merge(category);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error inserting item");
        } finally {
            em.close();
        }
    }

    public Items get(int itemID) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Items item = em.find(Items.class, itemID);
            
            return item;
        } finally { 
            em.close();
        }
    }

    public boolean delete(Items item) throws Exception{
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        
        try {
            Users user = item.getOwner();
            Categories category = item.getCategory();
            user.getItemsList().remove(item);
            category.getItemsList().remove(item);
            trans.begin();
            em.remove(em.merge(item));
            em.merge(category);
            em.merge(user);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error deleting Item");
        } finally {
            em.close();
        }
        
    }
    
     public int deleteAll(Users deletedUser) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        String qString = "DELETE FROM Items i WHERE i.owner = :owner"; 
        Query q = em.createQuery(qString).setParameter("owner", deletedUser);
        int count = 0;
        try {
            trans.begin();
            count = q.executeUpdate();
            trans.commit();
            return count;
        } catch (Exception ex) {
            trans.rollback();
            return count;
        } finally {
            em.close();
        }
    }

    public boolean update(Items item) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        Categories category = item.getCategory();
        category.getItemsList().add(item);
        try {
            trans.begin();
            em.merge(item);
            em.merge(category); 
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error updating item");
        } finally {
            em.close();
        }        
    }
}
