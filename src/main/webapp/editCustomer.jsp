<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Customers</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>
<h2>Manage Customers</h2>

<form method="get" action="<%=request.getContextPath()%>/customers" style="margin:10px 0;display:flex;gap:8px;">
    <input type="hidden" name="mode" value="manage">
    <input name="q" value="<%= request.getParameter("q")==null? "" : request.getParameter("q") %>"
           placeholder="Search to filter">
    <button type="submit">Search</button>
</form>

<table class="table">
    <tr><th>ID</th><th>Name</th><th>Phone</th><th>Email</th><th>Hidden fields</th><th>Actions</th></tr>
    <%
        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
        if (customers != null) {
            for (Customer c : customers) {
    %>
    <tr>
        <form action="<%=request.getContextPath()%>/updateCustomer" method="post" style="display:inline;">
            <td>
                <input type="hidden" name="id" value="<%=c.getId()%>">
                <%=c.getId()%>
            </td>
            <td>
                <input name="name" value="<%=c.getName()%>">
                <input name="surname" value="<%=c.getSurname()==null? "": c.getSurname()%>">
            </td>
            <td><input name="phone"  value="<%=c.getPhone()%>"></td>
            <td><input type="email" name="email" value="<%=c.getEmail()%>"></td>

            <!-- keep addr/province if you don’t edit them here -->
            <td style="max-width:0;">
                <input type="hidden" name="address1" value="<%=c.getAddress1()%>">
                <input type="hidden" name="address2" value="<%=c.getAddress2()%>">
                <input type="hidden" name="province" value="<%=c.getProvince()%>">
            </td>
            <td>
                <button type="submit">Save</button>
        </form>
        <form action="<%=request.getContextPath()%>/deleteCustomer" method="post" style="display:inline;"
              onsubmit="return confirm('Delete this customer?');">
            <input type="hidden" name="id" value="<%=c.getId()%>">
            <button type="submit">Delete</button>
        </form>
        </td>
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
