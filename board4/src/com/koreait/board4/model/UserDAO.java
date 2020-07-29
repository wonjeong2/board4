package com.koreait.board4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.koreait.board4.vo.UserChangeVO;
import com.koreait.board4.vo.UserVO;

public class UserDAO {

	public static int join(UserVO param) {

		int result = 0;

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "insert into t_user3 (i_user, cid, cpw, nm, r_dt) values (seq_user.nextval, ?, ?, ?, sysdate)";

		try {

			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, param.getCid());
			ps.setNString(2, param.getCpw());
			ps.setNString(3, param.getNm());
			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps);
		}

		return result;

	}
	
	public static int chkId(String cid) {
		
		int result = 0;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		String sql = "select count(cid) as isExist from t_user3 where cid = ?";
		
		try {
			
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, cid);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps);
		}

		return result;
		
	}

	public static int login(UserVO param) { // 1. 로그인성공, 2.비밀번호틀림, 3.아이디없음, 4.에러발생

		int result = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select cpw, i_user, nm from t_user3 where cid = ?";

		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, param.getCid());
			rs = ps.executeQuery();

			if (rs.next()) { // 쿼리문 실행!
				
				int i_user = rs.getInt("i_user");
				String nm = rs.getNString("nm");
				String cpw = rs.getNString("cpw");

				if (cpw.equals(param.getCpw())) {

					result = 1; // 들어온 cpw 값과 쿼리문의 cpw 값이 일치하기 때문에 로그인 성공
					param.setI_user(i_user);
					param.setNm(nm);
				} else {
					result = 2; // 비밀번호 틀림
				}
			} else {  //쿼리문 자체가 못들어왔기때문에 아이디 없음
				result = 3;
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DbCon.close(con, ps, rs);
		}

		return result;
	}
	
	public static int changePw(UserChangeVO param) {
		
		int result = 2; //2:에러발생, 1:수정완료, 0:기존비밀번호 틀림
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "UPDATE t_user3 SET cpw = ? WHERE i_user = ? AND cpw = ?";
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, param.getChangePw());
			ps.setInt(2, param.getI_user());
			ps.setNString(3, param.getCurrentPw());
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
				
		return result;
	}
}





