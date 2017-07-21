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
<table class="faqanswer">
<tr>
<td>Question</td>
<td>${faq.question }</td>
</tr>
<tr>
<td>Answer</td>
<td>${faq.answer }</td>
</tr>
</table>
</body>
</html>