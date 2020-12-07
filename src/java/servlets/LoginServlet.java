package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.AccountService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("logout");
        
        if(action != null) {
            session.invalidate();
            session = request.getSession();
            request.setAttribute("message", "You have successully logged out.");
        }
        
        if(session.getAttribute("username") == null ) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else if(session.getAttribute("username").equals("admin")){
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("inventory");
        } 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        AccountService as = new AccountService();
        Users user = as.login(username, password);
        System.out.println(user);
        if (user == null) {
            request.setAttribute("message", "Invalid login");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            
        }
                
        if (!user.getActive()) {
            request.setAttribute("message", "You are non-active user!");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("IsAdmin", user.getIsAdmin());
            if (user.getIsAdmin()) {
                response.sendRedirect("admin");
            } else {
                response.sendRedirect("inventory");
            }
        }
        
    }
}
