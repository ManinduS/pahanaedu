<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Item" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Items</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>

<!-- Back to Manage Items -->
<p style="margin:20px;">
    <a class="btn" href="<%=request.getContextPath()%>/manageItem.jsp">← Back to Manage Items</a>
</p>

<h2>Items</h2>

<form method="get" action="<%=request.getContextPath()%>/items" class="search">
    <input type="text" name="q" placeholder="Search by name/description"
           value="<%= request.getParameter("q")==null ? "" : request.getParameter("q") %>">
    <button type="submit">Search</button>
    <a class="btn" href="<%=request.getContextPath()%>/addItem.jsp">Add Item</a>
</form>

<table>
    <thead>
    <tr><th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Qty</th><th>Actions</th></tr>
    </thead>
    <tbody>
    <% if (items != null) for (Item it : items) { %>
    <tr>
        <td><%=it.getId()%></td>
        <td><%=it.getName()%></td>
        <td><%=it.getDescription()%></td>
        <td><%=it.getPrice()%></td>
        <td><%=it.getQuantity()%></td>
        <td class="actions">
            <a class="link" href="<%=request.getContextPath()%>/items/edit?id=<%=it.getId()%>">Edit</a>
            <form method="post" action="<%=request.getContextPath()%>/items/delete" style="display:inline">
                <input type="hidden" name="id" value="<%=it.getId()%>">
                <button type="submit" onclick="return confirm('Delete this item?')">Delete</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
