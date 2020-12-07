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
            <div class="admin_title">
                <h2>Admin window</h2>
            </div>
            
            <div class="user_table">
                <h2>Manage Users</h2>
                <table>
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.username}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>
                                <form action="admin" method="post" >
                                    <input type="submit" value="Delete">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="selectedUsername" value="${user.username}">
                                </form>
                            </td>
                            <td>
                                <form action="admin" method="get">
                                    <input type="submit" value="Edit">
                                    <input type="hidden" name="action" value="view">
                                    <input type="hidden" name="selectedUsername" value="${user.username}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="edit_form">
                <c:if test="${selectedUser != null}">
                    <h3>Edit User</h3>
                    <form action="admin" method="POST">
                        <div class="admin_input_div">
                            <p> Username </p>
                            <input type="text" name="username" value="${selectedUser.username}" readonly>
                        </div>
                        
                        <div class="admin_input_div">
                            <p> Password </p>
                            <input type="password" name="password" value="${selectedUser.password}">
                        </div>
                        
                        
                        <div class="admin_input_div">
                            <p> Email </p>
                            <input type="email" name="email" value="${selectedUser.email}">
                        </div>
                        
                        <div class="admin_input_div">
                            <p> First name </p>
                            <input type="text" name="firstname" value="${selectedUser.firstName}">
                        </div>
                        
                        <div class="admin_input_div">
                            <p> Last name </p>
                            <input type="text" name="lastname" value="${selectedUser.lastName}">
                        </div>
                        
                        <c:choose>
                            <c:when test="${selectedUser.active == true}">
                                <div class="admin_input_div">
                                    <p> Active </p>
                                    <input type="checkbox" name="active" checked>
                                </div>
                                 <br>
                            </c:when>
                            <c:otherwise>
                                Active: <input type="checkbox" name="active"><br>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${selectedUser.isAdmin == true}">
                                IsAdmin: <input type="checkbox" name="isadmin" checked><br>
                            </c:when>
                            <c:otherwise>
                                IsAdmin: <input type="checkbox" name="isadmin"><br>
                            </c:otherwise>
                        </c:choose>

                        <input type="hidden" name="action" value="edit">
                        <input type="submit" value="Save">
                    </form>
                </c:if>
            </div>
       
            <footer class="admin_footer">
                <h3>Copyright © Aiden's Inventory Service</h3>
            </footer>
        </div>
                   
    </body>
</html>
