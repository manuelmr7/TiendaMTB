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
                List<Users> lu;
                TypedQuery<Users> q = em.createNamedQuery("Users.findAll", Users.class);
                lu = q.getResultList();
                request.setAttribute("users", lu);
                vista = "users";
            }
            case "/new" -> {
                vista = "formUsers";
            }
            default -> {
                vista = "error";
            }
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
        if (accion.equals("/save")) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String pwd = request.getParameter("password");
            try {
                if (name.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
                    throw new NullPointerException();
                }
                String hashPwd = PasswordUtils.hashPassword(pwd);
                Users u = new Users(name, email, hashPwd);
                save(u);
                response.sendRedirect("http://localhost:8080/tienda/users");
            } catch (Exception e) {
                request.setAttribute("msg", "Error: datos no válidos");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
                rd.forward(request, response);
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
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
        return "Short description";
    }// </editor-fold>

}
