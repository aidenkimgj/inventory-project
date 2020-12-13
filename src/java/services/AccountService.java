package services;

import dataaccess.ItemsDB;
import dataaccess.RoleDB;
import dataaccess.UsersDB;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import models.Users;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Users getUserByUUID(String uuid) throws Exception {
        Users user = userDB.getByUUID(uuid);
        
        return user;
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
        

        System.out.println(password);
        Users user = userDB.getUser(username);
        String salt = user.getUserSalt();
        String hashedPass = PasswordUtil.hashPassword(username + password + salt);
        
        if (!(password.trim().isEmpty())&&(!hashedPass.equals(user.getPassword()))){
            
            try {
                PasswordUtil.checkPasswordStrength(password);
                System.out.println("Password is valid.");
            } catch (Exception ex ) {
                System.out.println(ex.getMessage());
                return false;
            }
            
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

    public boolean changePassword(String uuid, String newPassword)  {
        try {
            PasswordUtil.checkPasswordStrength(newPassword);
            System.out.println("Password is valid.");
        } catch (Exception ex) {
            return false;
        }
        
        try {
            Users user = userDB.getByUUID(uuid);
            System.out.println(user.getFirstName());
            String salt = user.getUserSalt();
            String passCheck = PasswordUtil.hashPassword(user.getUsername() + newPassword + salt);
            System.out.println(passCheck);
            if (passCheck.equals(user.getPassword())) {
                System.out.println("Do not enter previous password!");
                return false;
            }
            
            String newSalt = PasswordUtil.getSalt();
            String newPass = PasswordUtil.hashPassword(user.getUsername() + newPassword + newSalt);
            
            user.setPassword(newPass);
            user.setUserSalt(newSalt);
            user.setUserUUID(null);
            
            return userDB.update(user);
        } catch (Exception ex) {
            return false;
        }
    }

    public void resetPassword(String email, String path, String url) {
        
        try {
            String uuid = UUID.randomUUID().toString();
            Users user = userDB.getByEmail(email);
            
            user.setUserUUID(uuid);
            userDB.update(user);

            String to = user.getEmail();
            String subject = "Iventory App Password Reset";
            String template = path + "/emailtemplates/resetpassword.html";
            String link = url + "?uuid=" + uuid;

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("username", user.getUsername());
            tags.put("link", link);
                
        
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean activateAccount(String uuid) {
        try {
            Users user = userDB.getByUUID(uuid);
            user.setActive(true);
            return userDB.update(user);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean activationEmail(String email, String path, String url) {
       try {
           
            String uuid = UUID.randomUUID().toString();
            Users user = userDB.getByEmail(email);
            if (user.getActive()) {
                return false;
            }
            user.setUserUUID(uuid);
            userDB.update(user);

            String to = user.getEmail();
            String subject = "Iventory App Account Activate";
            String template = path + "/emailtemplates/activation.html";
            String link = url + "?uuid=" + uuid;

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);
                
        
            GmailService.sendMail(to, subject, template, tags);
            return true;
        } catch (Exception ex) {
            
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
       
    }

    public void welcomeEmail(String email, String path, String url) throws Exception {
        Users user = userDB.getByEmail(email);
        user.setUserUUID(null);
        userDB.update(user);
        String to = user.getEmail();
        String subject = "Welcome to Aiden's Iventory Service";
        String template = path + "/emailtemplates/welcome.html";
        String link = url + "?welcome=welcome";

        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstname", user.getFirstName());
        tags.put("lastname", user.getLastName());
        tags.put("link", link);
               
        GmailService.sendMail(to, subject, template, tags);
    }


}
