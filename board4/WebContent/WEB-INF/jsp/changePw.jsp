<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
</head>
<body>
	<div>${msg}</div>
	<div>
		<form id="frm" action="/myPage?typ=${param.typ}" method="post" onsubmit="return chk()">  <!-- {param.""}는 쿼리스트링에 있는 값을 가져오는것 -->
			<div><input type="password" name="currentPw" placeholder="현재 비밀번호"></div>
			<div><input type="password" name="changePw" placeholder="새로운 비밀번호"></div>
			<div><input type="password" name="changeCpwConfirm" placeholder="비밀번호 확인"></div>
			<div><input type="submit" value="변경"></div>
		</form>
	</div>
	
	<script>
		function chk() {
			
			if(frm.changePw.value == '') {
				alert('비밀번호를 입력해주세요.')
				frm.changePw.focus()
			return false
			
			} else if(frm.changePw.value == '') {
				alert('새로운 비밀번호를 입력해주세요.')
				frm.changePw.focus()
			return false
			
			} else if(frm.changePw.value != frm.changeCpwConfirm.value) {
				alert('비밀번호를 확인해주세요.')
				frm.changeCpwConfirm.focus()
			return false				
			} 
			
		}
	
	</script>
</body>
</html>