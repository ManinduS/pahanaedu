<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Customers</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>
<h2>Customers</h2>

<form method="get" action="<%=request.getContextPath()%>/customers" style="margin:10px 0;display:flex;gap:8px;">
    <input name="q" value="<%= request.getParameter("q")==null? "" : request.getParameter("q") %>"
           placeholder="Search name / phone / email">
    <button type="submit">Search</button>
</form>

<table class="table">
    <tr><th>ID</th><th>Name</th><th>Phone</th><th>Email</th><th>Province</th></tr>
    <%
        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
        if (customers != null) {
            for (Customer c : customers) {
    %>
    <tr>
        <td><%=c.getId()%></td>
        <td><%=c.getName()%> <%= c.getSurname()==null?"":c.getSurname()%></td>
        <td><%=c.getPhone()%></td>
        <td><%=c.getEmail()%></td>
        <td><%=c.getProvince()%></td>
    </tr>
    <%
            }
        }
    %>
</table>

<p style="margin-top:12px;">
    <a href="customer.jsp">← Back to Customer</a>
</p>
</body>
</html>
