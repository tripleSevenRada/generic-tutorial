<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>etnShop</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

	<div class="w3-container">
		<h2>Add product</h2>
	</div>

	<br>
	<div class="w3-container">
	
		<!-- validation using Spring validator pattern -->
		
		<form:form action="add_product" modelAttribute="intoFormProduct" method = "POST">
			<!-- need to associate this data with customer id -->
			<form:hidden path="id" />
			<table>
				<tr>
					<td>&nbsp;Name:&nbsp;</td><td><form:input style="width: 200px" path = "name"/>
							<form:errors path="name" cssClass="w3-red"/></td>
				</tr>
				<tr>			
					<td>&nbsp;Serial number 1:&nbsp;</td><td><form:input style="width: 200px" path ="serial1"/>
							<form:errors path="serial1" cssClass="w3-red"/></td>
				</tr>
				<tr>
					<td>&nbsp;Serial number 2:&nbsp;</td><td><form:input style="width: 200px" path="serial2"/>
							<form:errors path="serial2" cssClass="w3-red"/></td>
				</tr>
			</table>
			<br>
			<input	style="width: 180px" class="w3-btn w3-padding-small w3-green" type="submit" name="submitAdd" value="Submit product" />
		</form:form>
	</div>
	
	<br>
	<div class="w3-container">
		<a style="width: 180px" class="w3-btn w3-padding-small w3-blue" href="/etnshop/product/list" role="button">Back to list</a>
	</div>
	
	<br>
	<div class="w3-container">
		<a style="width: 180px" class="w3-btn w3-padding-small w3-blue" href="/etnshop" role="button">Back to homepage</a>
	</div>

</body>
</html>
