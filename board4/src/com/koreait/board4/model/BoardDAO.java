package com.koreait.board4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board4.vo.BoardListModel;
import com.koreait.board4.vo.BoardVO;

public class BoardDAO {
	
//			page = 1
//			boardCnt = 5 (게시물수)
//
//			나 : page * boardCnt
//			가 : (나 - boardCnt)
//
//			가:((page - 1) * boardCnt)
//			나 : page * boardCnt
//
//			* 오라클 페이징 쿼리문
//			SELECT * 
//			FROM ( 
//			    SELECT 
//			        ROWNUM AS RNUM, Z.* 
//			        FROM ( 
//			        SELECT * FROM t_board3 ORDER BY i_board DESC     //이 부분에 리스트 뿌리던 쿼리문 들어감!!!!!!!!!!
//			        ) Z WHERE ROWNUM <= 10  -> 나
//			        ) WHERE RNUM > 5;  -> 가


	public static List<BoardListModel> boardList(BoardListModel param) {

		List<BoardListModel> list = new ArrayList<BoardListModel>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM (  "
				+ " SELECT ROWNUM AS RNUM, Z.* "  //글 레코드의 순서 부여
				+ " FROM ( ";

		sql += "SELECT A.i_board, A.title, to_char(A.r_dt, 'YY/MM/DD hh:mm') as r_dt, B.i_user, B.nm, A.cnt , nvl(C.i_user, 0) as likeUser FROM t_board3 A INNER JOIN t_user3 B "
				+ "on A.i_user = B.i_user left join t_board3_like C on A.i_board = C.i_board and C.i_user = ? order by i_board desc";
		
		sql += ") Z WHERE ROWNUM <= ? " //레코드 순서를 처음부터 여기까지 적어주겠다!!!
				+ " ) WHERE RNUM > ? ";  //나타낼 레코드의 시작점
		
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_user());
			ps.setInt(2, param.getEndIdx());
			ps.setInt(3, param.getStartIdx());
			
			rs = ps.executeQuery();

			while (rs.next()) {

				int i_board = rs.getInt("i_board");
				String title = rs.getNString("title");
				String r_dt = rs.getNString("r_dt");
				int i_user = rs.getInt("i_user");
				String nm = rs.getNString("nm");
				int cnt = rs.getInt("cnt");
				int likeUser = rs.getInt("likeUser");
				

				BoardListModel bm = new BoardListModel();

				bm.setI_board(i_board);
				bm.setTitle(title);
				bm.setR_dt(r_dt);
				bm.setI_user(i_user);
				bm.setUserNm(nm);
				bm.setCnt(cnt);
				bm.setLikeUser(likeUser);

				list.add(bm);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps, rs);
		}

		return list;

	}

	public static BoardListModel baordSelect(BoardListModel param) {

		BoardListModel bm = new BoardListModel();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = " SELECT A.i_board, A.title, A.r_dt, A.ctnt, A.cnt, B.i_user, B.nm as userNm "
				+ " , nvl(C.i_user, 0) as likeUser " + " FROM t_board3 A INNER JOIN t_user3 B  ON A.i_user = B.i_user "
				+ " LEFT JOIN t_board3_like C ON A.i_board = C.i_board AND C.i_user = ? " + " WHERE A.i_board = ? ";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_user());
			ps.setInt(2, param.getI_board());
			rs = ps.executeQuery();

			while (rs.next()) {

				bm.setI_board(rs.getInt("i_board"));
				bm.setTitle(rs.getNString("title"));
				bm.setCtnt(rs.getString("ctnt"));
				bm.setR_dt(rs.getNString("r_dt"));
				bm.setUserNm(rs.getNString("userNm"));
				bm.setI_user(rs.getInt("i_user"));
				bm.setLikeUser(rs.getInt("likeUser"));
				bm.setCnt(rs.getInt("cnt"));

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps, rs);
		}

		return bm;

	}
	
	public static int selectTotalPageCnt(int recordCnt) {
		
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT CEIL(COUNT(i_board) / ?) FROM t_board3";
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, recordCnt);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps, rs);
		}
		return result;
	}

//	public static void boardUpdate() {
//		
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
//		String sql = "insert into t_board3 (i_board, title, ctnt, r_dt, i_user, cnt) values (seq_user.nextval, ?, ?, sysdate, ?, ?)";
//		
//	}
	
	
	
	public static void updCntAdd(int i_board) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update t_board3 set cnt = cnt + 1 where i_board = ?";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, i_board);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps);
		}
	}

	public static int boardReg(BoardVO param) {
		
		int i_board = 0;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "INSERT INTO t_board3 (i_board, title, ctnt, i_user) VALUES (seq_board3.nextval, ?, ?, ?)";
		
		String cols[] = {"i_board"};
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql, cols);
			ps.setNString(1, param.getTitle());
			ps.setNString(2, param.getCtnt());
			ps.setInt(3, param.getI_user());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			
			while (rs.next()) {
				i_board = rs.getInt(1);
				
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			DbCon.close(con, ps, rs);
		}
		
		
		return i_board;
		
	}
	
	public static void boardMod(BoardVO param) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "UPDATE t_board3 SET title = ?, ctnt = ?, m_dt = SYSDATE WHERE i_board = ? AND i_user = ?"; 
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);			
			ps.setNString(1, param.getTitle());
			ps.setNString(2, param.getCtnt());
			ps.setInt(3, param.getI_board());
			ps.setInt(4, param.getI_user());
			ps.executeUpdate();
			
		} catch (Exception e) {
		
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
			
		
	}
	


	
	



}
