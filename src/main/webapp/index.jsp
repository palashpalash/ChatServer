<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="heading" align="center"
		style="background-color: red; position: absolute; top: 2px; left: 332px; height: 50px; width: 1000px; z-index: 1">
		<form id="Login" action="LogIn" method="post"
			onsubmit=" return logInformSubmit();">
			<input type="text" id="username" name="username"
				placeholder="EmailId"
				style="height: 20px; width: 320px; border-radius: 20px; text-align: center; z-index: 5;">
			<input type="password" id="password" name="password"
				placeholder="Password"
				style="height: 20px; width: 320px; border-radius: 20px; text-align: center; z-index: 5;">
			<input type="submit" value="LOGIN">
		</form>

	</div>

	<div id="registration"
		style="background-color: purple; position: absolute; top: 150px; left: 500px; height: 500px; width: 700px; z-index: 1"></div>
		<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
	<script language="javascript">
		function logInformSubmit()
		{
			//alert("Hi");
			if ($("#username").val().length == 0)
				return false;
			if ($("#password").val().length == 0)
				return false;
			
		}
		
		</script>
</body>
</html>
