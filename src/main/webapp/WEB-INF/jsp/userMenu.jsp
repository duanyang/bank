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
<table class="userMenu">
<tr>
<td><a href="${pageContext.request.contextPath}/onlinebank/viewAccounts">View Accounts</a></td>
<td><a href="${pageContext.request.contextPath}/onlinebank/transfer">Transfer Money</a></td>
<td><a href="${pageContext.request.contextPath}/onlinebank/faq">FAQ</a></td>
<td><a href="${pageContext.request.contextPath}/onlinebank/contact">Contact</a></td>
<td><a href="${pageContext.request.contextPath}/onlinebank/logout">Logout</a></td>
</tr>
</table>
<table class="accounts">
<c:if test="${not empty saving }">
<tr>
<td><a href="${pageContext.request.contextPath}/onlinebank/saving?pk=${saving.id}&pageSize=5&pageNum=1">Saving ${saving.accountNumber }</a></td>
</tr>
</c:if>
<c:if test="${not empty checking}">
<tr>
<td><a href="${pageContext.request.contextPath}/onlinebank/checking?pk=${checking.id}&pageSize=5&pageNum=1">Checking ${checking.accountNumber }</a></td>
</tr>
</c:if>
</table>

<c:if test="${not empty transfer}">
<form action="processTransfer" method="post">
<table class="transfer">
<tr class="transfer">
<td class="transfer">
<select name="fromSelect">
<option value="Checking">Checking</option>
 <option value="Saving">Saving</option>
</select>
</td>
</tr >
<tr class="transfer">
<td class="transfer"><input type="text" name="fromAccount" placeholder="Enter From Account"/></td>
</tr >
<tr class="transfer">
<td class="transfer"><input type="text" name="fromRouting" placeholder="Enter From Routing"/></td>
</tr>
<tr class="transfer">
<td class="transfer">
<select name="toSelect">
<option value="Checking">Checking</option>
 <option value="Saving">Saving</option>
</select>
</td>
</tr >
<tr class="transfer">
<td class="transfer"><input type="text" name="toAccount" placeholder="Enter To Account"/></td>
</tr>
<tr class="transfer">
<td class="transfer"><input type="text" name="toRouting" placeholder="Enter To Routing"/></td>
</tr>
<tr class="transfer">
<td class="transfer"><input type="text" name="amount" placeholder="Enter Amount"/></td>
</tr>
<tr class="transfer">
<td class="transfer"><input type="submit" name="transfer"/></td>
</tr>
</table>
</form>

</c:if>

</c:when>
<c:otherwise>
<jsp:forward page="home.jsp"/>
</c:otherwise>
</c:choose>
</body>
</html>