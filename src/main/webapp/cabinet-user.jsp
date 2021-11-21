<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="by.hrachyshkin.provider.model.Account" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <jsp:include page="head.jsp"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <h2 class="text-center"><fmt:message key="userCabinetHeaderLabel"/></h2>
    <br>
    <div class="row table-responsive">
        <table class="table table-hover table-stripped w-auto small">
        <tr>
            <th><fmt:message key="emailLabel"/></th>
            <th><fmt:message key="roleLabel"/></th>
            <th><fmt:message key="nameLabel"/></th>
            <th><fmt:message key="telephoneLabel"/></th>
            <th><fmt:message key="addressLabel"/></th>
            <th><fmt:message key="balanceLabel"/></th>
        </tr>
        <tr>
            <form action="${url}/cabinet/accounts/update" method="POST">
                <td><input name="email" type="email" value="${account.email}" placeholder="${account.email}"/></td>
                <td>${account.role.name()}</td>
                <td><input name="name" type="text" value="${account.name}" placeholder="${account.name}"/></td>
                <td><input name="phone" type="tel" value="${account.phone}" placeholder="${account.phone}"/></td>
                <td><input name="address" type="text" value="${account.address}" placeholder="${account.address}"/>
                </td>
                <td>${account.balance}</td>
                <td>
                    <button type="submit" class="btn btn-info btn-sm"><fmt:message key="updateLabel"/></button>
                </td>
                <input name="accountId" type="hidden" value="${account.id}">
                <input name="role" type="hidden" value="${account.role.name()}">
                <input name="balance" type="hidden" value="${account.balance}">
            </form>
        </tr>
    </table>
    </div>

    <div class="row">
        <div class="col-md-2">
        </div>
        <div class="col-md-4 text-center">
            <h3>
                <fmt:message key="mySubscriptionsLabel"/>
                <a href="${url}/cabinet/subscriptions" class="btn btn-info btn-sm"><fmt:message
                        key="mySubscriptionsLabel"/></a>
            </h3>
        </div>
        <div class="col-md-4">
            <h3>
                <form action="${url}/cabinet/deposit" method="POST">
                    <input class="form-control input-sm" name="card" type="number"
                           placeholder="card number xxxx xxxx xxxx xxxx"/>
                    <input class="form-control input-sm" name="sum" type="number" value="sum" placeholder="sum"/>
                    <input class="form-control input-sm" name="validity" type="date" placeholder="VALID THRU"/>
                    <button type="submit" class="btn btn-info btn-sm"><fmt:message key="depositLabel"/></button>
                </form>
            </h3>
        </div>
    </div>
    <div class="col-md-2">
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>