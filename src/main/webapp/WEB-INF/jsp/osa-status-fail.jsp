<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Status Fail</title>
</head>
<body>
	<h2>Status Fail</h2>


	<h3>Errors</h3>

	<ul>
		<c:forEach items="${osaPage.errors}" var="item">
			<li><c:out value="${item}">
				</c:out></li>
		</c:forEach>
	</ul>
</body>
</html>