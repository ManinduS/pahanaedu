<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Item</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>

<!-- Back to Manage Items -->
<p style="margin:20px;">
    <a class="btn" href="<%=request.getContextPath()%>/manageItem.jsp">← Back to Manage Items</a>
</p>

<h2>Add New Item</h2>
<% String err = (String) request.getAttribute("error"); if (err!=null){ %>
<div class="error"><%=err%></div>
<% } %>

<form method="post" action="<%=request.getContextPath()%>/items/add">
    <label>Name</label><input name="name" required>
    <label>Description</label><textarea name="description" required></textarea>
    <label>Price</label><input type="number" step="0.01" name="price" required>
    <label>Quantity</label><input type="number" name="quantity" required>
    <button type="submit">Save</button>
    <a class="btn" href="<%=request.getContextPath()%>/items">Cancel</a>
</form>
</body>
</html>
