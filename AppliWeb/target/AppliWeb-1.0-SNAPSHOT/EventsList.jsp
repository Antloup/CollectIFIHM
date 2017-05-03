<%-- 
    Document   : EventsList
    Created on : 10 avr. 2017, 16:30:30
    Author     : Anthony
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
        System.out.println(request.getAttribute("ListActivite"));                
        %>
    </body>
</html>
