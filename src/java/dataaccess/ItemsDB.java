package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
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
            trans.begin();
            em.persist(item);
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
            user.getItemsList().remove(item);
            trans.begin();
            em.remove(em.merge(item));
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
}
