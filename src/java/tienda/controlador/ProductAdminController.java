package tienda.controlador;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.transaction.UserTransaction;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import tienda.modelo.Category;
import tienda.modelo.Product;
import tienda.modelo.Role;
import tienda.modelo.Users;
import tienda.util.UserUtils;

/**
 * Gestión de productos (alta, edición y borrado). Solo para administradores.
 *   GET  /admin/products/new       -> formulario de alta
 *   GET  /admin/products/edit?id=  -> formulario de edición
 *   POST /admin/products/save      -> crea o actualiza (con imagen opcional)
 *   POST /admin/products/delete    -> borra
 */
@WebServlet(name = "ProductAdminController", urlPatterns = {"/admin/products/*"})
@MultipartConfig(maxFileSize = 2 * 1024 * 1024, maxRequestSize = 3 * 1024 * 1024)
public class ProductAdminController extends HttpServlet {

    @PersistenceContext(unitName = "TiendaPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private static final Logger Log = Logger.getLogger(ProductAdminController.class.getName());

    // Subcarpeta de /img donde se guardan las imágenes subidas
    private static final String UPLOAD_DIR = "products";

    // ------------------------------------------------------------------ GET

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            denied(request, response);
            return;
        }

        String action = request.getPathInfo();

        if ("/new".equals(action)) {
            showForm(request, response, new Product(), null);

        } else if ("/edit".equals(action)) {
            Long id = parseId(request.getParameter("id"));
            Product p = (id == null) ? null : em.find(Product.class, id);
            if (p == null) {
                flash(request, "Producto no encontrado.", "danger");
                response.sendRedirect(adminUrl(request));
                return;
            }
            showForm(request, response, p, null);

        } else {
            response.sendRedirect(adminUrl(request));
        }
    }

    // ----------------------------------------------------------------- POST

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            denied(request, response);
            return;
        }

        String action = request.getPathInfo();

        if ("/save".equals(action)) {
            save(request, response);
        } else if ("/delete".equals(action)) {
            delete(request, response);
        } else {
            response.sendRedirect(adminUrl(request));
        }
    }

    // ----------------------------------------------------- Crear / actualizar

    private void save(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // La parte del fichero se lee la primera: si supera el tamaño máximo, lanza excepción
        Part imagePart;
        try {
            imagePart = request.getPart("image");
        } catch (IllegalStateException e) {
            flash(request, "La imagen supera el tamaño máximo (2 MB).", "danger");
            response.sendRedirect(adminUrl(request));
            return;
        }

        Long id = parseId(request.getParameter("id"));   // null = producto nuevo
        String name = request.getParameter("name");
        String descripcion = request.getParameter("descripcion");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");
        Long categoryId = parseId(request.getParameter("categoryId"));

        Product current = (id == null) ? null : em.find(Product.class, id);
        if (id != null && current == null) {
            flash(request, "Producto no encontrado.", "danger");
            response.sendRedirect(adminUrl(request));
            return;
        }

        // ---- Validación en el servidor ----
        String error = null;
        float price = 0;
        int stock = 0;
        Category category = null;
        String ext = null;

        if (name == null || name.isBlank() || name.length() > 255) {
            error = "El nombre es obligatorio (máximo 255 caracteres).";
        } else if (descripcion != null && descripcion.length() > 1000) {
            error = "La descripción no puede superar los 1000 caracteres.";
        }

        if (error == null) {
            try {
                price = Float.parseFloat(priceStr.trim().replace(',', '.'));
                if (!(price > 0) || Float.isInfinite(price)) {
                    error = "El precio debe ser un número mayor que 0.";
                }
            } catch (Exception e) {
                error = "El precio no es válido.";
            }
        }

        if (error == null) {
            try {
                stock = Integer.parseInt(stockStr.trim());
                if (stock < 0) {
                    error = "El stock no puede ser negativo.";
                }
            } catch (Exception e) {
                error = "El stock debe ser un número entero.";
            }
        }

        if (error == null) {
            category = (categoryId == null) ? null : em.find(Category.class, categoryId);
            if (category == null) {
                error = "Elige una categoría válida.";
            }
        }

        boolean hasImage = imagePart != null && imagePart.getSize() > 0;
        if (error == null && hasImage) {
            ext = detectImageExtension(imagePart);
            if (ext == null) {
                error = "La imagen debe ser un JPG, PNG o WEBP.";
            }
        }

        // Si hay un error, se vuelve a mostrar el formulario con lo que escribió el usuario
        if (error != null) {
            Product form = new Product();
            form.setId(id);
            form.setName(name);
            form.setDescripcion(descripcion);
            form.setPrice(price);
            form.setStock(stock);
            form.setCategory(category);
            form.setImageFile(current == null ? null : current.getImageFile());
            request.setAttribute("priceText", priceStr);
            request.setAttribute("stockText", stockStr);
            showForm(request, response, form, error);
            return;
        }

        // ---- Guardado: primero el fichero y después la base de datos ----
        String newImage = null;
        try {
            if (hasImage) {
                newImage = storeImage(imagePart, ext);
            }

            String oldImage;
            utx.begin();
            Category cat = em.find(Category.class, categoryId);
            Product p = (id == null) ? new Product() : em.find(Product.class, id);
            if (p == null || cat == null) {
                throw new IllegalStateException("Producto o categoría no encontrados");
            }
            oldImage = p.getImageFile();

            p.setName(name.trim());
            p.setDescripcion(descripcion == null ? "" : descripcion.trim());
            p.setPrice(price);
            p.setStock(stock);
            p.setCategory(cat);
            if (newImage != null) {
                p.setImageFile(newImage);
            }
            if (id == null) {
                em.persist(p);
            }
            utx.commit();

            // Si se ha sustituido la imagen, se borra la anterior (solo si era una subida)
            if (newImage != null) {
                deleteUploadedImage(oldImage);
            }

            flash(request, id == null ? "Producto creado correctamente." : "Producto actualizado correctamente.", "success");
            response.sendRedirect(adminUrl(request));

        } catch (Exception e) {
            Log.log(Level.SEVERE, "Error al guardar el producto", e);
            rollback();
            deleteUploadedImage(newImage);   // no dejar una imagen huérfana
            Product form = new Product();
            form.setId(id);
            form.setName(name);
            form.setDescripcion(descripcion);
            form.setPrice(price);
            form.setStock(stock);
            form.setCategory(category);
            form.setImageFile(current == null ? null : current.getImageFile());
            request.setAttribute("priceText", priceStr);
            request.setAttribute("stockText", stockStr);
            showForm(request, response, form, "No se ha podido guardar el producto.");
        }
    }

    // ---------------------------------------------------------------- Borrar

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = parseId(request.getParameter("id"));
        String imageFile = null;
        boolean ok = false;

        try {
            utx.begin();
            Product p = (id == null) ? null : em.find(Product.class, id);
            if (p != null) {
                imageFile = p.getImageFile();
                em.remove(p);
                ok = true;
            }
            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "Error al borrar el producto", e);
            rollback();
            ok = false;
        }

        if (ok) {
            deleteUploadedImage(imageFile);
            flash(request, "Producto eliminado.", "success");
        } else {
            flash(request, "No se ha podido eliminar el producto.", "danger");
        }
        response.sendRedirect(adminUrl(request));
    }

    // ------------------------------------------------------------ Auxiliares

    /** Autenticación y autorización (con try/catch). */
    private boolean isAdmin(HttpServletRequest request) {
        try {
            Users u = UserUtils.autentication(request, em);
            UserUtils.autorization(u, Role.ADMIN);
            return true;
        } catch (Exception ex) {
            Log.log(Level.WARNING, "Acceso denegado a la gestión de productos: {0}", ex.getMessage());
            return false;
        }
    }

    private void denied(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("view", "home");
        request.setAttribute("msg", "Acceso restringido: solo para administradores.");
        request.setAttribute("style", "danger");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response,
            Product p, String msg) throws ServletException, IOException {

        List<Category> categories = em.createNamedQuery("Category.findAll", Category.class).getResultList();
        request.setAttribute("categories", categories);
        request.setAttribute("product", p);
        if (request.getAttribute("priceText") == null) {
            request.setAttribute("priceText", p.getId() == null ? "" : String.valueOf(p.getPrice()));
        }
        if (request.getAttribute("stockText") == null) {
            request.setAttribute("stockText", p.getId() == null ? "" : String.valueOf(p.getStock()));
        }
        if (msg != null) {
            request.setAttribute("msg", msg);
            request.setAttribute("style", "danger");
        }
        request.setAttribute("view", "productForm");
        forward(request, response);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/template.jsp");
        rd.forward(request, response);
    }

    /** El mensaje viaja en la sesión porque después hay una redirección. */
    private void flash(HttpServletRequest request, String msg, String style) {
        request.getSession().setAttribute("msg", msg);
        request.getSession().setAttribute("style", style);
    }

    private String adminUrl(HttpServletRequest request) {
        return request.getContextPath() + "/admin?tab=products";
    }

    private Long parseId(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Long.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void rollback() {
        try {
            utx.rollback();
        } catch (Exception ignore) {
            // si no había transacción activa, no hay nada que deshacer
        }
    }

    // -------------------------------------------------------------- Imágenes

    /** Carpeta física donde se guardan las imágenes subidas (dentro de la aplicación desplegada). */
    private Path uploadDir() throws IOException {
        String real = getServletContext().getRealPath("/img/" + UPLOAD_DIR);
        if (real == null) {
            throw new IOException("No se puede localizar la carpeta de imágenes");
        }
        return Paths.get(real);
    }

    /**
     * Comprueba el tipo real de la imagen mirando sus primeros bytes
     * (el tipo que declara el navegador se puede falsificar).
     * Devuelve la extensión o null si no es JPG, PNG o WEBP.
     */
    private String detectImageExtension(Part part) throws IOException {
        byte[] h = new byte[12];
        int n;
        try (InputStream in = part.getInputStream()) {
            n = in.readNBytes(h, 0, h.length);
        }
        if (n >= 3 && (h[0] & 0xFF) == 0xFF && (h[1] & 0xFF) == 0xD8 && (h[2] & 0xFF) == 0xFF) {
            return ".jpg";
        }
        if (n >= 4 && (h[0] & 0xFF) == 0x89 && h[1] == 'P' && h[2] == 'N' && h[3] == 'G') {
            return ".png";
        }
        if (n >= 12 && h[0] == 'R' && h[1] == 'I' && h[2] == 'F' && h[3] == 'F'
                && h[8] == 'W' && h[9] == 'E' && h[10] == 'B' && h[11] == 'P') {
            return ".webp";
        }
        return null;
    }

    /** Guarda la imagen con un nombre generado por el servidor (nunca el del usuario). */
    private String storeImage(Part part, String ext) throws IOException {
        Path dir = uploadDir();
        Files.createDirectories(dir);
        String fileName = UUID.randomUUID() + ext;
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, dir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
        return UPLOAD_DIR + "/" + fileName;   // ruta relativa a /img
    }

    /** Borra una imagen subida. Las de data.sql (fuera de /products) no se tocan. */
    private void deleteUploadedImage(String imageFile) {
        if (imageFile == null || !imageFile.startsWith(UPLOAD_DIR + "/")) {
            return;
        }
        try {
            Path dir = uploadDir().normalize();
            Path file = dir.resolve(imageFile.substring(UPLOAD_DIR.length() + 1)).normalize();
            if (file.startsWith(dir)) {
                Files.deleteIfExists(file);
            }
        } catch (IOException e) {
            Log.log(Level.WARNING, "No se pudo borrar la imagen {0}", imageFile);
        }
    }
}