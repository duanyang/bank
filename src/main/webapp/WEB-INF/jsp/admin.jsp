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
<c:when test="${not empty userid }">
<table class="admin">
<tr>
<td><a href="${pageContext.request.contextPath}/onlinebank/addUser">Add Customer</a></td>
<td><a href="${pageContext.request.contextPath}/onlinebank/account?pageSize=3&pageNum=1">Customer Account</a></td>
<td><a href="">Delete User</a></td>
<td><a href="${pageContext.request.contextPath}/onlinebank/logout">Logout</a></td>
</tr>
</table>
</c:when>
<c:otherwise>
<jsp:forward page="home.jsp"/>
</c:otherwise>
</c:choose>
</body>
</html>