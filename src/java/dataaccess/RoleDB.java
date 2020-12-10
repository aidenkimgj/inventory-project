package dataaccess;

import util.DBUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Role;

public class RoleDB {
    private EntityManager em;
    private EntityTransaction trans;
    
    public Role get(int admin) throws Exception{
        em = DBUtil.getEmFactory().createEntityManager();
        try {
            Role role = em.find(Role.class, admin);
            return role;
        } finally {
            em.close();
        }
    }
    
}
