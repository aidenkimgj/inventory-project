package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

public class CreateAccountServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        
        if (uuid != null) {
            request.setAttribute("uuid", uuid);
            getServletContext().getRequestDispatcher("/WEB-INF/activation.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/create_account.jsp").forward(request, response);
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
        String url = request.getRequestURL().toString();
        String path = getServletContext().getRealPath("/WEB-INF");
        
        boolean active = false;
        AccountService as = new AccountService();
        
        try {
            if (action.equals("user_add")) {
                
               if (as.insert(username, password, email, firstname, lastname, active)) {
                    as.activationEmail(email, path, url);
                    request.setAttribute("activeEmail", "Adding the user has been complete!");
                } else {
                    request.setAttribute("unaddUser", "Please fill in the form!");
                    getServletContext().getRequestDispatcher("/WEB-INF/create_account.jsp").forward(request, response);
                }  
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Password must be at least 8 characters long.");
            getServletContext().getRequestDispatcher("/WEB-INF/create_account.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
