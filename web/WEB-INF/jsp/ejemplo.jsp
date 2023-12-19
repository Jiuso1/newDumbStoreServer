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
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2>Los datos del formulario son los siguientes:</h2>
        <p>
            <%
                String email = request.getParameter("correo");
                String contrasenha = request.getParameter("contrasenha");
                out.println(email);
                out.println(contrasenha);
            %>  
        </p>
        <h2>Los datos de la base de datos son los siguientes:</h2>
        <%
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\jesus\\Documents\\desarrolloWeb\\daw\\newDumbStoreServer\\database\\dumbDB.db");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from ejemplo;");
            while (rs.next()) {
                out.println("<p>");
                out.println("nombre = " + rs.getString("nombre"));
                out.println("descripcion = " + rs.getString("descripcion"));
                out.println("</p>");
            }
            rs.close();
            stat.close();
            conn.close();
        %>

    </body>
</html>
