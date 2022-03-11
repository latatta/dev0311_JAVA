package db.day2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import db.day1.OracleConnectUtil;

public class IDCheckInsert {
	private static Connection conn = OracleConnectUtil.connect();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String sql = "INSERT INTO IDEV.TBL_CUSTOMER# (CUSTOM_ID, NAME, EMAIL, AGE, REG_DATE)"
				+ "VALUES(?, ?, ?, ?, sysdate)";

		System.out.println("고객을 등록합니다. 먼저 사용할 ID를 입력하고 중복확인 합니다.");
		String id = "";
		do {
			System.out.print("ID 입력 -> ");
			id = sc.nextLine();

			if (idCheck(id)) {
				System.out.println("사용할 수 있는 id입니다.");
				break;
			} else
				System.out.println("사용중인 id입니다. 다시 입력해주세요.");

		} while (true);

		System.out.println("등록할 id \'" + id + "\' 입니다.");
		System.out.println("고객 이름 -> ");
		String name = sc.nextLine();
		System.out.println("고객 이메일 -> ");
		String email = sc.nextLine();
		System.out.println("고객 나이 -> ");
		int age = Integer.parseInt(sc.nextLine());
		System.out.println("회원가입이 완료되었습니다.");

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

	public static boolean idCheck(String id) {
		boolean result = false;
		String sql = "select * from tbl_customer# where custom_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (!rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}

		return result;
	}

}