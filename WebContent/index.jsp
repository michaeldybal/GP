<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>

<H1>Training test</H1>
<H2>Check notation:</H2>
	<form action="home" method="post">
	
	<input type="radio" name="notation" value="0" checked>10
	<input type="radio" name="notation" value="1">2 
	<input type="radio" name="notation" value="2">16
		<br><br><br>
		Enter Your expression here:
		<input type="text" name="expression" />
        <input type="submit" value="Execute" />
	</form>
</body>
</html>