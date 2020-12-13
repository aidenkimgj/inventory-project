package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.AccountService;

public class AccountServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        AccountService as = new AccountService();
        try {
            Users user = as.get(username);
            session.setAttribute("userAccount", user);
                               
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String activeCheck = request.getParameter("active");
        boolean active = false;
        int admin = 2;
        AccountService as = new AccountService();
        
        try {
            if (action.equals("edit")) {
                if (activeCheck == null) {
                    active = false;
                } else if (activeCheck.equals("on")) {
                    active = true;
                }
                
                if (as.update(username, password, email, firstname, lastname, active, admin)) {
                    request.setAttribute("update", "Updating the user has been complete!");
                } else {
                    request.setAttribute("check", "Password is too short. Must be at least 8 characters long.");
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        } 
                
        try {
            Users user = as.get(username);
            session.setAttribute("userAccount", user);
                               
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }
}
