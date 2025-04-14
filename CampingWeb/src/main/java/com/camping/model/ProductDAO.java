package com.camping.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProductDAO {

	// DB와 연결하는 객체
	Connection con = null;

	// DB에 SQL문을 전송하는 객체.
	PreparedStatement pstmt = null;

	// SQL문을 실행한 후에 결과값을 가지고 있는 객체.
	ResultSet rs = null;

	// SQL문을 저장할 변수
	String sql = null;

	// ProductDAO 객체를 싱글턴 방식으로 만들어 보자.
	// 1단계 : ProductDAO 객체를 정적(static) 멤버로
	// 선언을 해 주어야 한다.
	private static ProductDAO instance = null;

	// 2단계 : 싱글턴 방식으로 객체를 만들기 위해서는 우선저긍로
	// 기본생성자의 접근제어자를 public이 아닌
	// private으로 바꾸어 주어야 한다.
	// 즉, 외부에서 직접적으로 기본생성자를 접근하여
	// 호출하지 못하도록 하는 방법이다.
	private ProductDAO() {
	} // 기본 생성자

	// 3단계 : 기본 생성자 대신에 싱글턴 객체를 return 해 주는
	// getInstance() 라는 메서드를 만들어서 해당
	// getInstance() 메서드를 외부에서 접근할 수
	// 있도록 해 주면 됨.
	public static ProductDAO getInstance() {

		if (instance == null) {
			instance = new ProductDAO();
		}

		return instance;
	} // getInstance() 메서드 end

	// DB 연동하는 작업을 진행하는 메서드.
	// JDBC 방식이 아닌 DBCP 방식으로 DB와 연동 작업 진행.
	public void openConn() {

		try {
			// 1단계 : JNDI 서버 객체 생성.
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
			// 연결해 주는 개념이 Context 객체이며, InitialContext
			// 객체는 네이밍 서비스를 이용하기 위한 시작점이 됨.
			Context initCtx = new InitialContext();

			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env"는 현제 웹 애플리케이션에서
			// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
			// "java:comp/env" 아래에 위치를 하게 됨.
			Context ctx = (Context) initCtx.lookup("java:comp/env");

			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");

			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // openConn() 메서드 end

	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

		try {
			if (rs != null)
				rs.close();

			if (pstmt != null)
				pstmt.close();

			if (con != null)
				con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() 메서드 end

	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(PreparedStatement pstmt, Connection con) {

		try {

			if (pstmt != null)
				pstmt.close();

			if (con != null)
				con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() 메서드 end

	// cam_products 테이블에 상품을 추가(등록)하는 메서드.
	public int insertProduct(ProductDTO dto) {

		int result = 0, count = 0;

		try {
			openConn();

			sql = "select max(product_no) " + " from cam_product";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				count = rs.getInt(1) + 1;
			}

			// 출고가 기준으로 입고가 계산
			int inputPrice = dto.getOutput_price() / 2;
			int rentalUnitPrice = (int) (dto.getOutput_price() * 0.1);
			

			sql = "INSERT INTO cam_product "
					+ "(product_no, category_no, product_name, input_price, output_price, stock_qty, "
					+ "sold_qty, is_sold_out, is_rent_available, rental_unit_price, product_image, detail_image1, detail_image2, detail_image3, detail_image4) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, dto.getCategory_no());
			pstmt.setString(3, dto.getProduct_name());
			pstmt.setInt(4, inputPrice); // 계산된 입고가
			pstmt.setInt(5, dto.getOutput_price());
			pstmt.setInt(6, dto.getStock_qty());
			pstmt.setInt(7, 0); // sold_qty = 0
			pstmt.setString(8, "N"); // is_sold_out = 'N'
			pstmt.setString(9, "Y"); // is_rent_available = 'N'
			pstmt.setInt(10, rentalUnitPrice); // 대여금액
			pstmt.setString(11, dto.getProduct_image());
			pstmt.setString(12, dto.getDetail_image1());
			pstmt.setString(13, dto.getDetail_image2());
			pstmt.setString(14, dto.getDetail_image3());
			pstmt.setString(15, dto.getDetail_image4());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // insertProduct() 메서드 end
	
	

	// 전체 상품 목록을 조회하는 메서드.
	public List<ProductDTO> getProductList() {

		List<ProductDTO> list = new ArrayList<ProductDTO>();

		try {
			openConn();

			sql = "select * from cam_product " + " order by product_no desc";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				ProductDTO dto = new ProductDTO();

				dto.setProduct_no(rs.getInt("product_no"));
				dto.setCategory_no(rs.getInt("category_no"));
				dto.setProduct_name(rs.getString("product_name"));
				dto.setOutput_price(rs.getInt("output_price"));
				dto.setStock_qty(rs.getInt("stock_qty"));
				dto.setProduct_image(rs.getString("product_image"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getProductList() 메서드 end

	// 제품번호에 해당하는 제품의 상세 정보를 조회하는 메서드.
	public ProductDTO getProductContent(int no) {

		ProductDTO dto = null;

		try {
			openConn();

			sql = "select * from cam_product " + " where product_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new ProductDTO();

				dto.setProduct_no(rs.getInt("product_no"));
				dto.setCategory_no(rs.getInt("category_no"));
				dto.setProduct_name(rs.getString("product_name"));
				dto.setInput_price(rs.getInt("input_price"));
				dto.setOutput_price(rs.getInt("output_price"));
				dto.setStock_qty(rs.getInt("stock_qty"));
				dto.setSold_qty(rs.getInt("sold_qty"));
				dto.setIs_sold_out(rs.getString("is_sold_out"));
				dto.setIs_rent_available(rs.getString("is_rent_available"));
				dto.setRental_unit_price(rs.getInt("rental_unit_price"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;

	} // getProductContent() 메서드 end

	// 제품번호에 해당하는 제품의 정보를 수정하는 메서드. 
	public int updateProduct(ProductDTO dto) {

	int result = 0;

	try
	{
		openConn();

		sql = "update cam_product set product_image = ?, product_name = ?, stock_qty = ?, output_price = ?, rental_unit_price = ?, is_sold_out = ?, is_rent_available = ?  where product_no = ?";

		pstmt = con.prepareStatement(sql);

		pstmt.setString(1, dto.getProduct_image());
		pstmt.setString(2, dto.getProduct_name());
		pstmt.setInt(3, dto.getStock_qty());
		pstmt.setInt(4, dto.getOutput_price());
		pstmt.setInt(5, dto.getRental_unit_price());
		pstmt.setString(6, dto.getIs_sold_out());
		pstmt.setString(7, dto.getIs_rent_available());
		pstmt.setInt(8, dto.getProduct_no());
		

		result = pstmt.executeUpdate();

	}catch(
	SQLException e)
	{
		e.printStackTrace();
	}finally
	{
		closeConn(pstmt, con);
	}

	return result;} // updateProduct() 메서드 end

	// 상품번호에 해당하는 상품을 DB에서 삭제하는 메서드.
public int deleteProduct(int no) {

	int result = 0;

	try
	{
		openConn();

		sql = "delete from cam_product " + " where product_no = ?";

		pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, no);

		result = pstmt.executeUpdate();

	}catch(
	SQLException e)
	{
		e.printStackTrace();
	}finally
	{
		closeConn(pstmt, con);
	}

	return result;} // deleteProduct() 메서드 end

	// 상품 삭제 시 상품번호 재작업 하는 메서드.
	public void updateSequence(int product_no) {

	try{openConn();

	sql="update cam_product "+" set product_no = product_no - 1 "+" where product_no > ?";

	pstmt=con.prepareStatement(sql);

	pstmt.setInt(1,product_no);

	pstmt.executeUpdate();

	}catch(
	SQLException e)
	{
		e.printStackTrace();
	}finally
	{
		closeConn(pstmt, con);
	}
	}
	
	// 재고 수량 증가
	// ProductDAO.java 안에 있어야 정상 동작함
	public int increaseRentalStock(String productNo, int qty) {
	    int result = 0;
	    openConn();
	    try {
	        sql = "UPDATE cam_product SET rental_stock = rental_stock + ? WHERE product_no = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, qty);
	        pstmt.setInt(2, Integer.parseInt(productNo));
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(pstmt, con);
	    }
	    return result;
	}



	 // updateSequence() 메서드 end

	/*
	 * // 카테고리 코드에 해당하는 제품의 전체 리스트를 // 조회하는 메서드. public List<ProductDTO>
	 * getProductList(String code) {
	 * 
	 * List<ProductDTO> list = new ArrayList<ProductDTO>();
	 * 
	 * 
	 * try { openConn();
	 * 
	 * sql = "select * from shop_products " + " where pcategory_fk = ?";
	 * 
	 * pstmt = con.prepareStatement(sql);
	 * 
	 * pstmt.setString(1, code);
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * while(rs.next()) {
	 * 
	 * ProductDTO dto = new ProductDTO();
	 * 
	 * dto.setPnum(rs.getInt("pnum")); dto.setPname(rs.getString("pname"));
	 * dto.setPcategory_fk(rs.getString("pcategory_fk"));
	 * dto.setPcompany(rs.getString("pcompany"));
	 * dto.setPimage(rs.getString("pimage")); dto.setPqty(rs.getInt("pqty"));
	 * dto.setPrice(rs.getInt("price")); dto.setPspec(rs.getString("pspec"));
	 * dto.setPcontent(rs.getString("pcontents")); dto.setPoint(rs.getInt("point"));
	 * dto.setPinputdate(rs.getString("pinputdate"));
	 * 
	 * list.add(dto);
	 * 
	 * }
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } finally { closeConn(rs,
	 * pstmt, con); }
	 * 
	 * return list; } // getProductList() 메서드 end
	 */	

}
