<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Item" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
    String q = request.getParameter("q");
    String status = request.getParameter("status");
    if (status == null) status = "all"; // all | in | low | out

    // manage mode: show Edit/Delete when ?mode=manage OR servlet set attribute
    Boolean manageAttr = (Boolean) request.getAttribute("manage");
    boolean manage = (manageAttr != null && manageAttr) || "manage".equals(request.getParameter("mode"));
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= manage ? "Manage Items (Edit/Delete)" : "Items" %></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/manage_items.css">
</head>
<body>

<!-- Back to Manage Items -->
<p style="margin:20px;">
    <a class="btn" href="<%=request.getContextPath()%>/manageItem.jsp">← Back to Manage Items</a>
</p>

<h2><%= manage ? "Manage Items (Edit / Delete)" : "Items" %></h2>

<form method="get" action="<%=request.getContextPath()%>/items" class="search">
    <input type="text" name="q" placeholder="Search by name/description"
           value="<%= q == null ? "" : q %>">

    <!-- Stock filter -->
    <select name="status" title="Filter by stock">
        <option value="all" <%= "all".equals(status) ? "selected" : "" %>>All</option>
        <option value="in"  <%= "in".equals(status)  ? "selected" : "" %>>In stock (&gt; 0)</option>
        <option value="low" <%= "low".equals(status) ? "selected" : "" %>>Low stock (&le; 5)</option>
        <option value="out" <%= "out".equals(status) ? "selected" : "" %>>Out of stock (= 0)</option>
    </select>

    <% if (manage) { %>
    <!-- keep mode across searches -->
    <input type="hidden" name="mode" value="manage">
    <% } %>

    <button type="submit">Search</button>

    <% if (manage) { %>
    <a class="btn" href="<%=request.getContextPath()%>/addItem.jsp">Add Item</a>
    <a class="btn" href="<%=request.getContextPath()%>/items?mode=manage">Clear</a>
    <% } else { %>
    <a class="btn" href="<%=request.getContextPath()%>/items">Clear</a>
    <a class="btn" href="<%=request.getContextPath()%>/items?mode=manage">Switch to Edit Items</a>
    <% } %>
</form>

<table>
    <thead>
    <tr>
        <th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Qty</th>
        <% if (manage) { %><th>Actions</th><% } %>
    </tr>
    </thead>
    <tbody>
    <% if (items != null && !items.isEmpty()) {
        for (Item it : items) { %>
    <tr>
        <td><%= it.getId() %></td>
        <td><%= it.getName() %></td>
        <td><%= it.getDescription() %></td>
        <td><%= it.getPrice() %></td>
        <td><%= it.getQuantity() %></td>

        <% if (manage) { %>
        <td class="actions">
            <a class="link" href="<%=request.getContextPath()%>/items/edit?id=<%=it.getId()%>">Edit</a>
            <form method="post" action="<%=request.getContextPath()%>/items/delete" style="display:inline">
                <input type="hidden" name="id" value="<%=it.getId()%>">
                <button type="submit" onclick="return confirm('Delete this item?')">Delete</button>
            </form>
        </td>
        <% } %>
    </tr>
    <%   }
    } else { %>
    <tr>
        <td colspan="<%= manage ? 6 : 5 %>" style="text-align:center;color:#777;">No items found.</td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
