package services;

import dataaccess.ItemsDB;
import dataaccess.RoleDB;
import dataaccess.UsersDB;
import java.security.NoSuchAlgorithmException;
import models.Users;
import java.util.List;
import models.Items;
import models.Role;
import util.PasswordUtil;

public class AccountService {
    
    private UsersDB userDB;
    private RoleDB roleDB;
    private ItemsDB itemDB;
    
     public AccountService() {
        userDB = new UsersDB();
        roleDB = new RoleDB();
        itemDB = new ItemsDB();
    }
    public Users login(String username, String password) throws Exception {
                
        try {
            String salt = userDB.getUser(username).getUserSalt();
            String pass = PasswordUtil.hashPassword(username + password + salt); 
            Users user = userDB.getUser(username);
                        System.out.println(pass);
            if(pass.equals(user.getPassword())) {
                return user;
            }    
        } catch(Exception ex) {
              System.out.println(ex.getMessage());
        }
        return null;
    }
    public Users get(String username) throws Exception {
        return userDB.getUser(username);
    }
     
    public List<Users> getAll() throws Exception {
        return userDB.getAll();
    }

    public boolean insert(String username, String password, String email, String firstname, String lastname, boolean activation) throws Exception {
        
        if(username.equals("") || password.equals("") || email.equals("") || firstname.equals("") || lastname.equals("")) {
            return false;
        }
        
        try {
            PasswordUtil.checkPasswordStrength(password);
            System.out.println("Password is valid.");
        } catch (Exception ex ) {
            System.out.println(ex.getMessage());
        }
        String salt = PasswordUtil.getSalt();
        String pass = PasswordUtil.hashPassword(username + password + salt);
        Users user = new Users(username);
        Role role = roleDB.get(2);
                    
        user.setPassword(pass);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setActive(activation);
        user.setRole(role);
        user.setUserSalt(salt);
        
        return userDB.insert(user);
    }

    public boolean update(String username, String password, String email, String firstname, String lastname, boolean active, int admin) throws Exception {
        
        try {
            PasswordUtil.checkPasswordStrength(password);
            System.out.println("Password is valid.");
        } catch (Exception ex ) {
            System.out.println(ex.getMessage());
        }
        
        String salt = userDB.getUser(username).getUserSalt();
        Users user = userDB.getUser(username);

        if(password.equals(user.getPassword())) {
            user.setPassword(password);
            user.setUserSalt(salt);
        } else {
            String newSalt = PasswordUtil.getSalt();
            String newPass = PasswordUtil.hashPassword(username + password + newSalt);
            user.setPassword(newPass);
            user.setUserSalt(newSalt);
        } 
               
        Role role = roleDB.get(admin);
                
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
        List<Items> userItem = itemDB.getAllItems(username);
        
        if (userItem.size() == 0) {
            return userDB.delete(deletedUser);
        } else { 
            if (itemDB.deleteAll(deletedUser) > 0) {
                return userDB.delete(deletedUser);
            }
        }
        return false;
    }
}
