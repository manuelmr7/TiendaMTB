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
import java.util.List;
import java.util.logging.Logger;
import tienda.modelo.Product;

/**
 *
 * @author manue
 */
@WebServlet(name = "ProductsController", urlPatterns = {"/products", "/product"})
public class ProductsController extends HttpServlet {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;
    private static final Logger Log = Logger.getLogger(ProductsController.class.getName());

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

        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            Product product = em.find(Product.class, id);

            request.setAttribute("product", product);
            // Indicamos a template.jsp que cargue detalle.jsp
            request.setAttribute("view", "detalle");
        } else {
            List<Product> lista = em.createNamedQuery("Product.findAll", Product.class).getResultList();

            request.setAttribute("products", lista);
            // Indicamos a template.jsp que cargue catalogo.jsp
            request.setAttribute("view", "catalogo");
        }

        // Siempre abrimos template.jsp, tal como pide el profesor
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
