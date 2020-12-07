package services;

import dataaccess.UsersDB;
import models.Users;
import java.util.List;

public class AccountService {
    
    private UsersDB userDB;
    
     public AccountService() {
        userDB = new UsersDB();
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
        
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setActive(true);
        user.setIsAdmin(false);
        
        return userDB.insert(user);
    }

    public boolean update(String username, String password, String email, String firstname, String lastname, boolean active, boolean admin) throws Exception {
        Users user = new Users(username);
        
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setActive(active);
        user.setIsAdmin(admin);
        
        return userDB.update(user);       
    }
    
    public boolean delete(String username) throws Exception {
        Users deletedUser = userDB.getUser(username);
        
        if (deletedUser.getUsername().contains("admin")) {
            return false;
        }
        return userDB.delete(deletedUser);
    }
}
