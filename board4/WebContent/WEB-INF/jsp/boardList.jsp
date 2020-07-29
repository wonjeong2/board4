<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트화면</title>
<style>
.myCtnt {
	color: #1abc9c;
}

.boardItem:hover {
	background-color: #bdc3c7;
	cursor: pointer;
}

#pagingContainer {
	display:flex;
	width:100%;
	height:30px;
	justify-content:center;
	align-items:center;

}
</style>
</head>
<body>
	<div>${loginuser.nm}님환영합니다!</div>
	<div>
		<a href="/boardReg">
			<button>글쓰기</button>
		</a>
	</div>
		<div>
	<form action="/boardList" method="post">
			<input type="submit" value="로그아웃">			
	</form>
		</div>
		<a href="/myPage?typ=1"><button>비밀번호 수정</button></a>
	<div>
		<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>좋아요</th>
			</tr>

			<c:forEach var="item" items="${list}">
				<tr class="boardItem" onclick="moveToDetail(${item.i_board})">
		
					<td class="${item.i_user == loginuser.i_user ? 'myCtnt' : ''}">${item.i_board}</td>
					<td>${item.title}</td>
					<td>${item.r_dt}</td>
					<td>${item.userNm}</td>
					<td>${item.cnt}</td>
					<td>
					<c:choose>
					 <c:when test="${item.likeUser == 0}">
					 	<div>♡</div>
					 </c:when>
					 <c:when test="${item.likeUser != 0}">
					 	<div>♥</div>
					 </c:when>						
					</c:choose>					
					</td>
				</tr>		   
			</c:forEach>
		</table>
	</div>
		<div id = "pagingContainer">
		<div>
			<c:forEach begin="1" end="${totalPageCnt}" var="i">
			<span style="margin-right:5px;"><a href="/boardList?page=${i}">${i}</a></span>
			</c:forEach>
		</div>
		</div>
		<script>
	
	
		function moveToDetail(i_board) {
			location.href = '/boardDetail?i_board=' + i_board
		}
	</script>
</body>
</html>