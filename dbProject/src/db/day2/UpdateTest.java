package db.day2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import db.day1.OracleConnectUtil;

public class UpdateTest {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt;
		String sql = "UPDATE IDEV.TBL_CUSTOMER#	SET EMAIL=?" + 
				"WHERE CUSTOM_ID=?";
		
		System.out.print("email을 수정할 ID를 입력하세요 -> ");
		String id = sc.nextLine();
		System.out.print("새로운 email을 입력하세요 -> ");
		String email = sc.nextLine();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			
			pstmt.execute();
			pstmt.close();
			System.out.println("email 변경이 완료되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("SQL 실행오류 : " + e.getMessage());
		}
		
		OracleConnectUtil.close(conn);
	}

}