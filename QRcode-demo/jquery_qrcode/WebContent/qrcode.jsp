<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>


</head>
<body>
	<button onclick="createCode();">生成二维码(github_link)</button>
	 <br/><br/><br/><hr/>生成的二维码如下：<br/><br/>

	<div id="qrcode"></div>
	
	<script type="text/javascript">
		function createCode(){
			jQuery('#qrcode').qrcode("https://github.com/Jimmy1995");
		}
		
	</script>
</body>
</html>