package db.day2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.day1.OracleConnectUtil;

public class SelectDistinct {
// select 연습
	public static void main(String[] args) {
		
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql = "SELECT DISTINCT pcode FROM TBL_BUY#"; 
		List<String> codes = new ArrayList<>();
				
		System.out.println("구매 내역 확인합니다.");
		System.out.println("구매 내역은 판매된 상품코드로 조회합니다.");
		System.out.println("판매된 상품코드 : ");
		// 판매된 상품코드를 리스트에 추가
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				codes.add(rs.getString(1));
				System.out.print(String.format("%-20s", rs.getString(1)));
			}
			System.out.println();

			System.out.println("codes 출력 확인------------------");
			System.out.println(codes);
			
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Scanner sc = new Scanner(System.in); 
		System.out.println("구매내역에 상품코드가 존재하는지 확인 ---");
		System.out.println("상품코드 입력 -> ");
		String pcode = sc.nextLine();
		
		// select 한 결과가 변화가 없는 경우 List 활용
		if (codes.contains(pcode)) System.out.println("상품코드가 구매내역에 존재합니다.");
		else System.out.println("구매내역에 없는 상품코드 입니다.");
		
		
		OracleConnectUtil.close(conn);
	}

}