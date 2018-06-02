<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>etnShop</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

	<div class="w3-container">
		<h2>Products stats</h2>
	</div>

	<div class="w3-container">
		Number: ${products_stats_size} <br> Max. name length:
		${products_stats_name_max_length} <br> Min. name length:
		${products_stats_name_min_length}
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
