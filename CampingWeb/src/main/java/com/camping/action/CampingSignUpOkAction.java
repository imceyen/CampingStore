package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.CustomerDAO;
import com.camping.model.CustomerDTO;

public class CampingSignUpOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 회원가입 폼 페이지에서 넘어온 데이터를 테이블에 저장
		/*
		 *  customer_no NUMBER PRIMARY KEY,             -- 고객 번호
    customer_id VARCHAR2(30) UNIQUE NOT NULL,   -- 고객 아이디 (아이디 중복 방지)
    password VARCHAR2(100) NOT NULL,            -- 고객 비밀번호
    name VARCHAR2(50) NOT NULL,                 -- 고객 이름
    birth_date DATE,                            -- 고객 생년월일
    gender CHAR(1),                             -- 고객 성별 'M' or 'F'         
    phone VARCHAR2(20),                         -- 고객 전화번호
    address VARCHAR2(200)                       -- 고객 주소

		 */
		
		String customer_id = request.getParameter("cus_id").trim();
		String password = request.getParameter("cus_pwd").trim();
		String name = request.getParameter("cus_name").trim();
		String birth_date = request.getParameter("cus_birth").trim();
		String gender = request.getParameter("cus_gender").trim();
		String phone = request.getParameter("cus_phone").trim();
		String address = request.getParameter("cus_addr").trim();
		
		CustomerDTO dto = new CustomerDTO();
		
		dto.setCustomer_id(customer_id);
		dto.setPassword(password);
		dto.setName(name);
		dto.setBirth_Date(birth_date);
		dto.setGender(gender);
		dto.setPhone(phone);
		dto.setAddress(address);
		
		CustomerDAO dao = CustomerDAO.getInstance();
		
		int check = dao.joinCamping(dto);
		
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			// 게시글 추가 성공한 경우
			out.println("<script>");
			out.println("alert('회원가입 완료')");
			out.println("location.href='main.jsp'");			
			out.println("</script>");
			
			// forward.setRedirect(true);
			// forward.setPath("bbs_list.go");
		}else {
			// 게시글 추가 실패한 경우
			out.println("<script>");
			out.println("alert('회원가입 실패')");
			out.println("history.back()");			
			out.println("</script>");
			
			// forward.setRedirect(false);
			// forward.setPath("bbs/bbs_write.jsp");
		}
		
		return null;
	}
}
