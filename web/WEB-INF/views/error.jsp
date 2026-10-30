<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<h1>Tienda MTB</h1>
<h3>Ha ocurrido un error</h3>
<p><c:out value="${requestScope.msg}" default="La página solicitada no existe o la acción no está permitida."/></p>
<a href="${pageContext.request.contextPath}/users">Inicio</a>