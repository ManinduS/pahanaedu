<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Help • Pahana Edu Billing System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/help.css">
</head>
<body>
<div class="wrap">

    <div class="topbar">
        <div>
            <a class="btn" href="<%=request.getContextPath()%>/welcome.jsp">← Back to Home</a>
            <a class="btn" href="<%=request.getContextPath()%>/login.jsp">Go to Login</a>
        </div>
        <span class="muted">Pahana Edu Billing System • Help & User Guide</span>
    </div>

    <div class="card">
        <h1>Getting Started</h1>
        <p>
            This guide shows new users how to use the Pahana Edu Billing System to manage
            <strong>Customers</strong>, <strong>Items</strong>, and <strong>Billing</strong>, and how to log out safely.
        </p>
        <div class="hint muted">
            Tip: Press <span class="kbd">Ctrl</span> + <span class="kbd">F</span> to search this page quickly.
        </div>
    </div>

    <!-- LOGIN -->
    <div id="login" class="card">
        <h2>1) Login</h2>
        <ul class="check">
            <li>Go to <code>login.jsp</code> and enter your username and password.</li>
            <li>Click <em>Login</em> to access the system dashboard.</li>
        </ul>
    </div>

    <!-- CUSTOMERS -->
    <div id="customers" class="card">
        <h2>2) Customer Management</h2>
        <ul class="check">
            <li><strong>Add Customer</strong>: Fill in Name, Phone, Address, Email.</li>
            <li><strong>View/Search</strong>: Use filters to find customer details.</li>
            <li><strong>Edit/Delete</strong>: Select one row to edit, multiple to delete.</li>
        </ul>
    </div>

    <!-- ITEMS -->
    <div id="items" class="card">
        <h2>3) Item Management</h2>
        <ul class="check">
            <li><strong>Add Items</strong>: Provide Name, Description, Price, Quantity.</li>
            <li><strong>View Items</strong>: Filter by price, stock, or search by name.</li>
            <li><strong>Edit/Delete</strong>: Single edit, bulk delete available.</li>
        </ul>
    </div>

    <!-- BILLING -->
    <div id="billing" class="card">
        <h2>4) Billing & Printing</h2>
        <ul class="check">
            <li>Search and add items to the cart.</li>
            <li>Adjust quantities; totals update automatically.</li>
            <li>Click <em>Print Bill</em> to print and save the purchase.</li>
        </ul>
    </div>

    <!-- LOGOUT -->
    <div id="logout" class="card">
        <h2>5) Logout</h2>
        <p>Click <em>Logout</em> or go to <code>logout.jsp</code> to end your session securely.</p>
    </div>

    <!-- TROUBLESHOOTING -->
    <div id="troubleshoot" class="card">
        <h2>Troubleshooting</h2>
        <ul>
            <li><strong>Invalid input</strong>: Check required fields (e.g., phone digits only).</li>
            <li><strong>Login failed</strong>: Verify username/password (case sensitive).</li>
            <li><strong>Data not saving</strong>: Ensure database is running and connected.</li>
        </ul>
    </div>

</div>
</body>
</html>
