<%@ page import="uk.ac.ucl.main.Patient" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Luxuryc
  Date: 2019-03-19
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Hahaha -->
<html>
<head>
    <title>Patient List</title>
</head>
<body>
    <h1 align="center"> Patient List </h1>

    <%
    for(Patient patient : (List<Patient>)request.getAttribute("patientList")){
    %>
        <p align="center"> <%= patient.getFirst()+" "+patient.getLast()%> </p>
    <%
    }
    %>
</body>
</html>