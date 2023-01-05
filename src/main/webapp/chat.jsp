<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.example.demo.UserDetails"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-color: white;">

	<!--  ArrayList<Alien> al = (ArrayList<Alien>) request.getAttribute("objlist"); -->
	<!-- <form id="o">
		<input type="text" id="a">align="right"
	</form>
	height: 50px; width: 800px;
	<input type="button" onclick="hy()"> -->

	<form>
		<input type="submit" formaction="/logout" value="LOGOUT"
			style="position: absolute; top: 2px; left: 1200px;">
	</form>

	<form id="selectuserform">
		<input type="hidden" id="selectreceiverid" name="selectreceivername">
	</form>

	<form id="searchpersonform">
		<input type="text" id="serachperson" name="personmail"
			onkeyup="searchPerson()"
			style="position: absolute; top: 60px; left: 2px; height: 20px; width: 320px; border-radius: 20px; text-align: center; z-index: 5;">
	</form>
	<div id="sidebar"
		style="background-color: white; position: absolute; top: 90px; left: 2px; height: 650px; width: 330px; overflow: auto; z-index: 3;">

	</div>
	<div id="heading"
		style="background-color: skyblue; position: absolute; top: 2px; left: 332px; height: 50px; width: 1000px; z-index: 1">

		<p
			style="font-size: 20px; position: absolute; top: 2px; left: 850px; z-index: 2">
			<I><B><%=session.getAttribute("fullName")%></B></I>
		</p>

		<img src="menu1.png" height="50px" width="70px"
			style="position: absolute; top: 0px; left: 940px;"
			onclick="generateOPtion()"></img>
	</div>
	<div id="personname" align="center"
		style="background-color: skyblue; position: absolute; top: 61px; left: 400px; height: 50px; width: 800px;"></div>
	<div id="y"
		style="background-color: blue; position: absolute; top: 111px; left: 400px; height: 450px; width: 800px; overflow-y: auto; overflow-x: hidden;">

	</div>
	<form id="messageform">
		<textarea rows="4" cols="111" id="messagearea" name="messageareaname"
			style="position: absolute; top: 562px; left: 400px;"></textarea>
		<input type="hidden" id="recieverid" name="recievername"> <input
			type="button" style="position: absolute; top: 626px; left: 1147px;"
			value="SEND" onclick="sendMessage()">
	</form>
	<div id="fileUpload"
		style="position: absolute; top: 562px; left: 1200px;">
		<form id="fileuploadform">

			<!--  <label for="file-input"> <img src="https://goo.gl/pB9rpQ"
				style="width: 80px; height: 70px; cursor: pointer; background-color: skyblue;"><img>
			</label> -->
			<input id="file" type="file" accept="image/*,audio/*,video/*"
				name="files" multiple /> <input type="hidden" id="personfileId"
				name="personfileId"><input type="button" value="upload"
				onclick="uploadFile()">
		</form>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	<script type="text/javascript">
		
		var delayInMilliseconds = 300;
		var fetchitdetection = 0;
		var fetchitselfdetection = 0;
		var scrolldown = 0;
		var checkscrollbar=0;
		function sendMessage() {
			checkscrollbar=0;
			if ($("#messagearea").val().length == 0)
				return;
			if ($("#recieverid").val().length == 0)
				return;
			$.ajax({

				url : '/sendMessage',
				data : $("#messageform").serialize(),
				method : 'post',
				error : function() {
					alert("csfhgdhb");
					document.getElementById("messagearea").value = "";
				},
				success : function(msg) {
					//alert(msg);
					document.getElementById("messagearea").value = "";
					scrolldown = 1;
					

					
					

				}

			});
		}

		function person(msg1) {
			//alert(msg1);
			//$("#serachperson").value="";
			//document.getElementById("sidebar").innerHTML="";
			fetchitdetection = 0;
			document.getElementById("recieverid").value = msg1;
			document.getElementById("selectreceiverid").value = msg1;
			document.getElementById("personname").innerHTML = document
					.getElementById(msg1 + "").innerHTML;
			document.getElementById("y").innerHTML = "";
			document.getElementById("serachperson").value = "";
			document.getElementById("sidebar").innerHTML = "";
			document.getElementById("personfileId").value = msg1;
			scrollbarsetdectection = 0;

			//alert(document.getElementById("selectreceiverid").value);
			$
					.ajax({

						url : '/fetchallmessage',
						data : $("#selectuserform").serialize(),
						method : 'post',
						error : function() {
							alert("csfhgdhb");
						},
						success : function(msg) {
							var sender = msg[0];
							//alert(sender);
							//alert(msg[1]);
							for (var i = 1; i < msg.length; i++) {
								if (msg[i].split(",")[0] == sender) {
									document.getElementById("y").innerHTML += "<p style='background-color:orange;position:relative;top:10px;left:495px;width:300px;overflow-x:auto;'>"
											+ msg[i].substring(msg[i]
													.indexOf(',') + 1,
													msg[i].length) + "</p>";
								} else {
									document.getElementById("y").innerHTML += "<p style='background-color:grey;position:relative;top:10px;left:5px; width:300px;overflow-x:auto;'>"
											+ msg[i].substring(msg[i]
													.indexOf(',') + 1,
													msg[i].length) + "</p>";
								}

							}
							console
									.log("Height:"
											+ document.getElementById("y").scrollHeight);
							//$('#y').scrollTop($('#y')[0].scrollHeight);
							setTimeout(
									function() {
										document.getElementById("y").scrollTop = document
												.getElementById("y").scrollHeight - 450;
										
									}, 2000);
							setTimeout(
									function() {
										fetchitdetection = 1;
										fetchitselfdetection = 1;
										scrolldown = 1;
										checkscrollbar=1;
										
									}, 4000);
							
							
							//console.log("Height:"
								//	+ document.getElementById("y").scrollTop);
							//document.getElementById("y").scrollTop=document.getElementById("y").scrollHeight-450;
							//document.getElementById("y").scrollTop = document
							//.getElementById("y").scrollHeight;
							//console.log(document.getElementById("y").scrollTop);
							//console
									//.log(document.getElementById("y").scrollHeight);
							//$('#y').scrollTop($('#y').prop("scrollHeight"));
							

							//window.scrollTo(0,document.querySelector("#y").scrollHeight);

						}

					});
			//alert(msg1);
		}

		window
				.setInterval(
						function fetchallmessageIteration() {
							//document.getElementById("recieverid").value=msg1;
							//document.getElementById("selectreceiverid").value=msg1;
							//document.getElementById("y").innerHTML="";
							if (document.getElementById("selectreceiverid").value.length != 0
									&& fetchitdetection != 0
									&& fetchitselfdetection != 0) {
								fetchitselfdetection = 0;
								setsrollbarcheck = 0;
								$
										.ajax({

											url : '/fetchallmessageIteration',
											data : $("#selectuserform")
													.serialize(),
											method : 'post',
											error : function() {
												alert("csfhgdhb");
											},
											success : function(msg) {
												var sender = msg[0];

												for (var i = 1; i < msg.length; i++) {
													if (msg[i].split(",")[0] == sender) {
														document
																.getElementById("y").innerHTML += "<p style='background-color:orange;position:relative;top:10px;left:495px;width:300px;overflow-x:auto;'>"
																+ msg[i]
																		.substring(
																				msg[i]
																						.indexOf(',') + 1,
																				msg[i].length)
																+ "</p>";
													} else {
														document
																.getElementById("y").innerHTML += "<p style='background-color:grey;position:relative;top:10px;left:5px; width:300px;overflow-x:auto;'>"
																+ msg[i]
																		.substring(
																				msg[i]
																						.indexOf(',') + 1,
																				msg[i].length)
																+ "</p>";
													}

												}
												setTimeout(
														function() {
															if (scrolldown != 0 && msg.length!=1) {
																document
																		.getElementById("y").scrollTop = document
																		.getElementById("y").scrollHeight - 450;
																//console.log(msg.length);
															}
														}, 400);
												setTimeout(
														function() {
															fetchitselfdetection = 1;
															setsrollbarcheck = 1;
															checkscrollbar=1;
															
														}, 800);

												/*console
														.log(document
																.getElementById("y").scrollTop);
												console
														.log(document
																.getElementById("y").scrollHeight);*/
												//fetchitselfdetection = 1;
												//setsrollbarcheck = 1;
												//checkscrollbar=1;
											}

										});
							}
							//alert(msg1);
						}, delayInMilliseconds);

		var noofClick = 0;
		function generateOPtion() {
			if (noofClick % 2 == 0) {
				document.body.innerHTML += "<div id='option' style='position:absolute;top:54px;left:1240px;width:102px;height:40px;background-color:skyblue'><a href='/logout'>LOGOUT</a></div>";
				noofClick++;
			} else {
				var element = document.getElementById("option");
				element.parentNode.removeChild(element);
				noofClick++;
			}
		}
		function searchPerson() {
			
			if ($("#serachperson").val().length == 0) {
				document.getElementById("sidebar").innerHTML = "";
				return;
			}

			document.getElementById("sidebar").innerHTML = "";
			$
					.ajax({

						url : '/searchPerson',
						data : $("#searchpersonform").serialize(),
						method : 'post',
						error : function() {
							alert("csfhgdhb");
							document.getElementById("messagearea").value = "";
						},
						success : function(msg) {
							//alert(msg);
							//document.getElementById("messagearea").value = "";

							//document.getElementById("sidebar")
							document.getElementById("sidebar").innerHTML = "";
							for (var i = 0; i < msg.length; i++)

							{
								document.getElementById("sidebar").innerHTML += "<p id="
										+ msg[i].split(",")[0]
										+ " style='background-color:skyblue;position:relative;top:2px;left:2px;width:330px;' onclick=person("
										+ msg[i].split(",")[0]
										+ ")>"
										+ msg[i].split(",")[2]
										+ "<br>"
										+ msg[i].split(",")[1] + "</p>";
							}
							//alert(document.getElementById(1+"").innerHTML);
						}

					});

		}
		function uploadFile() {

			checkscrollbar=0;
			var form = $('#fileuploadform')[0];
			var data = new FormData(form);
			var jsonDataObj = {
				"personfileId" : $("#personfileId").val()
			};
			data.append("jsondata", JSON.stringify(jsonDataObj));
			document.getElementById("file").value = "";

			$.ajax({

				type : "POST",
				enctype : 'multipart/form-data',
				url : "/fileupload",
				data : data,
				processData : false,
				contentType : false,
				cache : false,
				timeout : 600000,
				error : function() {
					alert("csfhgdhb");
				},
				success : function(msg) {
					//alert(msg);
					scrolldown = 1;
					


				}

			});
		}

		window.setInterval(function setScrollbar() {
			if(checkscrollbar==0)
				return ;

			if ((document.getElementById("y").scrollHeight - document
					.getElementById("y").scrollTop) != 450) {
				scrolldown = 0;
				//console.log("palash");
			} else
				scrolldown = 1;

		}, 6000);
	</script>
</body>
</html>