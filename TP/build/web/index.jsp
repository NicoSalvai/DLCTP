<%-- 
    Document   : index2
    Created on : Apr 15, 2019, 11:50:56 AM
    Author     : dlcusr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <title>TP</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="<c:url value="/css/bootstrap-4.3.1-dist/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" />
    </head>
    
    <body>
        
        <div id="general" class="container">
            
            <jsp:include page="header.jsp"/>
             
            <jsp:include page="searchBar.jsp"/>
            
            <jsp:include page="footer.jsp"/>
            
        </div>
        
    </body>
</html>
