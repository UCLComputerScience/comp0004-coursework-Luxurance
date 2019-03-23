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
    <a href="/showPatientList/"><strong><-</strong>Back</a><br>
        <div id="menu" style="background-color:#C0C0C0;width:300px;text-align: center;float: left">
            <p><strong>Patient Name</strong></p>
            <%for(char letter = 'A'; letter <= 'Z'; letter++){%>
            <%if(letter==('A'+'Z')/2){%><br><%}%>
            <a href="/showPatientList/letter?letterSelected=<%=letter%>"><%=letter%></a>
            <%}%><br><br>
            <%for(Patient patient : (List<Patient>)request.getAttribute("patientList")){%>
            <a align="center" href="/showPatientList/name?patientSelected=<%=patient.getId()%>"> <%= patient.getFirst()+" "+patient.getLast()%> </a><br>
            <%}%>
        </div>
        <div id="content" style="background-color:#EEEEEE;width:750px;text-align: left;float: right">
            <%if(request.getAttribute("patientInfo") != null){%>
            <%for(String infoString : (List<String>)request.getAttribute("patientInfo")){%>
            <kbd><%=infoString%></kbd><br>
            <%}%>
            <%}else{%>
            <kbd>Patient information will be display here if selected.</kbd><br>
            <%}%>
        </div>
</body>
</html>