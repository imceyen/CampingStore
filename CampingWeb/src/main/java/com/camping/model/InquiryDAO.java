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

import com.camping.action.InquiryWriteOkAction;

public class InquiryDAO {

	// DB와 연결하는 객체
	Connection con = null;
		
	// DB에 SQL문을 전송하는 객체.
	PreparedStatement pstmt = null;
		
	// SQL문을 실행한 후에 결과값을 가지고 있는 객체.
	ResultSet rs = null;
		
	// SQL문을 저장할 변수
	String sql = null;
		
		
	// campingDAO 객체를 싱글턴 방식으로 만들어 보자.
	// 1단계 : campingDAO 객체를 정적(static) 멤버로
	//        선언을 해 주어야 한다.
	private static InquiryDAO instance = null;
		
	// 2단계 : 싱글턴 방식으로 객체를 만들기 위해서는 우선저긍로
	//        기본생성자의 접근제어자를 public이 아닌
	//        private으로 바꾸어 주어야 한다.
	//        즉, 외부에서 직접적으로 기본생성자를 접근하여
	//        호출하지 못하도록 하는 방법이다.
	
	private InquiryDAO() {  }  // 기본 생성자
		
	// 3단계 : 기본 생성자 대신에 싱글턴 객체를 return 해 주는
	//        getInstance() 라는 메서드를 만들어서 해당
	//        getInstance() 메서드를 외부에서 접근할 수 
	//        있도록 해 주면 됨.
	public static InquiryDAO getInstance() {
			
		if(instance == null) {
			instance = new InquiryDAO();
		}
			
		return instance;
	}  // getInstance() 메서드 end
		
		
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
			Context ctx =
					(Context)initCtx.lookup("java:comp/env");
				
			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
				
			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}  // openConn() 메서드 end
		
		
	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
			
			try {
				if(rs != null) rs.close();
				
				if(pstmt != null) pstmt.close();
					
				if(con != null) con.close();
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}  // closeConn() 메서드 end
		
		
	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(PreparedStatement pstmt, Connection con) {
			
		try {
				
			if(pstmt != null) pstmt.close();
				
			if(con != null) con.close();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
			
		}  // closeConn() 메서드 end


//-------------------------------------------------------------------------------------------------------------------------
	
	
	// 1:1 문의 작성 메서드
	public int insertInquiry(InquiryDTO dto) {
		
		int result = 0;
	    int newInquiryNo = 0;

	    try {
	        openConn(); 

	        // 1. 문의 번호의 최대값 구하기
	        sql = "SELECT NVL(MAX(INQUIRY_NO), 0) FROM product_inquiry"; 
	        
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // 2. 문의 번호의 최대값 + 1 = 새로운 문의 번호
	            newInquiryNo = rs.getInt(1) + 1; 
	        }
	        
	        // 3. 폼에서 입력받은 값 저장
	        sql = "INSERT INTO product_inquiry (inquiry_no, customer_no, product_no, content, inquiry_date, answer_content, answer_date) VALUES (?, ?, ?, ?, sysdate, ?, ?)";
	        
	        pstmt = con.prepareStatement(sql);

	        pstmt.setInt(1, newInquiryNo);  // 새로 생성된 INQUIRY_NO 값
	        pstmt.setInt(2, dto.getCustomer_no());  // 고객 번호
	        pstmt.setInt(3, dto.getProduct_no());  // 상품 번호
	        pstmt.setString(4, dto.getContent());  // 문의 내용
	        
	        pstmt.setNull(5, java.sql.Types.VARCHAR); // 관리자 답변
	        pstmt.setNull(6, java.sql.Types.DATE);    // 관리자 답변 작성 일자

	        result = pstmt.executeUpdate(); 

	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return result;
		
	} // insertInquiry() end
	

//-------------------------------------------------------------------------------------------------------------------------

	
	// 1:1문의 리스트
	public List<InquiryDTO> getInquiryList(int customerNo) {
		List<InquiryDTO> list = new ArrayList<>();

		try {
			openConn();
			
			sql = "select * from product_inquiry where customer_no = ? order by inquiry_no DESC";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, customerNo);
			
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				InquiryDTO dto = new InquiryDTO();
				
				dto.setInquiry_no(rs.getInt("inquiry_no"));
				dto.setCustomer_no(rs.getInt("customer_no"));
				dto.setProduct_no(rs.getInt("product_no"));
				dto.setContent(rs.getString("content"));
				dto.setInquiry_date(rs.getDate("inquiry_date"));
				
				list.add(dto);
			} // while end
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
		
	} // getInquiryList() end

	
//-------------------------------------------------------------------------------------------------------------------------
	
	
	// 문의 내역 상세보기 메서드
	public InquiryDTO getInquiryDetail(int inquiryNo) {
		InquiryDTO dto = null;
		
		try {
			openConn();
			
			sql = "select * from product_inquiry where inquiry_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, inquiryNo);
			
			rs = pstmt.executeQuery();
			
	        if (rs.next()) {
	            dto = new InquiryDTO();
	            dto.setInquiry_no(rs.getInt("inquiry_no"));
	            dto.setCustomer_no(rs.getInt("customer_no"));
	            dto.setProduct_no(rs.getInt("product_no"));
	            dto.setContent(rs.getString("content"));
	            dto.setInquiry_date(rs.getDate("inquiry_date"));
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
		
	} // getInquiryDetail() end
	
	
//-------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	
	
	
	
	
}
