/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newDumbStoreServer.Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jesus
 */
@WebServlet(name = "Controlador", urlPatterns = {"/do/*"})
public class Controlador extends HttpServlet {

    private Connection conn;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public void init() throws ServletException{
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\jesus\\Documents\\desarrolloWeb\\daw\\newDumbStoreServer\\database\\dumbDB.db");
        } catch (ClassNotFoundException | SQLException ex) {
            
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PreparedStatement ps = null;
        Statement statement = null;
        ResultSet rs = null;

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String accion = request.getPathInfo();
            String vista = null;

            switch (accion) {
                case "/registrarCuentaBD": {
                    try {
                        int id = -1;
                        String nombre = request.getParameter("nombre");
                        String apellidos = request.getParameter("apellidos");
                        ps = conn.prepareStatement("INSERT INTO PERSONA VALUES(NULL,?,?)");
                        ps.setString(1, nombre);
                        ps.setString(2, apellidos);
                        ps.executeUpdate();
                        ps.close();
                        /*statement = conn.createStatement();
                        rs = statement.executeQuery("SELECT MAX(ID) FROM PERSONA LIMIT 1;");
                        while (rs.next()) {
                            id = rs.getInt(0);
                        }
                        System.out.println(id);*/
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    } finally {
                        try {
                            if (ps != null) {
                                ps.close();
                            }
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                    vista = "../WEB-INF/jsp/cuentaRegistrada.jsp";
                    break;
                }
            }

            RequestDispatcher rd = request.getRequestDispatcher(vista);
            rd.forward(request, response);
    }

    public void destroy() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
