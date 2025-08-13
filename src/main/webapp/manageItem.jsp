<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Items</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>

<!-- Back to Welcome -->
<p style="margin:20px;">
    <a class="btn" href="<%=request.getContextPath()%>/welcome.jsp">← Back to Dashboard</a>
</p>

<h1>Manage Items</h1>
<div class="grid">
    <!-- Add Items -->
    <a class="card" href="<%=request.getContextPath()%>/addItem.jsp">➕ Add Items</a>

    <!-- View Items (read-only) -->
    <a class="card" href="<%=request.getContextPath()%>/items">👀 View Items</a>

    <!-- Edit Items (with Edit/Delete actions) -->
    <a class="card" href="<%=request.getContextPath()%>/items?mode=manage">✏️ Edit Items</a>
</div>
</body>
</html>
