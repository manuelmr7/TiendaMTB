package tienda.util;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import tienda.modelo.Role;
import tienda.modelo.Users;

/**
 * Utilidades para gestionar usuarios: autenticación y autorización.
 */
public class UserUtils {

    /**
     * Autenticación: comprueba que hay un usuario identificado en la sesión.
     * Devuelve el usuario (leído de la base de datos) o lanza una excepción.
     */
    public static Users autentication(HttpServletRequest request, EntityManager em) throws Exception {

        // getSession(false): no crea una sesión nueva si no existía
        HttpSession session = request.getSession(false);
        Users sessionUser = (session == null) ? null : (Users) session.getAttribute("user");

        if (sessionUser == null) {
            throw new Exception("Usuario no Autenticado");
        }

        // Se vuelve a leer de la base de datos para tener los datos actualizados
        Users user = em.find(Users.class, sessionUser.getId());
        if (user == null) {
            throw new Exception("Usuario no Autenticado");
        }

        return user;
    }

    /**
     * Autorización: comprueba que el usuario tiene el rol necesario.
     * Devuelve true o lanza una excepción.
     */
    public static boolean autorization(Users user, Role role) throws Exception {

        if (user.getRole() != role) {
            throw new Exception("Usuario no Autorizado");
        }

        return true;
    }
}