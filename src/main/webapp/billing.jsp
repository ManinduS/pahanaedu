<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Cart,model.CartItem,model.Customer" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) { cart = new model.Cart(); session.setAttribute("cart", cart); }
    String ctx = request.getContextPath();
    Customer sel = (Customer) session.getAttribute("billingCustomer");
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

    <!-- Customer selection (picked from customer section; no manual typing) -->
    <div class="results">
        <h3>Customer</h3>

        <% if (request.getParameter("needCustomer") != null) { %>
        <div style="color:#b00;margin-bottom:8px">Please select a customer before checkout.</div>
        <% } %>

        <% if (sel != null) { %>
        <div style="display:flex;justify-content:space-between;align-items:center">
            <div>
                <strong>
                    <%= sel.getName() %>
                    <%= (sel.getSurname()==null ? "" : " " + sel.getSurname()) %>
                </strong><br>
                <small><%= sel.getPhone()==null?"":sel.getPhone() %></small><br>
                <small>
                    <%= sel.getAddress1()==null?"":sel.getAddress1() %>
                    <%= sel.getAddress2()==null?"":" | "+sel.getAddress2() %>
                </small>
            </div>
            <form method="post" action="billing" class="inline">
                <input type="hidden" name="action" value="clearCustomer">
                <button type="submit" class="danger">Change</button>
            </form>
        </div>
        <% } else { %>
        <!-- Quick customer search -->
        <form class="search" method="get" action="billing">
            <input type="text" name="custq" placeholder="Search customer by name or phone..." required>
            <button type="submit">Search</button>
        </form>

        <%
            java.util.List<model.Customer> custResults =
                    (java.util.List<model.Customer>) request.getAttribute("custResults");
            if (custResults != null) {
        %>
        <table>
            <tr><th>Name</th><th>Phone</th><th>Address</th><th style="width:140px"></th></tr>
            <% for (model.Customer c : custResults) { %>
            <tr>
                <td><%= c.getName() %> <%= c.getSurname()==null?"":c.getSurname() %></td>
                <td><%= c.getPhone()==null?"":c.getPhone() %></td>
                <td>
                    <%= c.getAddress1()==null?"":c.getAddress1() %>
                    <%= c.getAddress2()==null?"":" | "+c.getAddress2() %>
                </td>
                <td>
                    <form method="post" action="billing" class="inline">
                        <input type="hidden" name="action" value="selectCustomer">
                        <input type="hidden" name="id" value="<%= c.getId() %>">
                        <button type="submit">Select</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
        <% } %>

        <p style="margin-top:8px">
            Tip: From your customer list, link to
            <code><%=ctx%>/billing?customerId=&lt;id&gt;</code>
            to pre-select a customer.
        </p>
        <% } %>
    </div>

    <!-- Item Search -->
    <form class="search" method="get" action="billing">
        <input type="text" name="q" placeholder="Search items by name..."
               value="<%= request.getParameter("q")==null?"":request.getParameter("q") %>" required>
        <button type="submit">Search</button>
    </form>

    <!-- Item Results -->
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
            <button type="submit" <% if(cart.isEmpty()){ %>disabled<% } %>>Save &amp; Print</button>
        </form>
    </div>
</div>
</body>
</html>
