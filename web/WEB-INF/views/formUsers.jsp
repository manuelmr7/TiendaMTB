<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1>Web App</h1>
<h3>Alta Usuario</h3>

<form id="formulario" action="${pageContext.request.contextPath}/user/save" method="POST">
    <label for="name">Nombre:</label>
    <input id="name" type="text" name="name"><br />
    <label for="email">Correo:</label>
    <input id="email" type="text" name="email"><br />
    <label for="pwd">Clave: </label>
    <input id="pwd" type="password" name="password"><br />
    <input type="submit" value="Guardar" />
</form>

<a href="${pageContext.request.contextPath}/users">Inicio</a>
<script src="${pageContext.request.contextPath}/js/functions.js"></script>