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
				
	            // content 값을 DB에서 가져온 후 첫 번째 줄만 추출
	            String content = rs.getString("content");
	            content = getFirstLine(content);  // 첫 줄만 추출
	            dto.setContent(content);
				
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
	
	
	// 고객 문의 내역 상세보기 메서드
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
	            
	            // 답변 내용과 답변 일자 추가
	            dto.setAnswer_content(rs.getString("answer_content"));
	            dto.setAnswer_date(rs.getDate("answer_date"));
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
		
	} // getInquiryDetail() end
	
	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 1:1 문의 수정 메서드
	public int updateInquiryContent(int inquiryNo, String content) {
		
		int result = 0;
		
		try {
			openConn();
			
			sql = "update product_inquiry set content = ? where inquiry_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, content);
			pstmt.setInt(2, inquiryNo);
			
			result = pstmt.executeUpdate();
			
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
		
		
	} // updateInquiryContent() end


//-------------------------------------------------------------------------------------------------------------------------
	
	
	// 문의 삭제 메서드
	public int deleteInquiry(int inquiryNo) {
		
		int result = 0;

		try {		
			openConn();
			
			sql = "delete from product_inquiry where inquiry_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, inquiryNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
		
	} // deleteInquiry() end
	
	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 문의 삭제 후 문의 번호 정렬 메서드
	public void updateInquiryNoAfterDelete(int deleteNo) {
		
		try {
			openConn();
			
			sql = "update product_inquiry set inquiry_no = inquiry_no - 1 where inquiry_no > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, deleteNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
	} // updateInquiryNoAfterDelete() end
	
	
//-------------------------------------------------------------------------------------------------------------------------

	// 문의 수 반환하는 메서드
	public int getInquiryCount(int customerNo) {
	    int count = 0;

	    try {
	        openConn();
	        
	        sql = "select count(*) from product_inquiry where customer_no = ?";

	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setInt(1, customerNo);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return count;
	}

	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 첫 번째 줄만 추출하는 메서드
	public String getFirstLine(String content) {
	    if (content != null) {
	        // 줄바꿈을 기준으로 첫 번째 줄만 잘라냄
	        String[] lines = content.split("(\r\n|\n)", 2);  // 첫 번째 줄만 잘라내기
	        return lines[0];
	    }
	    return content;
	}
	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 관리자 - 전체 문의내역 목록 조회 메서드
	public List<InquiryDTO> getAllInquiriesForAdmin() {
		List<InquiryDTO> list = new ArrayList<>();

	    try {
	        openConn();
	        
	        // 관리자용으로 모든 문의 조회 (답변 여부, 작성자 정보 포함)
	        sql = "select inquiry_no, customer_no, product_no, content, inquiry_date, answer_content from product_inquiry order by inquiry_date desc";

	        pstmt = con.prepareStatement(sql);
	        
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            InquiryDTO dto = new InquiryDTO();
	            
	            dto.setInquiry_no(rs.getInt("inquiry_no"));
	            dto.setCustomer_no(rs.getInt("customer_no"));
	            dto.setProduct_no(rs.getInt("product_no"));
	            dto.setContent(rs.getString("content"));
	            dto.setInquiry_date(rs.getDate("inquiry_date"));
	            
	            // 답변 여부를 판단하여 '답변완료' 또는 '미답변'으로 설정하기 위해서 사용
	            dto.setAnswer_content(rs.getString("answer_content"));

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

	
	// 관리자) 전체 문의내역 상세보기
	public InquiryDTO getInquiryByNo(int inquiryNo) {
		
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
		        dto.setAnswer_content(rs.getString("answer_content"));
		        dto.setAnswer_date(rs.getDate("answer_date"));
		    }
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
		
	}
	
	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 관리자) 1:1 문의 답변 메서드
	public boolean addAnswerToInquiry(int inquiryNo, String answerContent) {
		boolean result = false;

		try {
			openConn();
			
			sql = "update product_inquiry set answer_content = ?, answer_date = sysdate where inquiry_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, answerContent);
			pstmt.setInt(2, inquiryNo);
			
			int updateCount = pstmt.executeUpdate();
			
			if (updateCount > 0) {
				result = true;
	        }
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
	}

	
//-------------------------------------------------------------------------------------------------------------------------

	
	// 관리자) 1:1 문의 답변 수정 메서드
	public int updateInquiryAnswerContent(InquiryDTO dto) {
		int result = 0;
		
		try {
			openConn();
			
			sql = "update product_inquiry set answer_content = ?, answer_date = ? where inquiry_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
	        pstmt.setString(1, dto.getAnswer_content());
	        
	        // 답변일자가 null일 경우, 현재 날짜를 설정
	        if (dto.getAnswer_date() == null) {
	            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis())); // 현재 날짜
	        } else {
	            pstmt.setDate(2, dto.getAnswer_date());
	        }
	        
	        pstmt.setInt(3, dto.getInquiry_no());
            
            result = pstmt.executeUpdate();
            
            
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
		
	}
	
	
	// 관리자) 1:1문의 답변 포함 상세보기
	public InquiryDTO getInquiryDetailWithAnswer(int inquiryNo) {
	    InquiryDTO dto = null;
	    
	    try {
	        openConn();
	        
	        // 답변까지 포함된 쿼리문
	        sql = "SELECT * FROM product_inquiry WHERE inquiry_no = ?";
	        
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
	            dto.setAnswer_content(rs.getString("answer_content")); // 답변 내용 추가
	            dto.setAnswer_date(rs.getDate("answer_date"));         // 답변 날짜 추가
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    
	    } finally {
	        closeConn(rs, pstmt, con);
	    }
	    
	    return dto;
	
	} // getInquiryDetailWithAnswer() end
	
	
//-------------------------------------------------------------------------------------------------------------------------
	
	
	// 관리자) 1:1문의 답변 삭제
	public int deleteInquiryAnswer(int inquiryNo) {
	    int result = 0;

	    try {
	        openConn();
	        
	        sql = "update product_inquiry set answer_content = null, answer_date = null where inquiry_no = ?";
	        
	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setInt(1, inquiryNo);

	        result = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    
	    } finally {
	        closeConn(pstmt, con);
	    }

	    return result;
	}
	
	
//-------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
}
