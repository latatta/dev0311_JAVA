package proc_0311;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import db.day1.OracleConnectUtil;

// 데이터베이스에서 만든 proc_set_money 프로시저를 jdbc 방식으로 실행합니다.
public class SetMoneyProc_1 {

	public static void main(String[] args) {
		Connection conn = OracleConnectUtil.connect();
		String sql = "{call proc_set_money(?,?,?,?)}";		
		// 데이터베이스 프로시저 {call 프로시저 이름(매개변수)}
		
		// 프로시저에서 실행할 때 필요한 매개변수 선언
		String custom_id = "wonder";
		String pcode = "CJ-BABQ1";
		int quantity = 5;
		
		// 프로시저 실행은 PreparedStatement가 아니라 
		CallableStatement cstmt = null;
		
		try {
			cstmt = conn.prepareCall(sql);
			
			// 매개변수값 설정
			cstmt.setNString(1, custom_id);		// nvarchar2, nchar등 문자개수로 정하는 데이터타입이면
			cstmt.setNString(2, pcode);			//		setNString()사용 - 다른 때 써도 오류는 안남
			cstmt.setInt(3, quantity);
			// 4번째 매개변수는 OUT이라는 것을 알려줘야 함
			cstmt.registerOutParameter(4, Types.VARCHAR);
			// 정수/실수 일때는 Tpes.Numeric, 날짜는 Types.Date
			
			cstmt.executeUpdate();		// 프로시저 실행
			
			// 실행 후 프로시저 출력 매개변수 가져와서 print
			System.out.println("실행결과 : " + cstmt.getNString(4));
			cstmt.close();
			
		} catch (SQLException e) {
			System.out.println("실행 오류 : " + e.getMessage());
			
		} finally {
			OracleConnectUtil.close(conn);
		}
		
	}

}
