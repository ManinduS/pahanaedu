<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (session != null) ? (String) session.getAttribute("username") : null;
    if (username == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pahana Edu — Welcome</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/welcome.css">
</head>
<body>
<header class="topbar">
    <div class="brand">Pahana Edu</div>
    <div class="right">
        <span class="user">Hi, <strong><%= username %></strong></span>
        <form action="<%= request.getContextPath() %>/logout" method="get" class="logout-form">
            <button type="submit" class="btn btn-outline">Logout</button>
        </form>
    </div>
</header>

<main class="container">
    <h1 class="title">Dashboard</h1>
    <p class="subtitle">Choose a section to continue</p>

    <section class="grid">
        <!-- Customer -->
        <a class="card" href="<%=request.getContextPath()%>/editCustomer.jsp">
            <div class="emoji">👥</div>
            <h2>Customer</h2>
            <p>Add, view, and edit customers.</p>
            <span class="cta">Open</span>
        </a>

        <!-- Items -->
        <a class="card" href="<%=request.getContextPath()%>/manageItem.jsp">
            <div class="emoji">📦</div>
            <h2>Items</h2>
            <p>Add items, view inventory, and edit details.</p>
            <span class="cta">Open</span>
        </a>

        <!-- Print Bill -->
        <a class="card" href="<%=request.getContextPath()%>/billing.jsp">
            <div class="emoji">🧾</div>
            <h2>Print Bill</h2>
            <p>Search products, create a bill, and print.</p>
            <span class="cta">Open</span>
        </a>
    </section>
</main>

<footer class="footer">
    <span>© <%= java.time.Year.now() %> Pahana Edu</span>
</footer>
</body>
</html>
