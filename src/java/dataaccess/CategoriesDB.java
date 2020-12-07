package dataaccess;

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
    
}
