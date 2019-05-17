<%-- 
    Document   : busqueda
    Created on : Apr 15, 2019, 12:35:10 PM
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
            
            <div id="searchResults" class="container">
                <c:forEach items="${resultados}" var="res">       <%--  --%>
                    <div class="row">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${res.title}</h5>
                                <a href="<c:url value="/resultados?id=${res.id}"/>" class="card-link">resolver urls</a>
                            </div>
                        </div>  
                    </div>
                </c:forEach>        <%-- --%>
            </div>
            
            <jsp:include page="footer.jsp"/>
            
        </div>
        
    </body>
</html>