package db.day3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.day1.OracleConnectUtil;
import db.vo.Custom;

public class CustomDao {
	
	// 필드값이 없는 클래스 -> new 연산으로 객체를 생성했을 때 서로 다른 필드값을 갖는 경우가 없습니다.
	// -> static 키워드로 실행 가능한 메소드 입니다.
	// -> jdbc에서 dao클래스는 static으로 하지 않습니다. -> 싱글턴 객체로 사용합니다.
	
	private static CustomDao customDao = new CustomDao();
	private CustomDao() { }
	public static CustomDao getCustomDao() {
		return customDao;
	}
	
	
	// select 쿼리 (기본키 컬럼으로 조회하고 결과 반환)
		public static Custom selectOne(String custom_id) {			// 1개를 조회할때는 Custom 한개만 반환
			Connection conn = OracleConnectUtil.connect();
			PreparedStatement pstmt = null;
			String sql = "select * from tbl_customer# where custom_id = ?";
			ResultSet rs = null;
			Custom vo = null;
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, custom_id);
				rs = pstmt.executeQuery();
				if (rs.next()) {		// 조회결과 1개 행 있으면
					vo = new Custom();
					vo.setCustom_id(rs.getString(1));
					vo.setName(rs.getString(2));
					vo.setEmail(rs.getString(3));
					vo.setAge(rs.getInt(4));
					vo.setReg_date(rs.getDate(5));
				}		// 없으면(else) null 반환
				pstmt.close();
				
			} catch (SQLException e) {
				System.out.println("SQL 실행오류 : " + e.getMessage());
			}
			
			
			OracleConnectUtil.close(conn);
			return vo;
		}

// --------------------------------------------------------------------------------------------
	// insert 쿼리
	public void insert(Custom vo) {
		Connection conn = OracleConnectUtil.connect();
		System.out.println("main 메소드 확인용 : " + conn);

		String sql = "INSERT INTO IDEV.TBL_CUSTOMER# (CUSTOM_ID, NAME, EMAIL, AGE, REG_DATE)"
				+ "VALUES(?, ?, ?, ?, sysdate)";

//		String id = null;
//		String name = null;
//		String email = null;
//		int age = 0;

		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getCustom_id());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setInt(4, vo.getAge());

			pstmt.execute();
			System.out.println("고객 1건이 새로 등록되었습니다.");
			pstmt.close();

		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
	}

// ---------------------------------------------------------------------------------------
	// update 쿼리
//	public void update(String id, String email) {		// 방법 1)
	public void update(Custom vo) { // 방법 2)
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt;
		String sql = "UPDATE IDEV.TBL_CUSTOMER#	SET EMAIL=? WHERE CUSTOM_ID=?";
//		String id = null;
//		String email = null;

		try {
			pstmt = conn.prepareStatement(sql);
			// 방법 1)
//			pstmt.setString(1, email);				
//			pstmt.setString(2, id);				

			// 방법 2) 수정할 컬럼이 많을때 매개변수를 모두 나열하는 것은 비효율적이므로 객체로 받아옵니다.
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(1, vo.getCustom_id());

			pstmt.execute();
			pstmt.close();
			System.out.println("email 변경이 완료되었습니다.");

		} catch (SQLException e) {
			System.out.println("SQL 실행오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
	}

// ---------------------------------------------------------------------------------------
	// delete 쿼리
	public void delete(String custom_id) {
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt;
		String sql = "DELETE FROM IDEV.TBL_CUSTOMER# WHERE CUSTOM_ID=?";
//		String custom_id = null;		// custom_id는 이 메소드를 호출할 때 값이 전달됨

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, custom_id);

			pstmt.execute();
			pstmt.close();
			System.out.println("회원탈퇴가 완료되었습니다.");

		} catch (SQLException e) {
			System.out.println("SQL 실행오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
	}

// ---------------------------------------------------------------------------------------
	
	// selectOne() 메소드와 쿼리는 동일하지만 리턴형식이 다른 예
	boolean idCheck(String id) {
		Connection conn = OracleConnectUtil.connect();
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
// ---------------------------------------------------------------------------------------
	// select 쿼리(조건 없음)
	public List<Custom> selectAll() {		// 여러개를 조회할때는 list에  Custom을 저장하여 반환
		Connection conn = OracleConnectUtil.connect();
		String sql = "select * from tbl_customer#";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Custom> customs = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute(); // insert, update, delete
			rs = pstmt.executeQuery(); // select

			while (rs.next()) {
				customs.add(new Custom(rs.getString(1), 
										rs.getString(2), 
										rs.getString(3), 
										rs.getInt(4), 
										rs.getDate(5)));

			}
			pstmt.close();

		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
		return customs;
	}
}
