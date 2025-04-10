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

public class ReviewDAO {
	
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
		private static ReviewDAO instance = null;
			
		// 2단계 : 싱글턴 방식으로 객체를 만들기 위해서는 우선저긍로
		//        기본생성자의 접근제어자를 public이 아닌
		//        private으로 바꾸어 주어야 한다.
		//        즉, 외부에서 직접적으로 기본생성자를 접근하여
		//        호출하지 못하도록 하는 방법이다.
		
		private ReviewDAO() {  }  // 기본 생성자
			
		// 3단계 : 기본 생성자 대신에 싱글턴 객체를 return 해 주는
		//        getInstance() 라는 메서드를 만들어서 해당
		//        getInstance() 메서드를 외부에서 접근할 수 
		//        있도록 해 주면 됨.
		public static ReviewDAO getInstance() {
				
			if(instance == null) {
				instance = new ReviewDAO();
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
					e.printStackTrace();
				}
					
			}  // closeConn() 메서드 end
			
			
		// DB에 연결된 자원을 종료하는 메서드.
		public void closeConn(PreparedStatement pstmt, Connection con) {
				
			try {
					
				if(pstmt != null) pstmt.close();
					
				if(con != null) con.close();
					
			} catch (SQLException e) {
					e.printStackTrace();
			}
				
			}  // closeConn() 메서드 end


//-------------------------------------------------------------------------------------------------------------------------

	// 리뷰(후기) 등록하는 메서드
	// 추후 product_review와 product 테이블 join해서 상품명도 함께 출력할 예정
	public int insertReview(ReviewDTO dto) {
		
		int result = 0;
		int review_no = 1;
		    
		try {
			openConn();
			
		    // 현재 후기 중 제일 끝번호를 구해서 +1
		    sql = "select max(review_no) from product_review";
		        
		    pstmt = con.prepareStatement(sql);
		    rs = pstmt.executeQuery();

		    
		    if (rs.next()) {
		            review_no = rs.getInt(1) + 1;
		    }

		    rs.close();
		    pstmt.close();
		    	
		    	
		    // 리뷰(후기) 등록
		    sql = "insert into product_review values (?, ?, ?, ?, sysdate)";
		        
		    pstmt = con.prepareStatement(sql);
		        
		    pstmt.setInt(1, review_no);
	        pstmt.setInt(2, dto.getCustomer_no());    
	        pstmt.setInt(3, dto.getProduct_no());
	        pstmt.setString(4, dto.getContent());


		    result = pstmt.executeUpdate();
		        
		} catch (Exception e) {
			e.printStackTrace();
		    
		} finally {
		    closeConn(rs, pstmt, con);
		}
		    

		return result;
		
	} // insertReview() end


//-------------------------------------------------------------------------------------------------------------------------
	
	
	// 현재 로그인 되어있는 고객의 후기만 가져오기
	public List<ReviewDTO> getReviewsByCustomer(int customer_no) {
	    List<ReviewDTO> list = new ArrayList<>();
	    
	    try {
	        openConn();
	        
	        sql = "select * from product_review where customer_no = ? order by review_no desc";
	        
	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setInt(1, customer_no);
	        
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            ReviewDTO dto = new ReviewDTO();
	            
	            dto.setReview_no(rs.getInt("review_no"));
	            dto.setCustomer_no(rs.getInt("customer_no"));
	            dto.setProduct_no(rs.getInt("product_no"));
	            
	            String content = rs.getString("content");
	            dto.setContent(content);

	            // 줄바꿈 기준으로 첫 줄만 자르기
	            String[] lines = content.split("\\r?\\n");
	            dto.setFirstLine(lines.length > 0 ? lines[0] : content);
	            
	            dto.setReview_date(rs.getDate("review_date"));
	            
	            
	            list.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	   
	    } finally {
	        closeConn(rs, pstmt, con);
	   
	    }
	    
	    return list;
	    
	}
	

//-------------------------------------------------------------------------------------------------------------------------

	
	// 모든 사람이 작성한 전체 후기 가져오기
	public List<ReviewDTO> getAllReviews() {
	    List<ReviewDTO> list = new ArrayList<>();

	    try {
	        openConn();
	        
	        sql = "select * from product_review order by review_no desc";

	        pstmt = con.prepareStatement(sql);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            ReviewDTO dto = new ReviewDTO();
	            
	            dto.setReview_no(rs.getInt("review_no"));
	            dto.setCustomer_no(rs.getInt("customer_no"));
	            dto.setProduct_no(rs.getInt("product_no"));
	            
	            String content = rs.getString("content");
	            dto.setContent(content);
	            
	            String[] lines = content.split("\\r?\\n");
	            dto.setFirstLine(lines.length > 0 ? lines[0] : content);
	            
	            dto.setReview_date(rs.getDate("review_date"));
	            
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return list;
	
	} // getAllReviews() end

	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 후기 상세보기 메서드
	public ReviewDTO getReviewDetail(int reviewNo) {
	    ReviewDTO dto = null;

	    try {
	        openConn();
	        
	        sql = "select * from product_review where review_no = ?";
	        
	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setInt(1, reviewNo);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            dto = new ReviewDTO();
	            
	            dto.setReview_no(rs.getInt("review_no"));
	            dto.setCustomer_no(rs.getInt("customer_no"));
	            dto.setProduct_no(rs.getInt("product_no"));
	            dto.setContent(rs.getString("content"));
	            dto.setReview_date(rs.getDate("review_date"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return dto;
	    
	} // getReviewDetail() end

	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 리뷰 수정 메서드
	public int updateReview(ReviewDTO dto) {
		
		int result = 0;

		
		try {
			openConn();
			
			sql = "update product_review set content = ? where review_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getReview_no());
			
			result = pstmt.executeUpdate();
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
		
	} // updateReview() end
	
	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 리뷰 삭제 메서드
	public int deleteReview(int reviewNo) {
		
		int result = 0;

		try {		
			openConn();
			
			sql = "delete from product_review where review_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
		
	} // deleteReview() end
	
	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 리뷰 삭제 후 문의 번호 정렬 메서드
	public void updateReviewNoAfterDelete(int reviewNo) {
		
		try {
			openConn();
			
			sql = "update product_review set review_no = review_no - 1 where review_no > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, reviewNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
	} // updateReviewNoAfterDelete() end
	
	
	
	
	
	
	
	


}
