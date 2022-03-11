package db.day1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectTest2 {
// select SQL 실행 연습입니다.
	
	public static void main(String[] args) {
		
		Connection conn = OracleConnectUtil.connect();
		
		String sql = "select * from tbl_customer#";
		PreparedStatement pstmt = null;
		
		// select 쿼리는 결과를 저장해야 합니다.
		ResultSet rs = null;	// 쿼리 반환결과 테이블의 데이터 참조. 반환결과는 행단위로 접근합니다.
		// insert, update, delete 는 쿼리 실행한 반환결과 데이터가 없습니다.
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();				// insert, update, delete
			rs = pstmt.executeQuery();		// select

			while (rs.next()) {
				System.out.print(String.format("%-20s", rs.getString("custom_id")));		// 문자열 오른쪽 정렬이 기본
				System.out.print(String.format("%-20s", rs.getString("name")));				//  ㄴ '-' 기호는 왼쪽 정렬
				System.out.print(String.format("%-20s", rs.getString("email")));
				System.out.print(String.format("%5d  ", rs.getInt("age")));					// 정수는 왼쪽정렬을 하지 않고 오른쪽에 빈칸을 줘야함
				System.out.println(rs.getTimestamp("reg_date"));
				
				
			}
			
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}
		
		OracleConnectUtil.close(conn);
	}

}