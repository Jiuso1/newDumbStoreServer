/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newDumbStoreServer.Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
    private static Cipher cipher;
    KeyGenerator keyGenerator;
    SecretKey secretKey;
    SecureRandom random;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void init() throws ServletException {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\jesus\\Documents\\desarrolloWeb\\daw\\newDumbStoreServer\\database\\dumbDB.db");
        } catch (ClassNotFoundException | SQLException ex) {

        }
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
        random = new SecureRandom();
        random.setSeed(547834);
        keyGenerator.init(random);
        secretKey = keyGenerator.generateKey();
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            System.out.println(ex.getMessage());
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
                    int idPersona = -1;
                    int idCredencial = -1;
                    String nombre = request.getParameter("nombre");
                    String apellidos = request.getParameter("apellidos");
                    String email = request.getParameter("correo");
                    String contrasenha = request.getParameter("contrasenha");

                    //Controles de seguridad, también en el servidor:
                    if (nombre.length() <= 0 && apellidos.length() <= 0) {//Solo aceptamos nombres mayores con más de 0 caracteres.
                        break;
                    }

                    if (!validateEmail(email)) {
                        break;
                    }

                    if (contrasenha.length() < 12) {
                        break;
                    }

                    //Insertamos en la tabla PERSONA:
                    ps = conn.prepareStatement("INSERT INTO PERSONA VALUES(NULL,?,?)");
                    ps.setString(1, nombre);
                    ps.setString(2, apellidos);
                    ps.executeUpdate();
                    ps.close();

                    //Conseguimos el id de la persona generada por sqlite:
                    statement = conn.createStatement();
                    rs = statement.executeQuery("SELECT MAX(ID) FROM PERSONA LIMIT 1;");
                    while (rs.next()) {
                        idPersona = rs.getInt(1);
                    }
                    statement.close();

                    //Insertamos en la tabla CREDENCIAL:
                    ps = conn.prepareStatement("INSERT INTO CREDENCIAL VALUES(NULL,?,?)");
                    ps.setString(1, email);

                    //Ciframos la contrasenha:
                    String contrasenhaCifrada = null;
                    contrasenhaCifrada = encrypt(contrasenha, secretKey);

                    ps.setString(2, contrasenhaCifrada);
                    ps.executeUpdate();
                    ps.close();

                    //Conseguimos el id de la credencial generada por sqlite:
                    statement = conn.createStatement();
                    rs = statement.executeQuery("SELECT MAX(ID) FROM CREDENCIAL LIMIT 1;");
                    while (rs.next()) {
                        idCredencial = rs.getInt(1);
                    }
                    statement.close();

                    //Insertamos en la tabla CUENTA con el id de la persona y el id de la credencial:
                    ps = conn.prepareStatement("INSERT INTO CUENTA VALUES(NULL,?,?)");
                    ps.setInt(1, idPersona);
                    ps.setInt(2, idCredencial);
                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                } catch (Exception ex) {
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
            case "/iniciarSesionBD": {
                String correo = request.getParameter("correo");
                String contrasenha = request.getParameter("contrasenha");
                String contrasenhaBD = null;
                String contrasenhaBDdescifrada = null;
                request.setAttribute("xd", "lalala");
                String idCredencial = null;
                String idCuenta = null;

                //Seleccionamos la contraseña con el email pasado (si no existe el email valdrá null):
                try {
                    ps = conn.prepareStatement("SELECT CONTRASENHA, ID FROM CREDENCIAL WHERE CORREO=?");
                    ps.setString(1, correo);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        contrasenhaBD = rs.getString("contrasenha");
                        idCredencial = rs.getString("id");
                    }
                    ps.close();

                    if (correo == null || contrasenhaBD == null) {
                        request.setAttribute("error", "error al iniciar sesión: verifique su correo y contraseña");
                    } else {
                        //Desciframos la contraseña de la BD:
                        contrasenhaBDdescifrada = decrypt(contrasenhaBD, secretKey);
                        if (!contrasenha.equals(contrasenhaBDdescifrada)) {
                            request.setAttribute("error", "error al iniciar sesión: verifique su correo y contraseña");
                        } else {
                            ps = conn.prepareStatement("SELECT ID FROM CUENTA WHERE CREDENCIAL=?");
                            ps.setString(1, idCredencial);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                idCuenta = rs.getString("id");
                                System.out.println(idCuenta);
                            }
                            ps.close();
                            request.setAttribute("idCuenta", idCuenta);//Mandamos por la request idCuenta.
                            //request.removeAttribute("error");
                        }
                    }

                } catch (Exception ex) {
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

                vista = "../WEB-INF/jsp/login.jsp";
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

    static Pattern emailPattern = Pattern.compile("^(.+)@(\\\\S+)$");

    public static boolean validateEmail(String email) {
        Matcher m = emailPattern.matcher(email);
        return !m.matches();
    }

    public static String encrypt(String plainText, SecretKey secretKey)
            throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
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
