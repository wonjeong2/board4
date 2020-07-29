package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.model.BoardCmtDAO;
import com.koreait.board4.model.BoardDAO;
import com.koreait.board4.vo.BoardListModel;
import com.koreait.board4.vo.UserVO;

@WebServlet("/boardDetail") // 디테일화면으로 갈지, 수정화면으로 갈지 정하는곳!
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		int i_board = Integer.parseInt(request.getParameter("i_board"));  //boardList.jsp에서 넘어온 값

		HttpSession hs = request.getSession();
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");

		if (hs.getAttribute("loginuser") == null) {
			response.sendRedirect("/login");
			return;
		}
		BoardDAO.updCntAdd(i_board); // 조회수

		BoardListModel param = new BoardListModel();
		
		param.setI_board(i_board);
		param.setI_user(loginuser.getI_user());

		request.setAttribute("data", BoardDAO.baordSelect(param)); // 디테일 뿌려주기!!!!!
		request.setAttribute("cmtList", BoardCmtDAO.selectBoardCmtList(i_board)); // 댓글 리스트에 담기
		
		String jsp = "/WEB-INF/jsp/boardDetail.jsp";
		
		
		// 디테일 jsp에서 넘어오는것
		String typ = request.getParameter("typ"); // typ에 null이 넘어온거는 디테일 화면으로 가라!!
		String err = request.getParameter("err");

		if (err != null) {
			String msg = "";
			switch (err) {
			case "1":
				msg = "삭제 실패";
				break;
			case "2":
				msg = "수정 실패";
				break;
			}
			request.setAttribute("msg", msg);
		}

		if ("mod".equals(typ)) { // 디테일 화면에서 수정버튼 눌렀을때
			jsp = "/WEB-INF/jsp/boardRegmod.jsp";
		}

		request.getRequestDispatcher(jsp).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
