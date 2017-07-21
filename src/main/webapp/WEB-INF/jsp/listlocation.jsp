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
<c:when test="${not empty locations }">
<table class="locationList">
<tr>
<td>Street</td>
<td>City</td>
<td>Zipcode</td>
</tr>
<c:forEach items="${locations }" var="location">
<tr>
<td>${location.street }</td>
<td>${location.city }</td>
<td>${location.zip }</td>
</tr>
</c:forEach>
</table>
</c:when>
<c:otherwise>
<p>No location for this zipcode</p>
</c:otherwise>
</c:choose>
</body>
</html>