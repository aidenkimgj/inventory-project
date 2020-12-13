package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

/**
 *
 * @author 837033
 */
public class ResetPasswordServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        
        if (uuid != null) {
            request.setAttribute("uuid", uuid);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String url = request.getRequestURL().toString();
        String email = request.getParameter("email");
        String path = getServletContext().getRealPath("/WEB-INF");
        String newPassword = request.getParameter("newpassword");
        AccountService as = new AccountService();
        
        System.out.println("url: " +url);
        System.out.println("path: " + path);
;
        if (uuid != null) {
            if (as.changePassword(uuid, newPassword)) {
                request.setAttribute("message", "Password has been changed!");
                
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Password condition not matched!");
                request.setAttribute("uuid", uuid);
                getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            }
        } else {
            as.resetPassword(email, path, url);
            request.setAttribute("message", "New password link has been sent to " + email + ".");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
