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
<form action="processAddUser" method="post">
<table class="addUser">
<tr>
<td><input type="text" name="address" placeholder="Enter Address"/></td>
</tr>
<tr>
<td><input type="text" name="dob" placeholder="Enter DOB"/></td>
</tr>
<tr>
<td><input type="email" name="email" placeholder="Enter Email"/></td>
</tr>
<tr>
<td><input type="text" name="name" placeholder="Enter Name"/></td>
</tr>
<tr>
<td><input type="text" name="username" placeholder="Enter Username"/></td>
</tr>
<tr>
<td><input type="text" name="password" placeholder="Enter Password"/></td>
</tr>
<tr>
<td><input type="text" name="phone" placeholder="Enter Phone"/></td>
</tr>
<tr>
<td><input type="text" name="ssn" placeholder="Enter SSN"/></td>
</tr>
<tr>
<td><input type="submit" value="add"/></td>
</tr>
</table>
</form>
</body>
</html>