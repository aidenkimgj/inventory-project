package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Categories;
import models.Users;
import services.AccountService;
import services.InventoryService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InventoryService is = new InventoryService();
        AccountService as = new AccountService();
        String action = request.getParameter("action");
        
        if (action != null && action.equals("u_view")) {
            String selectedUsername = request.getParameter("selectedUsername");
            try {
                Users user = as.get(selectedUsername);
                request.setAttribute("selectedUser", user);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action != null && action.equals("c_view")) {
            int selectedCategory = Integer.parseInt(request.getParameter("selectedCategory"));
            
            System.out.println(selectedCategory);
            try {
                Categories category = is.get(selectedCategory);
                request.setAttribute("selectedCategory", category);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        List<Users> users = null;
        List<Categories> categories = null;        
        try {
            users = as.getAll();
            categories = is.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        request.setAttribute("categories", categories);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String activeCheck = request.getParameter("active");
        String adminCheck = request.getParameter("isadmin");
        String categoryName = request.getParameter("categoryname");
        String categoryId = request.getParameter("categoryid");
        
        boolean active = false;
        int admin = 0;
               
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();
        
        try {
            if (action.equals("u_delete")) {
                String selectedUsername = request.getParameter("selectedUsername");
                if(as.delete(selectedUsername)) {
                    request.setAttribute("message", "Deleting the user has been complete!");
                } else {
                    request.setAttribute("message", "The administrator cannot be removed!");
                }
            } else if (action.equals("c_delete")) {
                String selectedCategory = request.getParameter("selectedCategory");
                if(is.delete(selectedCategory)) {
                    request.setAttribute("message", "Deleting the category has been complete!");
                } else {
                    request.setAttribute("message", "The administrator cannot be removed!");
                } 
            }else if (action.equals("user_edit")) {
                if(activeCheck == null) {
                    active = false;
                } else if(activeCheck.equals("on")) {
                    active = true;
                }
         
                if(adminCheck == null) {
                    admin = 2;
                } else if(adminCheck.equals("on")) {
                    admin = 1;
                }
                     
                if(as.update(username, password, email, firstname, lastname, active, admin)) {
                    request.setAttribute("message", "Updating the user has been complete!");
                }
            } else if (action.equals("category_edit")){
                
                if(is.update(categoryId,categoryName)) {
                    request.setAttribute("message", "Updating the user has been complete!");
                }
            
            } else if (action.equals("category_add")) {
                
               if(is.insert(categoryName)) {
                    request.setAttribute("message", "Updating the user has been complete!");
                } else {
                    request.setAttribute("message", "Please fill in the form!");
                }  
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }
        
        List<Users> users = null;
        List<Categories> categories = null;        
        try {
            users = as.getAll();
            categories = is.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        request.setAttribute("categories", categories);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
