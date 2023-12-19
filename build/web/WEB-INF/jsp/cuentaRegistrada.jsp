<%-- 
    Document   : resultado
    Created on : 10 dic 2023, 17:41:27
    Author     : jesus
--%>

<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="..\css\estilo.css"/>
        <link rel="icon" href="..\media\photo\favicon.png">
    </head>
    <body>
        <div class="imagenLogin">
            <div class="imagenCentrada">
                <img src="..\media\photo\favicon.png">
            </div>
        </div>
        <div class="formulario">
            <form method="post" action="..\html\index.html">
                <h1>Su cuenta ha sido registrada</h1>
                <input type="submit" value="ok" id="botonIniciarSesion">
            </form>
        </div>
    </body>
</html>
