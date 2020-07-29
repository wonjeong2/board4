package com.koreait.board4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board4.vo.BoardCmtVO;

public class BoardCmtDAO {

	public static void insertCmt(BoardCmtVO bcv) {

		int result = 0;

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO t_board3_cmt (i_cmt, i_board, i_user, cmt) VALUES (seq_cmt.nextval, ?, ?, ?)";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bcv.getI_board());
			ps.setInt(2, bcv.getI_user());
			ps.setNString(3, bcv.getCmt());
			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps);
		}

	}

	public static List<BoardCmtVO> selectBoardCmtList(int i_board) {

		List<BoardCmtVO> list = new ArrayList<BoardCmtVO>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT A.i_cmt, A.i_user, A.cmt, TO_CHAR(A.r_dt, 'YYYY/MM/DD HH:mm') AS r_dt, B.nm AS writerNm "
				+ "FROM t_board3_cmt A " + "INNER JOIN t_user3 B " + "ON A.i_user = B.i_user "
				+ "WHERE A.i_board = ? ORDER BY i_cmt ASC";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, i_board);
			rs = ps.executeQuery();

			while (rs.next()) {

				BoardCmtVO param = new BoardCmtVO();

				param.setI_cmt(rs.getInt("i_cmt"));
				param.setI_user(rs.getInt("i_user"));
				param.setCmt(rs.getString("cmt"));
				param.setR_dt(rs.getNString("r_dt"));
				param.setWriterNm(rs.getNString("writerNm"));

				list.add(param);

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			DbCon.close(con, ps, rs);
		}

		return list;

	}

	public static void delBoardCmt(BoardCmtVO param) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "DELETE t_board3_cmt WHERE i_cmt = ? AND i_user = ?";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_cmt());
			ps.setInt(2, param.getI_user());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}

	}

	public static void updBoardCmt(BoardCmtVO param) {
		
		
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE t_board3_cmt SET cmt = ? WHERE i_cmt = ? ";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, param.getCmt());
			ps.setInt(2, param.getI_cmt());
			ps.executeUpdate();
			
			
			

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps);
		}

	}

}
