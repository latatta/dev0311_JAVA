package proc_0311;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import db.day1.OracleConnectUtil;

public class ReturnBook {

	public static void main(String[] args) {
		Connection conn = OracleConnectUtil.connect();
		String sql = "{call return_book2(?,?,?)";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("책 코드를 입력하세요. -> ");
		String bcode = sc.nextLine();
		
		System.out.println("반납 기일을 입력하세요.(yyyy-mm-dd 형식) -> ");
		Date return_date = Date.valueOf(sc.nextLine());
		
		CallableStatement cstmt = null;
		
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setNString(1, bcode);
			cstmt.setDate(2, return_date);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			
			cstmt.executeUpdate();
			
			System.out.println("실행 결과 : " + cstmt.getNString(3));
		} catch (SQLException e) {
			System.out.println("실행 오류 : " + e.getMessage());
		}
		
	}

}
