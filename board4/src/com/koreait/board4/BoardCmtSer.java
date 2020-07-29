package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.model.BoardCmtDAO;
import com.koreait.board4.vo.BoardCmtVO;
import com.koreait.board4.vo.UserVO;


@WebServlet("/boardCmt")  //화면단은 boardDetail.jsp 를 쓴다.
public class BoardCmtSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String i_board = request.getParameter("i_board");   //어느글로 돌아갈지 필요해서 받은 값
		int i_cmt = Integer.parseInt(request.getParameter("i_cmt"));  //어느 댓글을 삭제할지 필요해서 받은 값

		HttpSession hs = request.getSession();
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");
		
		if(loginuser == null) {
			response.sendRedirect("login");
		}
		
		BoardCmtVO param = new BoardCmtVO();	
		param.setI_cmt(i_cmt);
		param.setI_user(loginuser.getI_user());
		
		BoardCmtDAO.delBoardCmt(param);

		
		response.sendRedirect("/boardDetail?i_board=" + i_board);
	
	}
	
	//등록, 수정
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession hs = request.getSession();  //로그인되어있는지 아닌지 확인
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");
		
		if (loginuser == null) {
			response.sendRedirect("/login");
		}
		
		String strI_cmt = request.getParameter("i_cmt");
		String cmt = request.getParameter("cmt"); //등록, 수정
		int i_board = Integer.parseInt(request.getParameter("i_board")); //등록
				
		BoardCmtVO bcv = new BoardCmtVO();
		
		bcv.setI_board(i_board);
		bcv.setI_user(loginuser.getI_user());
		bcv.setCmt(cmt);
		
		
		if (strI_cmt.equals(null)) {	//댓글 등록	
			
			BoardCmtDAO.insertCmt(bcv);  
		
		} else {  //댓글 수정
			
			int i_cmt = Integer.parseInt(strI_cmt);  //수정
			bcv.setI_cmt(i_cmt);  //등록때는 i_cmt 값을 모르기때문에 위에 삽입해버리면 에러남!!
			BoardCmtDAO.updBoardCmt(bcv);
			
			request.setAttribute("data", bcv);
		}		
		
		
		response.sendRedirect("/boardDetail?i_board=" + i_board);
		// request.getRequestDispatcher("/boardDetail").forward(request, response);  //안되는 이유는 Dispatcher은 post에서 날리면 post로 날라간다
		
	}

}
