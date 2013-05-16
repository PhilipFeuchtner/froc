<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Status Ok</title>
 <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>"/>

</head>
<body>
	<h2>Status Ok</h2>

	<fieldset>
		<legend>Values</legend>

		Osa selected:[
		<c:out value="${uploadItem.osaList[0]}"></c:out>
		]
	</fieldset>

	<fieldset>
		<legend>Debug</legend>

		<table border="0">
			<tr>
				<td>Values changed</td>
				<td>&nbsp;</td>
				<td><c:out value="${osaPage.size()}"></c:out></td>
			</tr>
			<tr>
				<td>jdbc String</td>
				<td>&nbsp;</td>
				<td><c:out value="${dataBaseConfig.jdbcString}"></c:out></td>
			</tr>
			<tr>
				<td>User</td>
				<td>&nbsp;</td>
				<td><c:out value="${dataBaseConfig.db_user}"></c:out></td>
			</tr>
			<tr>
				<td>Password</td>
				<td>&nbsp;</td>
				<td><c:out value="${dataBaseConfig.db_password}"></c:out></td>
			</tr>
			<tr>
				<td>Valid?</td>
				<td>&nbsp;</td>
				<td><c:out value="${dataBaseConfig.hasValidData}"></c:out></td>
			</tr>
		</table>
	</fieldset>
</body>
</html>