package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.model.UserDAO;
import com.koreait.board4.vo.UserVO;


@WebServlet("/join")
public class BoardJoinSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/join - get");
		if(request.getSession().getAttribute("loginuser") != null) {
			response.sendRedirect("/login");
			return;
		}
		
		request.getRequestDispatcher("WEB-INF/jsp/join.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/join - post");
	
		UserVO param = new UserVO();
		
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
		String nm = request.getParameter("nm");
		
		param.setCid(cid);
		param.setCpw(cpw);
		param.setNm(nm);
		
		int result = UserDAO.join(param);
		
		if (result == 1) {
			response.sendRedirect("/login");
			return;
		} else { 
			request.setAttribute("data", param);
			request.setAttribute("msg", "회원가입에 실패하였습니다.");
			doGet(request, response);
		}
		
	}

}
