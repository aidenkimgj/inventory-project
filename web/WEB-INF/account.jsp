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
                    <c:if test="${username != null}">
                        <li><a href="login?logout">Logout</a></li>
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
        <div class="admin_container">
            <c:if test="${update != null}">
                <script>
                     alert('Updating the user has been complete!');
                     document.location.href="account";
                </script>
            </c:if>
            <c:if test="${check != null}">
                <script>
                     alert('Password is too short. Must be at least 8 characters long!');
                     document.location.href="account";
                </script>
            </c:if>
            <c:if test="${message != null}">
                <script>
                     alert('Whoops.  Could not perform that action.');
                     document.location.href="account";
                </script>
            </c:if>                
            <div class="admin_title">
                <h2>Account for <strong>${userAccount.firstName} ${userAccount.lastName}</strong></h2>
            </div>
            
            <div class="account_table">
                <h2>Manage Account</h2>
                
                <form action="account" method="POST">

                        <div class="admin_input_div">
                            <p> Username </p>
                            <input type="text" name="username" value="${userAccount.username}" readonly>
                        </div>
                        
                        <div class="admin_input_div">
                            <p> Password </p>
                            <input type="password" name="password" value="">
                        </div>
                                                
                        <div class="admin_input_div">
                            <p> Email </p>
                            <input type="email" name="email" value="${userAccount.email}">
                        </div>
                        
                        <div class="admin_input_div">
                            <p> First name </p>
                            <input type="text" name="firstname" value="${userAccount.firstName}">
                        </div>
                        
                        <div class="admin_input_div">
                            <p> Last name </p>
                            <input type="text" name="lastname" value="${userAccount.lastName}">
                        </div>
                        
                        <c:choose>
                            <c:when test="${userAccount.active == true}">
                                <div class="admin_input_div">
                                    <p> Active </p>
                                    <input type="checkbox" name="active" checked>
                                </div>
                             </c:when>
                            <c:otherwise>
                                <div class="admin_input_div">
                                    <p> Active </p>
                                    <input type="checkbox" name="active"> 
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <input type="hidden" name="action" value="edit">
                        <div class="submit_div">
                             <input type="submit" value="Save">
                        </div>
                       
                    </form>
            </div>
            <footer>
                <h3>Copyright © Aiden's Inventory Service</h3>
            </footer>
        </div>
 
    </body>
    
</html>
