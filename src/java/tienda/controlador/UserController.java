/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package tienda.controlador;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import tienda.modelo.Users;
import tienda.util.PasswordUtils;

/**
 *
 * @author manue
 */
@WebServlet(name = "UserController", urlPatterns = {"/users", "/user/*"})
public class UserController extends HttpServlet {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(UserController.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String vista;
        String accion = "/users";
        if (request.getServletPath().equals("/user")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            } else {
                accion = "error";
            }
        }
        switch (accion) {
            case "/users" -> {
                // El listado de usuarios ahora está en el panel de administración
                response.sendRedirect(request.getContextPath() + "/admin");
                return;
            }
            case "/new" -> {
                vista = "formUsers";
            }
            default -> {
                vista = "error";
            }
        }
        request.setAttribute("view", vista);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/template.jsp");
        rd.forward(request, response);
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

        HttpSession session = request.getSession();
        String cp = request.getContextPath();
        String accion = request.getPathInfo();

        if ("/save".equals(accion)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String pwd1 = request.getParameter("pwd1");
            String pwd2 = request.getParameter("pwd2");

            String msg;
            String style;
            String destino;

            try {
                if (name == null || email == null || pwd1 == null || pwd2 == null
                        || name.isBlank() || email.isBlank() || pwd1.isEmpty() || pwd2.isEmpty()) {
                    throw new Exception("Datos no válidos");
                }
                if (!email.contains("@")) {
                    throw new Exception("Correo no válido");
                }
                if (!pwd1.equals(pwd2)) {
                    throw new Exception("Las claves no coinciden");
                }
                if (pwd1.length() < 8) {
                    throw new Exception("Clave demasiado corta");
                }

                String hashPwd = PasswordUtils.hashPassword(pwd1);
                Users u = new Users(name, email, hashPwd);
                save(u);

                msg = "Registro realizado correctamente. Ya puedes iniciar sesión.";
                style = "success";
                destino = cp + "/auth/login";

            } catch (Exception e) {
                Log.log(Level.WARNING, "Registro no válido: {0}", e.getMessage());
                msg = "ERROR: datos de registro no válidos";
                style = "danger";
                destino = cp + "/user/new";
            }

            // El mensaje viaja en la sesión porque después hay una redirección
            session.setAttribute("msg", msg);
            session.setAttribute("style", style);
            response.sendRedirect(destino);

        } else {
            request.setAttribute("view", "error");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/template.jsp");
            rd.forward(request, response);
        }
    }

    public void save(Users u) {
        Long id = u.getId();
        try {
            utx.begin();
            if (id == null) {
                em.persist(u);
                Log.log(Level.INFO, "New User saved");
            } else {
                Log.log(Level.INFO, "User {0} updated", id);
                em.merge(u);
            }
            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controlador de usuarios";
    }// </editor-fold>

}
