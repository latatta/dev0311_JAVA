package db.day2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import db.day1.OracleConnectUtil;

public class DeleteTest {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt;
		String sql = "DELETE FROM IDEV.TBL_CUSTOMER# WHERE CUSTOM_ID=?";
;

		System.out.print("회원탈퇴를 진행알 ID를 입력하세요 -> ");
		String id = sc.nextLine();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			pstmt.execute();
			pstmt.close();
			System.out.println("회원탈퇴가 완료되었습니다.");

		} catch (SQLException e) {
			System.out.println("SQL 실행오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
		sc.close();

	}

}