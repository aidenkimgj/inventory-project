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
           
            <div class="admin_header">
                <div class="admin_title">
                     
                    <h2>Admin menu for ${username}</h2>
                </div>
                <a name="ManageUsers"></a>
                <ul class="admin_menu">
                    <li><a href="#ManageUsers">Manage Users</a></li>
                    <li><a href="#ManageCategories">Manage Categories</a></li>
                </ul>
                
            </div>
            
                
            <div class="user_table">                
                <h2>Manage Users</h2>
                <table>                    
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Active</th>
                        <th>Role</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.username}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.email}</td>
                            <c:choose>
                                <c:when test="${user.active == true}">
                                    <td>on</td>
                                </c:when>
                                <c:otherwise>
                                    <td>off</td>
                                </c:otherwise>    
                            </c:choose>
                            
                            
                            <td>${user.role.getRolename()}</td>
                            <td>
                                <form class="in_form" action="admin" method="post" >
                                    <div class="btn_style">
                                         <input type="submit" value="Delete">
                                    </div>
                                    <input type="hidden" name="action" value="u_delete">
                                    <input type="hidden" name="selectedUsername" value="${user.username}">
                                </form>
                            </td>
                            <td>
                                <form class="in_form" action="admin" method="get">
                                    <div class="btn_style">
                                        <input type="submit" value="Edit">
                                    </div>
                                    <input type="hidden" name="action" value="u_view">
                                    <input type="hidden" name="selectedUsername" value="${user.username}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
                        
            <div class="user_form">
                <c:if test="${selectedUser == null}">
                    <form action="admin" method="POST">
                        <h3>Add User</h3>
                        <div class="admin_input_div">
                            <p> Username </p>
                            <input type="text" name="username">
                        </div>
                        <div class="admin_input_div">
                            <p> Password </p>
                            <input type="password" name="password">
                        </div>
                        <div class="admin_input_div">
                            <p> Email </p>
                            <input type="email" name="email">
                        </div>
                        <div class="admin_input_div">
                            <p> First name </p>
                            <input type="text" name="firstname">
                        </div>
                        
                        <div class="admin_input_div">
                            <p> Last name </p>
                            <input type="text" name="lastname">
                        </div>
                        <input type="hidden" name="action" value="user_add">
                        <div class="submit_div">
                             <input type="submit" value="Submit">
                        </div>
                    </form>
                </c:if>
                
                <c:if test="${selectedUser != null}">
                    <form action="admin" method="POST">
                        <h3>Edit User</h3>
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
                             </c:when>
                            <c:otherwise>
                                <div class="admin_input_div">
                                    <p> Active </p>
                                    <input type="checkbox" name="active"> 
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${selectedUser.role.getRoleid() == 1}">
                                <div class="admin_input_div">
                                    <p> Permission </p>
                                    <input type="checkbox" name="isadmin" checked> 
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="admin_input_div">
                                    <p> Permission </p>
                                    <input type="checkbox" name="isadmin"> 
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <input type="hidden" name="action" value="user_edit">
                        <div class="submit_div">
                             <input type="submit" value="Save">
                        </div>
                       
                    </form>
                </c:if>
            </div>
             
            <div class="category_table">
                <a name="ManageCategories"></a>
                <h2>Manage Categories</h2>
                <table>
                    <tr>
                        <th>Category Name</th>
                        <th>Category ID</th>
                        <th>Item Number</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.categoryName}</td>
                            <td>${category.categoryID}</td>
                            <td>${category.itemsList.size()}</td>
                            <td>
                                <form class="in_form" action="admin" method="post" >
                                    <div class="btn_style">
                                        <input type="submit" value="Delete">
                                    </div>
                                    
                                    <input type="hidden" name="action" value="c_delete">
                                    <input type="hidden" name="selectedCategory" value="${category.categoryID}">
                                </form>
                            </td>
                            <td>
                                <form class="in_form" action="admin" method="get">
                                    <div class="btn_style">
                                        <input type="submit" value="Edit">
                                    </div>
                                    
                                    <input type="hidden" name="action" value="c_view">
                                    <input type="hidden" name="selectedCategory" value="${category.categoryID}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            
            <div class="category_form">
                <c:if test="${selectedCategory == null}">
                    
                    <form action="admin" method="POST">
                        <h3>Add Category</h3>
                                             
                        <div class="admin_input_div">
                            <p> Category Name </p>
                            <input type="text" name="categoryname">
                        </div>

                        <input type="hidden" name="action" value="category_add">
                        <div class="submit_div">
                             <input type="submit" value="Save">
                        </div>
                       
                    </form>
                </c:if>
                
                <c:if test="${selectedCategory != null}">
                    
                    <form action="admin" method="POST">
                        <h3>Edit Category</h3>
                        <div class="admin_input_div">
                            <p> Category ID </p>
                            <input type="text" name="categoryid" value="${selectedCategory.categoryID}" readonly>
                        </div>
                        
                        <div class="admin_input_div">
                            <p> Category Name </p>
                            <input type="text" name="categoryname" value="${selectedCategory.categoryName}">
                        </div>

                        <input type="hidden" name="action" value="category_edit">
                        <div class="submit_div">
                             <input type="submit" value="Save">
                        </div>
                       
                    </form>
                </c:if>
            </div>
            
            <footer class="admin_footer">
                <h3>Copyright © Aiden's Inventory Service</h3>
            </footer> 
            
        </div>
               
    </body>
</html>
