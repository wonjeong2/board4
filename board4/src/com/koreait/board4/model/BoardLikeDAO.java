package com.koreait.board4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.koreait.board4.vo.BoardLikeVO;
import com.koreait.board4.vo.BoardVO;

public class BoardLikeDAO {
	
	public static int enableLike(BoardLikeVO param) {
		
		int result = 0;
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "insert into t_board3_like (i_board, i_user, r_dt) values (? , ? , sysdate)";
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());
			ps.setInt(2, param.getI_user());
			result = ps.executeUpdate();
			
		} catch (Exception e) {	
			e.printStackTrace();
		
		}	finally {
			DbCon.close(con, ps);
		}
		//System.out.println("result :" + result);
		
		return result;
		
		
	}
	
	public static int disableLike(BoardLikeVO param) {
		
		int result = 0;
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "delete t_board3_like where i_board = ? and i_user = ?";
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());
			ps.setInt(2, param.getI_user());
			result = ps.executeUpdate();
			
		} catch (Exception e) {	
			e.printStackTrace();
		
		}	finally {
			DbCon.close(con, ps);
		}
		
		return result;
	}
	
	public static int boardLikeDel(BoardVO param) {
		
		int result = 0;
		
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "DELETE t_board3_like WHERE i_board = ?";
		String sql2 = "DELETE t_board3 WHERE i_board = ? AND i_user = ? ";

		try {        //트랜잭션
			con = DbCon.getCon();
			con.setAutoCommit(false);   //오토커밋 끄기
			
			ps = con.prepareStatement(sql);  //A객체
			ps.setInt(1, param.getI_board());
			result = ps.executeUpdate();  //여기서 result = i_board에 좋아요 한 사람들의 수!
			ps.close();  //A객체를 닫는것
			
			ps = con.prepareStatement(sql2);
			ps.setInt(1, param.getI_board());
			ps.setInt(2, param.getI_user());
			result = ps.executeUpdate();
			
			if(result == 0) {
				con.rollback();
			} else {
				con.commit();
			}
			 
			con.setAutoCommit(true); //오토커밋 켜기
			
		} catch (Exception e) {
			e.printStackTrace();
			if(con != null) {  //혹시나 하는 에러때문에 넣은것
				try {
					con.rollback();  //rollback도 에러가 터질수 있기때문에 try/catch 해준다.
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		} finally {
			DbCon.close(con, ps);
		}
		
		return result;
		
	}
}
