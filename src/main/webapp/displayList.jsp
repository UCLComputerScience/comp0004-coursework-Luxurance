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
        <div id="menu" style="background-color:#C0C0C0;width:300px;text-align: center;float: left">
            <a href="/showPatientList/"><strong><-</strong>Back</a>&nbsp;&nbsp;&nbsp;
            <a href="/showPatientList/statistics" target="_blank">Show Stats.</a><br>
            <p><strong>Patient Name</strong></p>
            <%for(char letter = 'A'; letter <= 'Z'; letter++){%>
                <%if(letter==('A'+'Z')/2){%><br><%}%>
                <a href="/showPatientList/letter?letterSelected=<%=letter%>"><%=letter%></a>
            <%}%><br><br>
            <form action="/showPatientList/search" method="post">
                Search by<br><br>
                gender: <input type="radio" name="gender" value="Male">Male
                <input type="radio" name="gender" value="Female">Female<br>
                age: from<input type="number" name="lowerAge" min="0" max="150">
                to<input type="number" name="upperAge" min="0" max="150"><br>
                name:<input type="text" name="name"><br>
                city:<input type="text" name="city"><br><br>
                <input type="submit" value="Apply">
            </form>
            <%for(Patient patient : (List<Patient>)request.getAttribute("patientList")){%>
                <a align="center" href="/showPatientList/name?patientSelected=<%=patient.getId()%>">
                <%= patient.getFirst()+" "+patient.getLast()%> </a><br>
            <%}%>
        </div>
        <div id="content" style="background-color:#EEEEEE;width:750px;text-align: left;float: right">
            <form action="/showPatientList/uploadFile" enctype="multipart/form-data" method="post">
                <input type="file" name="uploadFile" value="select CSV file">
                <input type="submit" value="upload">
                <strong>Upload CSV file please.</strong><br>
                <strong>The upload is for displaying various patients lists.</strong>
            </form>
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