package tienda.controlador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import tienda.modelo.Users;

/**
 * Responde a la petición Fetch del registro: ¿está ya en uso este email?
 * Devuelve un texto: "Ok" si está libre, o un mensaje si ya existe.
 */
@WebServlet(name = "CheckEmailController", urlPatterns = {"/checkemail"})
public class CheckEmailController extends HttpServlet {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String respuesta;

        // Comprobar validez del dato recibido
        if (email == null || email.isBlank() || email.length() > 255
                || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            respuesta = "Email no válido";
        } else {
            List<Users> lu = em.createNamedQuery("Users.findByEmail", Users.class)
                    .setParameter("email", email.trim())
                    .getResultList();
            respuesta = lu.isEmpty() ? "Ok" : "Este email ya está en uso";
        }

        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        try (PrintWriter out = response.getWriter()) {
            out.print(respuesta);
        }
    }
}