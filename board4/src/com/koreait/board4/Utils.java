package com.koreait.board4;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.koreait.board4.vo.UserVO;

public class Utils {
	public static UserVO getLoginUser(HttpServletRequest request) {
		
		HttpSession hs = request.getSession();
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");
		
		return loginuser;
	}
	
	public static void logout(HttpServletRequest request) {
		
		HttpSession hs = request.getSession();
		hs.invalidate();
		
	}
}
