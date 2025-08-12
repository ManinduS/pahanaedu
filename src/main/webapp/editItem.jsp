<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Item" %>
<%
    Item item = (Item) request.getAttribute("item");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Item</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>

<!-- Back to Manage Items -->
<p style="margin:20px;">
    <a class="btn" href="<%=request.getContextPath()%>/manageItem.jsp">← Back to Manage Items</a>
</p>

<h2>Edit Item</h2>
<% if (item == null) { %>
<p>Item not found.</p>
<% } else { %>
<form method="post" action="<%=request.getContextPath()%>/items/edit">
    <input type="hidden" name="id" value="<%=item.getId()%>">
    <label>Name</label><input name="name" value="<%=item.getName()%>" required>
    <label>Description</label><textarea name="description" required><%=item.getDescription()%></textarea>
    <label>Price</label><input type="number" step="0.01" name="price" value="<%=item.getPrice()%>" required>
    <label>Quantity</label><input type="number" name="quantity" value="<%=item.getQuantity()%>" required>
    <button type="submit">Update</button>
    <a class="btn" href="<%=request.getContextPath()%>/items">Cancel</a>
</form>
<% } %>
</body>
</html>
