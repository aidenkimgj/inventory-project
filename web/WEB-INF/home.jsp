<%-- 
    Document   : home
    Created on : Nov 28, 2020, 8:24:33 PM
    Author     : 837033
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <li><a href="login">Login</a></li>
                </ul>

                <ul class="navbar_icons">
                    <li><i class="fab fa-instagram"></i></li>
                    <li><i class="fab fa-twitter"></i></li>
                    <li><i class="fab fa-facebook-square"></i></li>
                </ul>
                <a href="#" class="navbar_toogleBtn">
                    <i class="fas fa-bars"></i>
                </a>
            </nav>
        </header>
        <div class="container">
            <h1>Would you want to create inventory?</h1>
            <a href="login">Go to Inventory</a>
        </div>
        <footer>
            <h3>Copyright © Aiden's Inventory Service</h3>
        </footer>
       
    </body>
</html>
