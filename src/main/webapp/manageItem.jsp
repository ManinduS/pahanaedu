<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Items</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>
<h1>Manage Items</h1>
<div class="grid">
    <a class="card" href="<%=request.getContextPath()%>/addItem.jsp">➕ Add Items</a>
    <a class="card" href="<%=request.getContextPath()%>/items">👀 View Items</a>
    <a class="card" href="<%=request.getContextPath()%>/items">✏️ Edit Items</a>
</div>
</body>
</html>
