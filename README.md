# 🚵 TiendaMTB

Aplicación web de comercio electrónico desarrollada bajo el patrón arquitectónico **Modelo-Vista-Controlador (MVC)** en **Java Enterprise**, orientada a la gestión y venta de componentes y accesorios de ciclismo de montaña (MTB).

![Pantalla Principal](web/img/pantallaPrincipal.JPG)

---

## ✨ Funcionalidades Principales

* **Catálogo y Detalle de Productos:** Exploración de artículos organizados por categorías (transmisión, ruedas, componentes) con vista individual de especificaciones, precio y disponibilidad en stock (`catalogo.jsp`, `detalle.jsp`).
* **Autenticación y Gestión de Usuarios:** Registro de nuevos clientes, inicio de sesión y validación de credenciales con cifrado de contraseñas (`AuthController`, `UserController`, `PasswordUtils`).
* **Panel de Administración por Roles:** Acceso restringido según rol (`Role`, `Users`) para la administración integral de usuarios (alta, baja, consulta y promoción de permisos) y mantenimiento del catálogo de productos (`AdminController`, `admin.jsp`).
* **Interfaz Responsiva y Gestión de Errores:** Diseño basado en **Bootstrap** combinado con estilos personalizados (`main.css`), plantilla común (`template.jsp`) y página dedicada de control de excepciones (`error.jsp`).

---

## 🛠️ Stack Tecnológico

| Capa / Ámbito | Tecnología |
| :--- | :--- |
| **Backend** | Java Servlets (Arquitectura MVC) |
| **Persistencia** | Jakarta Persistence / JPA (`persistence.xml`) + SQL (`data.sql`) |
| **Frontend** | JSP (JavaServer Pages), HTML5, CSS3, Bootstrap |
| **Servidor de Aplicaciones** | GlassFish Server (`glassfish-resources.xml`, `glassfish-web.xml`) |
| **Construcción e IDE** | Apache Ant (`build.xml`) / Apache NetBeans (`nbproject/`) |

---

## 📁 Estructura del Proyecto

```text
TiendaMTB/
├── nbproject/                  # Configuración del proyecto Apache NetBeans y Ant
├── setup/
│   └── glassfish-resources.xml # Pool de conexiones y recursos JDBC para GlassFish
├── src/
│   ├── conf/
│   │   ├── MANIFEST.MF
│   │   └── persistence.xml     # Configuración de la unidad de persistencia (JPA)
│   └── java/
│       ├── data.sql            # Script SQL de carga inicial de datos
│       └── tienda/
│           ├── controlador/    # Servlets controladores (MVC)
│           │   ├── AdminController.java
│           │   ├── AuthController.java
│           │   ├── HomeController.java
│           │   ├── ProductsController.java
│           │   └── UserController.java
│           ├── modelo/         # Entidades JPA del dominio
│           │   ├── Category.java
│           │   ├── Product.java
│           │   ├── Role.java
│           │   └── Users.java
│           └── util/           # Utilidades de seguridad y gestión de usuarios
│               ├── PasswordUtils.java
│               └── UserUtils.java
├── web/
│   ├── WEB-INF/
│   │   ├── glassfish-web.xml   # Descriptor de despliegue en GlassFish
│   │   └── views/              # Vistas JSP
│   │       ├── admin.jsp
│   │       ├── catalogo.jsp
│   │       ├── detalle.jsp
│   │       ├── error.jsp
│   │       ├── formUsers.jsp
│   │       ├── home.jsp
│   │       ├── login.jsp
│   │       └── template.jsp
│   ├── css/
│   │   ├── bootstrap.min.css
│   │   └── main.css
│   └── img/                    # Recursos gráficos y capturas
├── build.xml                   # Script de compilación y empaquetado Apache Ant
└── .gitignore
