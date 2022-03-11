package db.day1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTest {	// 오라클 idev 스키마에 customer#테이블에 고객을 추가하는 연습
							// SQL insert 실행
	public static void main(String[] args) {
		
		Connection conn = OracleConnectUtil.connect();
		System.out.println("main 메소드 확인용 : " + conn);
		
		String sql = "INSERT INTO IDEV.TBL_CUSTOMER# " +
					 "(CUSTOM_ID, NAME, EMAIL, AGE, REG_DATE)" + 
//					 "VALUES('sana', '최사나', 'unknown', 22, sysdate)";		// 항상 돟일한 값	 -> 변경 필요합니다.
					 "VALUES(?, ?, ?, ?, sysdate)";		// 값 부분에 ? -> 해당값은 execute 전에 대입
		
		// sql을 실행할 객체 생성
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 해당하는 값 전달. 
			pstmt.setString(1, "sana22");
			pstmt.setString(2, "이사나");
			pstmt.setString(3, "sana@gmail.com");
//			pstmt.setNString(parameterIndex, value);		// 오라클 데이터 타입이 NVARCHAR2, NCHAR일 때 사용
			pstmt.setInt(4, 27);
			
			pstmt.execute();
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}
		
		
	}

}