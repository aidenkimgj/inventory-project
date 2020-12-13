<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME Inventory</title>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@600&display=swap" rel="stylesheet">
        <link href="assets/css/base.css" rel="stylesheet" type="text/css"/>
        <script src="https://kit.fontawesome.com/80ee7ec6c8.js" crossorigin="anonymous"></script>
        <script src="assets/scripts/header.js" defer></script>
        
    </head>
    <body>
         <header>
            <nav class="navbar">
                <div class="navbar_logo">
                    <i class="fas fa-laptop-house"></i>
                    <a href="home">Home Inventory</a>
                </div>

                <ul class="navbar_menu">
                    <li><a href="account">Account</a></li>
                    <li><a href="admin">Admin</a></li>
                    <li><a href="inventory">Inventory</a></li>
                    <c:if test="${username == null}">
                        <li><a href="login">Login</a></li>
                    </c:if>
                    
                </ul>

                <ul class="navbar_icons">
                    <li><a href="https://www.instagram.com/"><i class="fab fa-instagram"></i></a></li>
                    <li><a class="twitter" href="https://twitter.com/?lang=en"><i class="fab fa-twitter"></i></a></li>
                    <li><a class="facebook" href="https://www.facebook.com/"><i class="fab fa-facebook-square"></i></a></li>
                </ul>
                <a href="#" class="navbar_toogleBtn">
                    <i class="fas fa-bars"></i>
                </a>
            </nav>
        </header>
        
        <div class="login_container">

           <c:if test="${logout != null}">
                <script>
                    alert('You have successully logged out.');
                    document.location.href="login";
                </script>
            </c:if>
            <c:if test="${login != null}">
                <script>
                    alert('Invalid login!');
                    document.location.href="login";
                </script>
            </c:if>
            <c:if test="${active != null}">
                <script>
                    alert('Please activate your account!');
                    document.location.href="login";
                </script>
            </c:if>
            <c:if test="${actived != null}">
                <script>
                    alert('Your account has been activated!');
                    document.location.href="login";
                </script>
            </c:if>                 
            <c:if test="${email != null}">
                <script>
                    alert('Account activation email has been sent to your email which has been registered our service.');
                    document.location.href="login";
                </script>
            </c:if>  
            <c:if test="${account != null}">
                <script>
                    alert('Your account already activated.');
                    document.location.href="login";
                </script>
            </c:if>  
            <c:if test="${activeEmail != null}">
                <script>
                    alert('Your account has been successfully created.');
                    alert('An account activation email has been sent to the email registered with our service.!');
                    document.location.href="login";
                </script>
            </c:if> 
            <c:if test="${resetEmail != null}">
                <script>
                    alert('New password link has been sent to the email registered with our service!');
                    document.location.href="login";
                </script>
            </c:if>
            <c:if test="${passChange != null}">
                <script>
                    alert('Password has been changed!');
                    document.location.href="login";
                </script>
            </c:if>
            <form action="login" method="post">
                <div class="login_intro">
                    <i class="fas fa-laptop-house"></i>
                    <h2>Log in to HOME Inventory</h2>
                </div>
                
                <div class="login_div">
                    <div class="login_input_div">
                        <p> Username <a id="active_account" href="active">Non-active account?</a></p>
                        <input type="text" name="username" placeholder="Enter Username"/>
                    </div>
                    
                    <div class="login_input_div" id="password">
                        <p> Password <a class="forgot_password" href="reset">Forgot password?</a></p>
                        <input type="password" name="password" placeholder="Enter Password"/>    
                    </div>
                   
                    <div class="submit_div">
                        <input type="submit" value="Login">
                    </div>
                </div>
                <div class="create_account">
                <p>New to HOME Inventory? <a href="create">Create an account.</a> </p>
            </div>
            </form>
                  
        </div>
        
        <footer class="login_footer">
            <h3>Copyright © Aiden's Inventory Service</h3>
        </footer>
    </body>
</html>
