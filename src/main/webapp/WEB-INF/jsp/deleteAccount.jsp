<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
<title>Insert title here</title>
</head>
<body>
<img  class="bank" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEoSsSEqrWznDYj8o2FRqwSkWFDQDQ9MtamSiKC0OLPhtlSrFGYQ">
<c:choose>
<c:when test="${not empty sessionScope.userid }">
<table class="accounts">
<c:if test="${not empty user.savingAccount}">
<tr>
<td><a href="${pageContext.request.contextPath}/onlinebank/deleteAccount?pk=${user.id}&type=save">Delete Saving ${user.savingAccount.accountNumber }</a></td>
</tr>
</c:if>
<c:if test="${not empty user.checkingAccount}">
<tr>
<td><a href="${pageContext.request.contextPath}/onlinebank/deleteAccount?pk=${user.id}&type=check">Delete Checking ${user.checkingAccount.accountNumber }</a></td>
</tr>
</c:if>
</table>
</c:when>
<c:otherwise>
<jsp:forward page="home.jsp"/>
</c:otherwise>
</c:choose>

</body>
</html>