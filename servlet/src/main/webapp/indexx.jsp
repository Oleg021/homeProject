<%@page import="java.util.ArrayList" %>
<%@ page import="ua.com.yaniv.Request" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Servlet</title>
    <style type="text/css">
        table {
            font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
            font-size: 14px;
            background: white;
            max-width: 100%;
            width: 100%;
            border-collapse: collapse;
            text-align: left;
        }
        th {
            font-weight: normal;
            color: #039;
            border-bottom: 2px solid #6678b1;
            padding: 10px 8px;
        }
        td {
            border-bottom: 1px solid #ccc;
            color: #669;
            padding: 9px 8px;
            transition: .3s linear;
        }
        tr:hover td {
            color: #6699ff;
        }
    </style>
</head>

<body>
<%ArrayList<Request> repository = (ArrayList<Request>) request.getAttribute("repository");%>
<table>
    <tr>
        <th>IP</th>
        <th>user-agent</th>
        <th>time</th>
    </tr>
    <%for (Request repoRequest : repository) { %>
    <tr>
        <td <% if (repoRequest.getIpAddress().equals(request.getRemoteAddr())) { %> style="font-weight:900" <% } %>>
            <%out.println(repoRequest.getIpAddress()); %></td>
        <td <% if (repoRequest.getIpAddress().equals(request.getRemoteAddr())) { %> style="font-weight:900" <% } %>>
            <%out.println(repoRequest.getUserAgent()); %></td>
        <td> <%out.println(repoRequest.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))); %></td>
    </tr>
    <%}%>
</table>
</body>
</html>