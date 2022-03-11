package db.day3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.day1.OracleConnectUtil;
import db.vo.Product;

public class ProductDao {

	private static ProductDao productDao = new ProductDao();

	private ProductDao() {
	}

	public static ProductDao getProductDao() {
		return productDao;
	}

// --------------------------------------------------------------------------------------------
	public static Product selectOne(String pcode) {
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt = null;
		String sql = "select * from tbl_product# where pcode = ?";
		ResultSet rs = null;
		Product vo = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pcode);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 조회결과 1개 행 있으면
				vo = new Product();
				vo.setPcode(rs.getString(1));
				vo.setCategory(rs.getString(2));
				vo.setPname(rs.getString(3));
				vo.setPrice(rs.getInt(4));
			} // 없으면(else) null 반환
			pstmt.close();

		} catch (SQLException e) {
			System.out.println("SQL 실행오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
		return vo;
	}

// --------------------------------------------------------------------------------------------
	public void insert(Product vo) {
		Connection conn = OracleConnectUtil.connect();
		System.out.println("main 메소드 확인용 : " + conn);

		String sql = "INSERT INTO IDEV.TBL_PRODUCT# (PCODE, CATEGORY, PNAME, PRICE)"
				+ "VALUES(?, ?, ?, ?)";

		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getPcode());
			pstmt.setString(2, vo.getCategory());
			pstmt.setString(3, vo.getPname());
			pstmt.setInt(4, vo.getPrice());

			pstmt.execute();
			System.out.println("상품 1건이 새로 등록되었습니다.");
			pstmt.close();

		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
	}

	// ---------------------------------------------------------------------------------------
	public void update(Product vo) {
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt;
		String sql = "UPDATE IDEV.TBL_PRODUCT# SET CATEGORY = ?, PNAME=?, PRICE = ? WHERE PCODE=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getCategory());
			pstmt.setString(1, vo.getPname());
			pstmt.setInt(1, vo.getPrice());
			pstmt.setString(1, vo.getPcode());

			pstmt.execute();
			pstmt.close();
			System.out.println("상품이름, 가격, 카테로리 변경이 완료되었습니다.");

		} catch (SQLException e) {
			System.out.println("SQL 실행오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
	}

	// ---------------------------------------------------------------------------------------
	public void delete(String pcode) {
		Connection conn = OracleConnectUtil.connect();
		PreparedStatement pstmt;
		String sql = "DELETE FROM IDEV.TBL_PRODUCT# WHERE PCODE=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pcode);

			pstmt.execute();
			pstmt.close();
			System.out.println("상품제거가 완료되었습니다.");

		} catch (SQLException e) {
			System.out.println("SQL 실행오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
	}

	// ---------------------------------------------------------------------------------------
	public List<String> selectCategory() {
		Connection conn = OracleConnectUtil.connect();
		String sql = "select category from tbl_customer#";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> categories = new ArrayList<>(); 

		System.out.println("카테고리 : ");

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				categories.add(rs.getString(1));
				System.out.print(String.format("%-20s", rs.getString(1)));
			}

		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}

		return categories;
	}

	// ---------------------------------------------------------------------------------------
	public List<Product> selectAll() {
		Connection conn = OracleConnectUtil.connect();
		String sql = "select * from tbl_product#";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> products = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 

			while (rs.next()) {
				products.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));

			}
			pstmt.close();

		} catch (SQLException e) {
			System.out.println("SQL 실행 오류 : " + e.getMessage());
		}

		OracleConnectUtil.close(conn);
		return products;
	}

}
