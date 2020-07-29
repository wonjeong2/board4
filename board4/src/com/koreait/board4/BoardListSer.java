package com.koreait.board4;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.model.BoardDAO;
import com.koreait.board4.vo.BoardListModel;
import com.koreait.board4.vo.UserVO;


@WebServlet("/boardList")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private int recordCnt = 5;  //페이징할때 레코드 수 정하는 것
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession hs = request.getSession();
		UserVO loginuser = (UserVO) hs.getAttribute("loginuser");
		
		if (hs.getAttribute("loginuser") == null) {
			response.sendRedirect("/login");
			return;		
		} 
			
		String strPage = request.getParameter("page");
		int page = 1;
		
		if (strPage != null) {   //page값이 안넘어왔다는건 page=1이라는 뜻
			page = Integer.parseInt(strPage);
			
		}
		
		request.setAttribute("totalPageCnt", BoardDAO.selectTotalPageCnt(recordCnt));  //레코드 수를 받아서 페이징의 페이징을 하는것
		
		int endIdx = page * recordCnt;
		int startIdx = endIdx - recordCnt;
		
		
		BoardListModel bm =new BoardListModel();
		int i_user = loginuser.getI_user();
		bm.setI_user(i_user);
		bm.setStartIdx(startIdx);
		bm.setEndIdx(endIdx);
		
		List<BoardListModel> list = BoardDAO.boardList(bm);
		request.setAttribute("list", list);	
		
		String jsp = "/WEB-INF/jsp/boardList.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//로그아웃 기능
		
		HttpSession hs = request.getSession();
		hs.invalidate();  // 로그아웃은 이걸 쓰기 ! 
//		hs.removeAttribute("loginuser"); 부분으로 할때는 이걸로주기
//		hs.setAttribute("loginuser", null); 
		response.sendRedirect("/login");
	
//sendRedirect는 무조건 get방식
	}

}


