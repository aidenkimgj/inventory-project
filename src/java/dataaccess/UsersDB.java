package dataaccess;

import util.DBUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Users;

public class UsersDB {
    
    private EntityManager em;
    private EntityTransaction trans;
    
    public Users getUser(String username) throws Exception {
         em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Users user = em.find(Users.class, username);
            return user;
        } catch (Exception ex) {
            throw new Exception("Error getting Users");
        } finally {
            em.close();
        }
    }

    public List<Users> getAll() throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        
        
        try {
            List<Users> list = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            return list;
            
        } finally {
            em.close();
        }
    }

    public boolean insert(Users user) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error inserting user");
        } finally {
            em.close();
        }
    }

    public boolean update(Users user) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error updating user");
        } finally {
            em.close();
        }
    }

    public boolean delete(Users user) throws Exception {
        em = DBUtil.getEmFactory().createEntityManager();
        trans = em.getTransaction();

        try {
           
           trans.begin();

           em.remove(em.merge(user));
           trans.commit();
           return true;
        } catch (Exception ex) {
            trans.rollback();
            throw new Exception("Error deleting User");
        } finally {
            em.close();
            
        }
    }

    public Users getByEmail(String email) {
        em = DBUtil.getEmFactory().createEntityManager();
        try {
            Users user = em.createNamedQuery("Users.findByEmail", Users.class).setParameter("email", email).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
    
    public Users getByUUID(String uuid) {
        em = DBUtil.getEmFactory().createEntityManager();
   
        try {
            Users user = em.createNamedQuery("Users.findByUserUUID", Users.class).setParameter("userUUID", uuid).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
}

