<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인화면</title>
</head>
<body>
	<div>${msg}</div>
	<form action = "/login" method = "post">
		<div><input type = "text" name = "cid" placeholder="id" value="${data.cid}"></div>
		<div><input type = "password" name = "cpw" placeholder="password" value="${data.cpw}"></div>
		<div><input type = "submit" value="로그인"></div>		
	</form>
	<div><a href="/join"><button>회원가입</button></a></div>
</body>
</html>
