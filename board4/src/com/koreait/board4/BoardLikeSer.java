package com.koreait.board4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.model.BoardLikeDAO;
import com.koreait.board4.vo.BoardLikeVO;
import com.koreait.board4.vo.UserVO;


@WebServlet("/boardLike")
public class BoardLikeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession hs = request.getSession();
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");
		
		String i_board_str = request.getParameter("i_board");
		String isLike_str = request.getParameter("isLike");  //기존에 좋아요 했었는지 안했었는지 구분용 !!
		

		
		int i_board = Integer.parseInt(i_board_str);
		int isLike = Integer.parseInt(isLike_str);
		
		BoardLikeVO param = new BoardLikeVO();
		param.setI_board(i_board);
		param.setI_user(loginuser.getI_user());
		
		int result = 0;
		
		if(isLike == 0) { //좋아요
			result = BoardLikeDAO.enableLike(param);
	
			
		} else { //좋아요 취소
		
			result = BoardLikeDAO.disableLike(param);
		}
		
		System.out.println("result : " + result);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String res = String.format("{\"result\":%d}", result);
		out.print(res);
		out.flush();
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}

}
