<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Customer</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>
<h2>Add Customer</h2>

<form action="<%=request.getContextPath()%>/addCustomer" method="post" class="form">
    <div style="display:grid;grid-template-columns:repeat(2,minmax(220px,1fr));gap:10px;max-width:720px;">
        <input name="name"     placeholder="Name *" required>
        <input name="surname"  placeholder="Surname (optional)">
        <input name="phone"    placeholder="Phone">
        <input type="email" name="email" placeholder="Email">
        <input name="address1" placeholder="Address line 1">
        <input name="address2" placeholder="Address line 2">
        <input name="province" placeholder="Province/State">
    </div>
    <br>
    <button type="submit">Save</button>
</form>

<p style="margin-top:12px;">
    <a href="customer.jsp">← Back to Customer</a>
</p>
</body>
</html>
