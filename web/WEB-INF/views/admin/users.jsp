<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<h2 class="h5 mb-3">Administración de usuarios</h2>

<c:if test="${!empty requestScope.users}">
    <div class="table-responsive">
        <table class="table table-striped align-middle">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Rol</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${requestScope.users}">
                    <tr>
                        <td>${user.id}</td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td>
                            <span class="badge ${user.role == 'ADMIN' ? 'text-bg-danger' : 'text-bg-secondary'}">${user.role}</span>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<c:if test="${empty requestScope.users}">
    <p class="text-muted">No hay usuarios todavía.</p>
</c:if>