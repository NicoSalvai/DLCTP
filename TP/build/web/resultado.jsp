<%-- 
    Document   : resultado
    Created on : May 17, 2019, 4:42:54 PM
    Author     : dlcusr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${resultado.title}</title>
    </head>
    <body>
        <h1>${resultado.title}</h1>
        <p>${contenido}</p>
    </body>
</html>
