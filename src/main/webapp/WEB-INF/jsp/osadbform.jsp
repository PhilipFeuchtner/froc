<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Cosa implementing Froc</title>
 <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>"/>

</head>
<body>

	<h2>Osa DB</h2>

	<form:form modelAttribute="uploadItem" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>Upload Fields</legend>

			<p>
				<form:label for="osa" path="osaList">Osa</form:label>
				<br />
				<form:select path="osaList" multiple="false">
					<form:option value="Select"></form:option>
					<form:options items="${uploadItem.osaList}" />
				</form:select>

			</p>

			<p>
				<form:label for="pagesid" path="pagesid">Pages Id</form:label>
				<br />
				<form:input path="pagesid"/>
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
