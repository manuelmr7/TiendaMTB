/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package tienda.controlador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.List;
import java.util.logging.Logger;
import tienda.modelo.Users;
import tienda.util.PasswordUtils;

/**
 *
 * @author manue
 */
@WebServlet(name = "AuthController", urlPatterns = {"/auth/*"})
public class AuthController extends HttpServlet {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;
    private static final Logger Log = Logger.getLogger(AuthController.class.getName());

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
        String accion = request.getPathInfo();
        String vista = "error";

        if ("/login".equals(accion)) {
            vista = "login";
        } else if ("/logout".equals(accion)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
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
        String accion = request.getPathInfo();

        if (!"/login".equals(accion)) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
            rd.forward(request, response);
            return;
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
                List<Users> lista = em.createNamedQuery("Users.findByEmail", Users.class)
                        .setParameter("email", email)
                        .getResultList();

                if (!lista.isEmpty()
                        && PasswordUtils.checkPassword(password, lista.get(0).getPassword())) {
                    Users u = lista.get(0);
                    HttpSession session = request.getSession();
                    request.changeSessionId();
                    session.setAttribute("usuario", u);
                    Log.log(Level.INFO, "Login correcto: {0}", u.getEmail());
                    response.sendRedirect(request.getContextPath() + "/users");
                    return;
                }
            }
        } catch (Exception e) {
            Log.log(Level.SEVERE, "Error en el login", e);
        }

        request.setAttribute("msg", "Correo o clave incorrectos");
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        rd.forward(request, response);
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
