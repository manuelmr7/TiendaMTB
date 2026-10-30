<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<h1>Tienda MTB</h1>
<h3>Iniciar Sesión</h3>

<form action="${pageContext.request.contextPath}/auth/login" method="POST">
    <label for="email">Correo:</label>
    <input id="email" type="text" name="email"><br />
    <label for="pwd">Clave: </label>
    <input id="pwd" type="password" name="password"><br />
    <input type="submit" value="Entrar" />
</form>

<p>${requestScope.msg}</p>

<a href="${pageContext.request.contextPath}/users">Inicio</a>
<a href="${pageContext.request.contextPath}/user/new">Registrarse</a>