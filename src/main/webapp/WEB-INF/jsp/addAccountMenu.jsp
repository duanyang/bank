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

<form action="processAddAccount" method="post">
<table class="addAccount">
<tr>
<td><input type="hidden" name="id" value="${id }"/></td>
</tr>
<tr>
<td>
<select name="type">
<option value="checking">Checking</option>
<option value="saving">Saving</option>
</select>
</td>
</tr>
<tr>
<td><input type="text" name="accountNumber" placeholder="Account Number"/></td>
</tr>
<tr>
<td><input type="text" name="amount" placeholder="Amount"/></td>
</tr>
<tr>
<td><input type="text" name="routing" placeholder="Routing"/></td>
</tr>
<tr>
<td><input type="submit" value="submit"/></td>
</tr>
</table>
</form>

</body>
</html>