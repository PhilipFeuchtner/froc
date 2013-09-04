<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String hostName = request.getServerName();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Status Ok</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/style.css'/>" />

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
				<th colspan="3" align="left">Jdbc</th>
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

			<tr>
				<th colspan="3" align="left">Deleted Item</th>
			</tr>

			<tr>
				<td>Pages</td>
				<td>&nbsp;</td>
				<td><c:forEach items="${osaPage.itemDeleted.pagesList}"
						var="item">
						<c:out value="${item.id}">
						</c:out>
					</c:forEach></td>
			</tr>

			<tr>
				<td>Quests</td>
				<td>&nbsp;</td>
				<td><c:forEach items="${osaPage.itemDeleted.questsList}"
						var="item">
						<c:out value="${item}">
						</c:out>
					</c:forEach></td>
			</tr>
			<tr>
				<td>Questitems</td>
				<td>&nbsp;</td>
				<td><c:forEach items="${osaPage.itemDeleted.questitemList}"
						var="item">
						<c:out value="${item}">
						</c:out>
					</c:forEach></td>
			</tr>
			<tr>
				<th colspan="3" align="left">Added Item</th>
			</tr>

			<tr>
				<td>Pages</td>
				<td>&nbsp;</td>
				<td><c:forEach items="${osaPage.itemNew.pagesList}" var="item">
						<a href="http://<%=hostName%>/dev.php?${item.md5}"><c:out
								value="${item.id}">
							</c:out></a>
					</c:forEach></td>
			</tr>

			<tr>
				<td>Quests</td>
				<td>&nbsp;</td>
				<td><c:forEach items="${osaPage.itemNew.questsList}" var="item">
						<c:out value="${item}">
						</c:out>
					</c:forEach></td>
			</tr>
			<tr>
				<td>Questitems</td>
				<td>&nbsp;</td>
				<td><c:forEach items="${osaPage.itemNew.questitemList}"
						var="item">
						<c:out value="${item}">
						</c:out>
					</c:forEach></td>
			</tr>

			<tr>
				<th colspan="3" align="left">Errors</th>
			</tr>
			<tr>

				<c:forEach items="${osaPage.errors}" var="item">
					<td colspan="3"><c:out value="${item}">
						</c:out></td>
				</c:forEach>
			</tr>
		</table>
	</fieldset>
</body>
</html>