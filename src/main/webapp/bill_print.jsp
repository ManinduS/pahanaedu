<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Cart,model.CartItem" %>
<%
    Cart cart = (Cart) request.getAttribute("cart");
    String cname = (String) request.getAttribute("cust_name");
    String cphone= (String) request.getAttribute("cust_phone");
    String a1    = (String) request.getAttribute("cust_addr1");
    String a2    = (String) request.getAttribute("cust_addr2");
    String orderId = (String) request.getAttribute("orderId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Bill <%=orderId%></title>
    <style>
        *{box-sizing:border-box;font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial}
        .receipt{max-width:720px;margin:24px auto;padding:24px;border:1px solid #ddd;background:#fff}
        .head{text-align:center;margin-bottom:12px}
        .meta{display:flex;justify-content:space-between;font-size:14px;margin:8px 0}
        table{width:100%;border-collapse:collapse;margin-top:12px}
        th,td{border-bottom:1px solid #eee;padding:8px;text-align:left}
        .right{text-align:right}
        .totals td{font-weight:600}
        .grand td{font-size:18px}
        .printbar{max-width:720px;margin:12px auto;text-align:center}
        @media print {.printbar{display:none} .receipt{border:none}}
    </style>
</head>
<body>
<div class="printbar">
    <button onclick="window.print()">Print / Save as PDF</button>
    <a href="welcome.jsp">Back</a>
</div>

<div class="receipt">
    <div class="head">
        <h2>PahanaEdu</h2>
        <div>Bill No: <%=orderId%></div>
        <small><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()) %></small>
    </div>

    <div class="meta">
        <div>
            <strong>Bill To</strong><br>
            <%= cname==null?"":cname %><br>
            <%= cphone==null?"":cphone %><br>
            <%= a1==null?"":a1 %><br>
            <%= a2==null?"":a2 %>
        </div>
        <div style="text-align:right">
            <strong>From</strong><br>
            PahanaEdu POS<br>
            Sri Lanka
        </div>
    </div>

    <table>
        <tr><th>Item</th><th>Qty</th><th class="right">Unit</th><th class="right">Amount</th></tr>
        <% for (CartItem ci : cart.getItems()) { %>
        <tr>
            <td><%= ci.getItem().getName() %></td>
            <td><%= ci.getQty() %></td>
            <td class="right">Rs. <%= ci.getUnitPrice() %></td>
            <td class="right">Rs. <%= ci.getAmount() %></td>
        </tr>
        <% } %>
        <tr class="totals"><td colspan="3" class="right">Subtotal</td><td class="right">Rs. <%= cart.getSubtotal() %></td></tr>
        <tr class="totals"><td colspan="3" class="right">Discount (<%= cart.getDiscountPct() %>%)</td><td class="right">− Rs. <%= cart.getDiscountAmount() %></td></tr>
        <tr class="grand"><td colspan="3" class="right">TOTAL</td><td class="right">Rs. <%= cart.getTotal() %></td></tr>
    </table>

    <p style="margin-top:24px;text-align:center">Thank you! — PahanaEdu</p>
</div>
</body>
</html>
