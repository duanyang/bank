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
<c:when test="${not empty sessionScope.userid}">
<c:if test="${not empty users}">
<table class="userList">
<tr>
<th>Name</th>
<th>Address</th>
<th>DOB</th>
<th>SSN</th>
</tr>

	<c:forEach items="${users}" var="user">
	<tr>
	<td>${user.name}</td>
	<td>${user.address}</td>
	<td>${user.dob }</td>
	<td>${user.ssn}</td>
	<td><a href="${pageContext.request.contextPath}/onlinebank/addAccountMenu?id=${user.id}">Add Account</a></td>
	<td><a href="${pageContext.request.contextPath}/onlinebank/closeAccountMenu?id=${user.id}">Close Account</a></td>
	</tr>
	</c:forEach>
</table>
</c:if>
<table class="pageCount">
<c:forEach var ="i" begin="1" end="${pagesCount}">
<td><a href="${pageContext.request.contextPath}/onlinebank/account?pageSize=3&pageNum=${i}">${i}</a></td>
</c:forEach>
</table>
</c:when>
<c:otherwise>
<jsp:forward page="home.jsp"/>
</c:otherwise>
</c:choose>
</body>
</html>