package services;

import dataaccess.RoleDB;
import dataaccess.UsersDB;
import models.Users;
import java.util.List;
import models.Role;

public class AccountService {
    
    private UsersDB userDB;
    private RoleDB roleDB;
    
     public AccountService() {
        userDB = new UsersDB();
        roleDB = new RoleDB();
    }
    public Users login(String username, String password) {
               
        try {
            Users user = userDB.getUser(username);
            if(password.equals(user.getPassword())) {
                return user;
            }    
        } catch(Exception e) {
              
        }
            return null;
    }
    public Users get(String username) throws Exception {
        return userDB.getUser(username);
    }
     
    public List<Users> getAll() throws Exception {
        return userDB.getAll();
    }

    public boolean insert(String username, String password, String email, String firstname, String lastname) throws Exception {
        if(username.equals("") || password.equals("") || email.equals("") || firstname.equals("") || lastname.equals("")) {
            return false;
        }
        
        Users user = new Users(username);
        Role role = roleDB.get(2);
        
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setActive(true);
        user.setRole(role);
        
        return userDB.insert(user);
    }

    public boolean update(String username, String password, String email, String firstname, String lastname, boolean active, int admin) throws Exception {
        Users user = new Users(username);
        Role role = roleDB.get(admin);
        
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setActive(active);
        user.setRole(role);
        
        return userDB.update(user);       
    }
    
    public boolean delete(String username) throws Exception {
        Users deletedUser = userDB.getUser(username);
        
        if (deletedUser.getRole().getRoleid() == 1 && deletedUser.getRole().getRolename().equals("system admin")) {
            return false;
        }
        return userDB.delete(deletedUser);
    }
}
