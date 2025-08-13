<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Cart,model.CartItem" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) { cart = new model.Cart(); session.setAttribute("cart", cart); }
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Billing</title>
    <link rel="stylesheet" href="<%=ctx%>/css/billing.css">
</head>
<body>
<div class="wrap">
    <div class="topbar">
        <a class="back" href="welcome.jsp">← Back</a>
        <h1>PahanaEdu — Billing</h1>
    </div>

    <!-- Optional Customer Details -->
    <form class="customer" method="post" action="billing">
        <input type="hidden" name="action" value="noop">
        <div>
            <label>Customer Name (optional)</label>
            <input name="cust_name" form="checkoutForm" placeholder="e.g. Nimal Perera">
        </div>
        <div>
            <label>Phone</label>
            <input name="cust_phone" form="checkoutForm" placeholder="07XXXXXXXX">
        </div>
        <div>
            <label>Address 1</label>
            <input name="cust_addr1" form="checkoutForm" placeholder="Line 1">
        </div>
        <div>
            <label>Address 2</label>
            <input name="cust_addr2" form="checkoutForm" placeholder="Line 2">
        </div>
    </form>

    <!-- Search -->
    <form class="search" method="get" action="billing">
        <input type="text" name="q" placeholder="Search items by name..." value="<%= request.getParameter("q")==null?"":request.getParameter("q") %>" required>
        <button type="submit">Search</button>
    </form>

    <!-- Results -->
    <%
        java.util.List<model.Item> results = (java.util.List<model.Item>) request.getAttribute("results");
        if (results != null) {
    %>
    <div class="results">
        <h3>Results</h3>
        <table>
            <tr><th>Name</th><th>Price</th><th style="width:180px">Add</th></tr>
            <% for (model.Item it : results) { %>
            <tr>
                <td><%= it.getName() %></td>
                <td>Rs. <%= it.getPrice() %></td>
                <td>
                    <form method="post" action="billing" class="inline">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="id" value="<%= it.getId() %>">
                        <input type="number" name="qty" value="1" min="1" required>
                        <button type="submit">Add</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
    <% } %>

    <!-- Cart -->
    <div class="cart">
        <h3>Cart</h3>
        <form method="post" action="billing" class="inline">
            <input type="hidden" name="action" value="clear">
            <button type="submit" class="danger" <% if(cart.isEmpty()){ %>disabled<% } %>>Clear</button>
        </form>
        <table>
            <tr><th>Item</th><th>Qty</th><th>Unit</th><th>Amount</th><th></th></tr>
            <% for (CartItem ci : cart.getItems()) { %>
            <tr>
                <td><%= ci.getItem().getName() %></td>
                <td>
                    <form method="post" action="billing" class="inline">
                        <input type="hidden" name="action" value="updateQty">
                        <input type="hidden" name="id" value="<%= ci.getItem().getId() %>">
                        <input type="number" name="qty" value="<%= ci.getQty() %>" min="1" required>
                        <button type="submit">Update</button>
                    </form>
                </td>
                <td>Rs. <%= ci.getUnitPrice() %></td>
                <td>Rs. <%= ci.getAmount() %></td>
                <td>
                    <form method="post" action="billing" class="inline">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="id" value="<%= ci.getItem().getId() %>">
                        <button type="submit" class="danger">✕</button>
                    </form>
                </td>
            </tr>
            <% } %>
            <tr class="totals">
                <td colspan="2"></td>
                <td>Subtotal</td>
                <td>Rs. <%= cart.getSubtotal() %></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="2"></td>
                <td>
                    <form method="post" action="billing" class="inline">
                        <input type="hidden" name="action" value="discount">
                        <label>Discount %</label>
                        <input type="number" name="pct" min="0" max="100" step="0.01" value="<%= cart.getDiscountPct() %>">
                        <button type="submit">Apply</button>
                    </form>
                </td>
                <td>− Rs. <%= cart.getDiscountAmount() %></td>
                <td></td>
            </tr>
            <tr class="grand">
                <td colspan="2"></td>
                <td>Total</td>
                <td>Rs. <%= cart.getTotal() %></td>
                <td></td>
            </tr>
        </table>

        <form id="checkoutForm" method="post" action="billing" class="checkout">
            <input type="hidden" name="action" value="checkout">
            <button type="submit" <% if(cart.isEmpty()){ %>disabled<% } %>>Print / Download Bill</button>
        </form>
    </div>
</div>
</body>
</html>
