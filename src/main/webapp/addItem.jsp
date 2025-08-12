<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Item</title>
    <link rel="stylesheet" href="<c:url value='/css/manage_items.css'/>">
</head>
<body>

<!-- Back to Manage Items -->
<p style="margin:20px;">
    <a class="btn" href="<c:url value='/manageItem.jsp'/>">← Back to Manage Items</a>
</p>

<h2>Add New Item</h2>

<c:if test="${not empty success}">
    <div class="success">${success}</div>
</c:if>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

<form method="post" action="<c:url value='/items/add'/>" autocomplete="off">
    <label>Name</label>
    <input name="name" value="<c:out value='${name}'/>" placeholder="e.g., Blue Pen" required autofocus>

    <label>Description</label>
    <textarea name="description" placeholder="Short description" required><c:out value='${description}'/></textarea>

    <label>Price</label>
    <input type="number" step="0.01" min="0" name="price" value="<c:out value='${price}'/>" placeholder="0.00" required>

    <label>Quantity</label>
    <input type="number" step="1" min="0" name="quantity" value="<c:out value='${quantity}'/>" placeholder="0" required>

    <button type="submit">Save</button>
    <button type="reset" class="btn">Clear</button>
    <a class="btn" href="<c:url value='/items'/>">Cancel</a>
</form>

<script>
    // Auto-hide success banner after 3s
    const s = document.querySelector('.success');
    if (s) setTimeout(() => s.style.display = 'none', 3000);
</script>

</body>
</html>
