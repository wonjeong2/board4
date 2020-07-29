<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<div>
		<form action="/boardReg" method="post">
 			<input type="hidden" name="i_board" value="${data.i_board}"> 
			<div>제목 : <input type="text" name="title" value="${data.title}"></div>
			<div>내용 : <textarea name="ctnt">${data.ctnt}</textarea></div>
			<input type="submit" value=${data.i_board eq null ? "등록" : "수정"}>


<!--  			<c:choose>
				<c:when test = "${data.i_board eq null}">
				<div><input type="submit" value="등록"></div>
				</c:when>
				<c:when test = "${data.i_board ne null}">
				<div><input type="submit" value="수정"></div>		
				</c:when>
			</c:choose>	
			
			 -->

		</form>
	</div>
</body>
</html>