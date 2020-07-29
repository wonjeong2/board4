package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.model.BoardDAO;
import com.koreait.board4.model.BoardLikeDAO;
import com.koreait.board4.vo.BoardVO;
import com.koreait.board4.vo.UserVO;


@WebServlet("/boardDel")
public class BoardDelSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession hs = request.getSession();
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");
		
		if (loginuser == null) {
			response.sendRedirect("/login");
			return;
		}
		
		BoardVO param = new BoardVO();
		String i_board_str = request.getParameter("i_board");
		
		int i_board = Integer.parseInt(i_board_str);
		
		param.setI_board(i_board);
		param.setI_user(loginuser.getI_user());
		
		int result = BoardLikeDAO.boardLikeDel(param);
		
		if (result == 0) {
			
			String url = String.format("/boardDetail?i_board=%d&err=1", i_board);
			response.sendRedirect(url);
			return;
		} 
		
		response.sendRedirect("/boardList");
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}

}
