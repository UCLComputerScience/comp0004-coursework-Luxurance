<%--
  Created by IntelliJ IDEA.
  User: Luxuryc
  Date: 2019-03-25
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>STATS</title>
</head>
<body>
    <h1 align="center">Patients Stats.</h1>
    <div align="center">
        <table border="1">
            <tr>
                <td>Mean Age</td>
                <td>Youngest</td>
                <td>Eldest</td>
                <td>
                    <form action="/showPatientList/statistics/getNumber" method="post">
                        Number of Patients in Ages
                        <input type="number" name="statsLower" min="0" max="150">
                        to
                        <input type="number" name="statsUpper" min="0" max="150">
                        <input type="submit" name="check" value="check">
                    </form>
                </td>
            </tr>
            <tr>
                <td><%=request.getAttribute("meanAge")%></td>
                <td><%=request.getAttribute("youngest")%></td>
                <td><%=request.getAttribute("eldest")%></td>
                <%if(request.getAttribute("patientCount")!=null){%>
                    <td><%=request.getAttribute("patientCount")%></td>
                <%}%>
            </tr>
        </table>
    </div>
</body>
</html>
