package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.model.BoardDAO;
import com.koreait.board4.vo.BoardVO;
import com.koreait.board4.vo.UserVO;

@WebServlet("/boardReg")
public class BoardRegSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// jsp 파일은 등록, 수정 똑같은 파일을 쓴다.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/jsp/boardRegmod.jsp").forward(request, response);
		// 글쓰기 버튼 누르면 boardRegmod.jsp 이 화면을 띄운다.

	}

	// reg, mod 같이 쓸거임!!!
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession hs = request.getSession();
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");

		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		
		String i_board_str = request.getParameter("i_board");
				System.out.println("i_board : "+i_board_str);
		BoardVO param = new BoardVO();
		param.setTitle(title);
		param.setCtnt(ctnt);
		param.setI_user(loginuser.getI_user());

		
		if ("".equals(i_board_str) || i_board_str == null) {  //등록	
			int result = BoardDAO.boardReg(param);
			response.sendRedirect("/boardDetail?i_board=" + result);
		
		} else {   //수정
			int i_board = Integer.parseInt(i_board_str);
			param.setI_board(i_board);
			
			BoardDAO.boardMod(param);
			request.setAttribute("data", param);
			response.sendRedirect("/boardDetail?i_board=" + i_board);
		}
	}

}
