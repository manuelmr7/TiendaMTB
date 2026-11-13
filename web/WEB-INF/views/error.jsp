<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<div class="text-center py-5">
    <h1 class="display-4 fw-bold text-danger">¡Vaya!</h1>
    <p class="lead">
        <c:out value="${requestScope.msg}" default="La página solicitada no existe o la acción no está permitida."/>
    </p>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-dark">Volver al inicio</a>
</div>