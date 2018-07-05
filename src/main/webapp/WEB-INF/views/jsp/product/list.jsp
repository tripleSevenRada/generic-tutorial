<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>etnShop</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

	<div class="w3-container">
		<h2>Products</h2>
	</div>
	
	<br>
	<div class="w3-container">
		<a style="width: 180px" class="w3-btn w3-padding-small w3-blue" href="/etnshop/product/add_form" role="button">Add product</a>
	</div>

	<br>
	<div class="w3-container">
		<div style="height: 340px; overflow: auto" class="w3-container">
			<table class="w3-table w3-hoverable w3-bordered">

				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Serial number</th>
						<th>Update</th>
						<th>Delete</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${products}" var="product">
					
						<c:url var = "updateLink" value = "/product/update_form">
							<c:param name = "productId" value = "${product.id}"/>
						</c:url>
						
						<c:url var = "deleteLink" value = "/product/delete_product">
							<c:param name = "productId" value = "${product.id}"/>
						</c:url>
									
						<tr>
							<td>${product.id}</td>
							<td>${product.name}</td>
							<spring:eval expression="product.getSerialISSN()" var="calculatedISSN" />
							<td>${calculatedISSN}</td>
							<td><a class="w3-text-blue" href = "${updateLink}">Update</a></td>
							<td><a class="w3-text-red" href = "${deleteLink}"
							onclick="if (!(confirm('Are you sure?'))) return false">Delete</a></td>
						</tr>
						
					</c:forEach>
				</tbody>

			</table>
		</div>
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
