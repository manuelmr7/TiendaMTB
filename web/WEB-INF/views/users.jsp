<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<h1>Tienda MTB - Usuarios</h1>

<p>
    <a href="${pageContext.request.contextPath}/user/new" class="btn">Crear Nuevo Usuario</a>
</p>

<c:if test="${!empty requestScope.users}">
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Correo</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${requestScope.users}">
                <tr>
                    <td>${user.id}</td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.email}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty requestScope.users}">
    <p>Oops! No hay Usuarios todavía!</p>
</c:if>