package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Users;
import services.AccountService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            String selectedUsername = request.getParameter("selectedUsername");
            try {
                Users user = as.get(selectedUsername);
                request.setAttribute("selectedUser", user);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        List<Users> users = null;        
        try {
            users = as.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
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
        boolean active = false;
        boolean admin = false;
        
       
        AccountService as = new AccountService();

        try {
            if (action.equals("delete")) {
                String selectedUsername = request.getParameter("selectedUsername");
                if(as.delete(selectedUsername)) {
                    request.setAttribute("message", "Deleting the user has been complete!");
                } else {
                    request.setAttribute("message", "The administrator cannot be removed!");
                }
            } else if (action.equals("edit")) {
                if(activeCheck == null) {
                    active = false;
                } else if(activeCheck.equals("on")) {
                    active = true;
                }
         
                if(adminCheck == null) {
                    admin = false;
                } else if(adminCheck.equals("on")) {
                    admin = true;
                }
                     
                if(as.update(username, password, email, firstname, lastname, active, admin)) {
                    request.setAttribute("message", "Updating the user has been complete!");
                }
            } else if (action.equals("add")) {
                
                if(as.insert(username, password, email, firstname, lastname)){
                    request.setAttribute("message", "Adding the user has been complete!");
                } else {
                    request.setAttribute("message", "Please fill in the form!");
                }  
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }
        
        List<Users> users = null;
        try {
            users =  as.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
