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
import services.CategoryService;
import services.InventoryService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryService cs = new CategoryService();
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
                Categories category = cs.get(selectedCategory);
                request.setAttribute("selectedCategory", category);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        List<Users> users = null;
        List<Categories> categories = null;        
        try {
            users = as.getAll();
            categories = cs.getAllCategories();
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
        CategoryService cs = new CategoryService();
        
        try {
            switch (action) {
                case "u_delete":
                    String selectedUsername = request.getParameter("selectedUsername");
                    if (as.delete(selectedUsername)) {
                        request.setAttribute("deleteUser", "Deleting the user has been complete!");
                    } else {
                        request.setAttribute("undeleteUser", "The administrator cannot be removed!");
                    }   break;
                case "c_delete":
                    String selectedCategory = request.getParameter("selectedCategory");
                    if (cs.delete(selectedCategory)) {
                        request.setAttribute("deleteCategory", "Deleting the category has been complete!");
                    } else {
                        request.setAttribute("undeleteCategory", "Failed to delete category.");
                    }   break;
                case "user_edit":
                    if (activeCheck == null) {
                        active = false;
                    } else if (activeCheck.equals("on")) {
                        active = true;
                    } if (adminCheck == null) {
                        admin = 2;
                    } else if (adminCheck.equals("on")) {
                        admin = 1;
                    } if (as.update(username, password, email, firstname, lastname, active, admin)) {
                        request.setAttribute("updateUser", "Updating the user has been complete!");
                    } break;
                case "category_edit":
                    if (cs.update(categoryId,categoryName)) {
                        request.setAttribute("updateCategory", "Updating the category has been complete!");
                    } break;
                case "user_add":
                    active = true;
                    if (as.insert(username, password, email, firstname, lastname, active) == 3) {
                        request.setAttribute("addUser", "Adding the user has been complete!");
                    } else if(as.insert(username, password, email, firstname, lastname, active) == 1) {
                        request.setAttribute("unaddUser1", "Please fill in the form!");
                    } else {
                        request.setAttribute("unaddUser2", "UserName already exsit!");
                    } 
                    break;
                case "category_add":
                    if (cs.insert(categoryName)) {
                        request.setAttribute("addCategory", "Adding the Category has been complete!");
                    } else {
                        request.setAttribute("unaddCategory", "Please fill in the form!");
                    } break;
                default:
                    break;  
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }
        
        List<Users> users = null;
        List<Categories> categories = null;        
        try {
            users = as.getAll();
            categories = cs.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        request.setAttribute("categories", categories);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
