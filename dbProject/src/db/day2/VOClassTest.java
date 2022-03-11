package db.day2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.day1.OracleConnectUtil;
import db.vo.Custom;

public class VOClassTest {
// SelectTest2.java를 vo를 이용하여 자바변수에 저장하는 연습
	
	public static void main(String[] args) {

		Connection conn = OracleConnectUtil.connect();
		
		String sql = "select * from tbl_customer#";
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		List<Custom> customs = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();				// insert, update, delete
			rs = pstmt.executeQuery();		// select

			while (rs.next()) {
				// select 조회 결과를 한행씩 가져와서 List에 추가
				// getXXX(인덱스) : 
				
				// 방법 1) 커스텀 생성자
				customs.add(new Custom(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5)));		
				
				// 방법 2) setter 메소드
				Custom vo = new Custom();
				vo.setCustom_id(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setEmail(rs.getString(3));
				vo.setAge(rs.getInt(4));
				vo.setReg_date(rs.getDate(5));
				
				customs.add(vo);
				/*
				System.out.println(String.format(rs.getString("custom_id")));		
				System.out.print(String.format("%-20s", rs.getString("name")));				
				System.out.print(String.format("%-20s", rs.getString("email")));
				System.out.print(String.format("%5d  ", rs.getInt("age")));					
				System.out.println(rs.getTimestamp("reg_date"));
				*/
				
			}
			System.out.println("List 에 저장된 값 확인 -------------------------");
			System.out.println(customs);
			System.out.println("List의 데이터를 1개씩 출력 ----------------------");
			for (int i = 0; i < customs.size(); i++) {
				System.out.print(String.format("i : %-3s", i+1));
				System.out.println(customs.get(i));
			}
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}
		
		OracleConnectUtil.close(conn);
	}

}