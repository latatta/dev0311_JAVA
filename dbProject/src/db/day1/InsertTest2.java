package db.day1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertTest2 { // insert 할 값을 Scanner 이용해서 입력

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conn = OracleConnectUtil.connect();
		System.out.println("main 메소드 확인용 : " + conn);

		String sql = "INSERT INTO IDEV.TBL_CUSTOMER# (CUSTOM_ID, NAME, EMAIL, AGE, REG_DATE)"
				+ "VALUES(?, ?, ?, ?, sysdate)"; // 값 부분에 ? -> 해당값은 execute 전에 대입

		System.out.println("고객 정보를 입력하세요.");
		System.out.println("고객 ID -> ");
		String id = sc.nextLine();
		System.out.println("고객 이름 -> ");
		String name = sc.nextLine();
		System.out.println("고객 이메일 -> ");
		String email = sc.nextLine();
		System.out.println("고객 나이 -> ");
		int age = Integer.parseInt(sc.nextLine());

		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setInt(4, age);

			pstmt.execute();
			pstmt.close();

		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}
		
		OracleConnectUtil.close(conn);
	}

}