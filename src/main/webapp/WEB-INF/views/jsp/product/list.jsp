<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>etnShop</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

	<div class="w3-container">
		<h2>Products</h2>
	</div>

	<div class="w3-container">
		<div style="height: 240px; overflow: auto" class="w3-container">
			<table class="w3-table w3-hoverable w3-bordered">

				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Serial number</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${products}" var="product">
						<tr>
							<td>${product.id}</td>
							<td>${product.name}</td>
							<spring:eval expression="product.getSerialISSN()" var="calculatedISSN" />
							<td>${calculatedISSN}</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>

	<br>
	<div class="w3-container">
	
		<!-- validation using Spring validator pattern -->
		
		<form:form action="add_product" modelAttribute="intoFormProduct">
			&nbsp;Name:&nbsp;<form:input style="width: 180px" path = "name"/>
							<form:errors path="name" cssClass="w3-red"/>
			&nbsp;Serial number 1:&nbsp;<form:input style="width: 160px" path ="serial1"/>
							<form:errors path="serial1" cssClass="w3-red"/>
			&nbsp;Serial number 2:&nbsp;<form:input style="width: 160px" path="serial2"/>
							<form:errors path="serial2" cssClass="w3-red"/>
			<input	style="width: 180px" class="w3-btn w3-padding-small w3-green" type="submit" name="submitAdd" value="Add product" />
		</form:form>
	</div>
	<br>
	<div class="w3-container">
		<form action="edit_product" method="GET">
			<input style="width: 100px" type="number" min="1" max="100000000" step="1" name="id" placeholder="ID" required />
			<input style="width: 180px" type="text" name="name" placeholder="Name" required />
			<input style="width: 160px" type="number" min="1" max="100000000" step="1" name="serial1" placeholder="Serial number 1" required />
			<input style="width: 160px" type="number" min="1" max="100000000" step="1" name="serial2" placeholder="Serial number 2" required />
			<input style="width: 180px" class="w3-btn w3-padding-small w3-orange" type="submit" name="submitEdit" value="Edit product" />
		</form>
	</div>
	<br>
	<div class="w3-container">
		<form action="remove_product" method="GET">
			<input style="width: 100px" type="number" min="1" max="100000000" step="1" name="idRemove" placeholder="ID" required />
			<input style="width: 180px" class="w3-btn w3-padding-small w3-red" type="submit" name="submitRemove" value="Remove product" />
		</form>
	</div>
	<br>
	<div class="w3-container">
		<a style="width: 180px" class="w3-btn w3-padding-small w3-blue" href="/etnshop" role="button">Back to homepage</a>
	</div>

	<div class="w3-container w3-bottom">
		<footer> &copy; Etnetera a.s. 2015 </footer>
	</div>

</body>
</html>
