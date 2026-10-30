<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>TiendaMTB</title>
        <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${ctx}/css/main.css">
    </head>
    <body class="d-flex flex-column min-vh-100 ${requestScope.view == 'home' ? 'home-body' : ''}">

        <!-- Barra de navegación común -->
        <nav class="navbar navbar-expand-lg navbar-dark ${requestScope.view == 'home' ? 'navbar-home' : 'bg-dark'}">
            <div class="container">
                <a class="navbar-brand fw-bold" href="${ctx}/home">Tienda<span class="text-danger">MTB</span></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#menu" aria-controls="menu" aria-expanded="false" aria-label="Mostrar menú">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="menu">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item"><a class="nav-link" href="${ctx}/products">Catálogo</a></li>
                        <li class="nav-item"><a class="nav-link" href="${ctx}/users">Usuarios</a></li>
                    </ul>
                    <ul class="navbar-nav align-items-lg-center">
                        <c:if test="${empty sessionScope.usuario}">
                            <li class="nav-item"><a class="nav-link" href="${ctx}/auth/login">Entrar</a></li>
                            <li class="nav-item"><a class="btn btn-danger btn-sm ms-lg-2" href="${ctx}/user/new">Registrarse</a></li>
                        </c:if>
                        <c:if test="${!empty sessionScope.usuario}">
                            <li class="nav-item"><span class="navbar-text me-lg-3">Hola, <c:out value="${sessionScope.usuario.name}"/></span></li>
                            <li class="nav-item"><a class="btn btn-outline-light btn-sm" href="${ctx}/auth/logout">Salir</a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Contenido dinámico: catalogo.jsp, detalle.jsp, home.jsp, etc. -->
        <main class="${requestScope.view == 'home' ? 'flex-grow-1 d-flex align-items-center justify-content-center p-3' : 'container flex-grow-1 py-4'}">
            <jsp:include page="${requestScope.view}.jsp" />
        </main>

        <!-- Pie común -->
        <footer class="${requestScope.view == 'home' ? 'footer-home' : 'bg-dark'} text-center text-white-50 py-3">
            &copy; 2026. TiendaMTB - Desarrollo de Aplicaciones Web.
        </footer>

        <script src="${ctx}/js/bootstrap.bundle.min.js"></script>
    </body>
</html>