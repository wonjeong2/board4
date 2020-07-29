package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.model.UserDAO;
import com.koreait.board4.vo.UserVO;


@WebServlet("/login")
public class BoardLoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/login - get");
		HttpSession hs = request.getSession();  //자동로그인 해주는 부분
//		UserVO loginuser = new UserVO();
//		loginuser.setI_user(41);
//		loginuser.setCid("1234");
//		loginuser.setNm("최원정");
//		hs.setAttribute("loginuser", loginuser);
		
		if(hs.getAttribute("loginuser") != null) {
			response.sendRedirect("/boardList");
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/login - post");
		
		UserVO param = new UserVO();
		
		HttpSession hs = request.getSession();
		
		
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
				
		param.setCid(cid);
		param.setCpw(cpw);
		
		int result = UserDAO.login(param);
		System.out.println("result :" + result);
		
		
		if (result == 1) {
			
			param.setCpw(null);
			hs.setAttribute("loginuser", param);
			response.sendRedirect("/boardList");	
			return;
		}
		
		String msg = "에러발생";
		
		switch (result) {
			case 2 :
				msg = "비밀번호를 다시 입력해주세요.";
				break;
			case 3: 
				msg = "아이디를 다시 입력해주세요.";
				break;
		}
				
		request.setAttribute("msg", msg);
		request.setAttribute("data", param);
		
		doGet(request, response);
								
		}
	
	}


