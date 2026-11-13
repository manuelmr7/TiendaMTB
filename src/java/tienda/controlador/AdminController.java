package tienda.controlador;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tienda.modelo.Role;
import tienda.modelo.Users;
import tienda.util.UserUtils;
import tienda.modelo.Category;
import tienda.modelo.Product;

/**
 * Panel de administración: solo accesible para usuarios con rol ADMIN.
 */
@WebServlet(name = "AdminController", urlPatterns = {"/admin","/admin/modif-catalogo"})
public class AdminController extends HttpServlet {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private static final Logger Log = Logger.getLogger(AdminController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Requiere estar identificado y ser administrador (si no, lanzan excepción)
            Users u = UserUtils.autentication(request, em);
            UserUtils.autorization(u, Role.ADMIN);

            List<Users> lu = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            request.setAttribute("users", lu);
            request.setAttribute("view", "admin");

        } catch (Exception ex) {
            Log.log(Level.WARNING, "Acceso denegado a /admin: {0}", ex.getMessage());
            request.setAttribute("view", "home");
            request.setAttribute("msg", "Acceso restringido: solo para administradores.");
            request.setAttribute("style", "danger");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/template.jsp");
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}