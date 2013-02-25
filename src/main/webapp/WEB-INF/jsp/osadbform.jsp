<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Cosa implementing Froc</title>
<style type="text/css">
body {
	font-family: sans-serif;
}

.data,.data td {
	border-collapse: collapse;
	width: 100%;
	border: 1px solid #aaa;
	margin: 2px;
	padding: 2px;
}

.data th {
	font-weight: bold;
	background-color: #5C82FF;
	color: white;
}
</style>
</head>
<body>

	<h2>Osa DB</h2>

	<form:form modelAttribute="uploadItem" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>Upload Fields</legend>

			<p>
				<form:label for="name" path="name">Abschnitt</form:label>
				<br />
				<form:input path="name" />
			</p>

			<p>
				<form:label for="fileData" path="fileData">Qti-File</form:label>
				<br />
				<form:input path="fileData" type="file" />
			</p>

			<p>
				<input type="submit" />
			</p>

		</fieldset>
	</form:form>

</body>
</html>
