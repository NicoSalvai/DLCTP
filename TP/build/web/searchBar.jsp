<%-- 
    Document   : searchBar
    Created on : Apr 15, 2019, 12:27:21 PM
    Author     : dlcusr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="searchBar" class="container">    
    <div class="row">    
        <form accept-charset="UTF-8" id="searchForm" method="doPost" class="input-group mb-3 col-sm-6 offset-sm-3" action="<c:url value="/busqueda"/>">
            <input type="text" name="searchText" class="form-control" placeholder="Buscar.....">
            <div class="input-group-append">
                <input type="submit" value="Search" class="btn btn-outline-secondary btn-dark" id="button-search">  
            </div>
        </form>
    </div>
</div>
