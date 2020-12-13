<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
 
            <form action="reset" method="post">
                <div class="login_intro">
                    <i class="fas fa-laptop-house"></i>
                    <h2>Enter a new Password</h2>
                </div>
                <div class="reset_mention">
                    <p>Please enter new password which must be at least 8 characters long.</p>
                    <p>Do not enter previous password!!</p>
                </div>
                <div class="login_input_div">
                    <p> New Password </p>
                    <input type="password" name="newpassword" placeholder="Please enter new password"/>
                </div>
                <input type="hidden" name="uuid" value="${uuid}">
                <div class="submit_div">
                    <input type="submit" value="Submit">
                </div>
             </form>
        </div>
                    <c:if test="${message != null}">
                <script>
                    alert('Password condition not matched!');
                    document.location.href="reset";
                </script>
            </c:if>
                       
        <footer class="login_footer">
            <h3>Copyright © Aiden's Inventory Service</h3>
        </footer>
    </body>
</html>
