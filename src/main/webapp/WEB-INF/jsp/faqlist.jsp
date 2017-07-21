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
<c:if test="${not empty faqs }">
<table class="faqlist">
<c:forEach items="${faqs }" var="faq">
<tr>
<td>FAQ Question</td>
<td><a href="${pageContext.request.contextPath}/onlinebank/getanswer?id=${faq.id}">${faq.question }</a></td>
</tr>
</c:forEach>
</table>
</c:if>
</body>
</html>