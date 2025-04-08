package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.CustomerDAO;


public class CustomerCheckPwdOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	    HttpSession session = request.getSession();
	    
	    String id = (String)session.getAttribute("cus_id"); // 세션에서 id 가져오기
	    String pwd = request.getParameter("cus_pwd");   // 폼에서 입력된 비밀번호
	    String target = request.getParameter("target"); // 정보 변경 or 회원 탈퇴에서 사용할 비밀번호 재확인

	    CustomerDAO dao = CustomerDAO.getInstance();
	    
	    boolean isValid = dao.checkPassword(id, pwd); // 이게 true일 경우에 성공
	    
	    if (!isValid) {
	        PrintWriter out = response.getWriter();
		    
	        // 비밀번호 불일치
	        out.println("<script>");
	        out.println("alert('비밀번호가 일치하지 않습니다.');");
	        out.println("history.back();");
	        out.println("</script>");
	        
	        out.close();
	        
	        return null;
	    }

	    // 비밀번호 일치
	    ActionForward forward = new ActionForward();
	    forward.setRedirect(false);
	    
	    if ("delete".equals(target)) {
	    	// 회원 탈퇴
	    	forward.setPath("customer_delete.go"); // 회원 탈퇴 페이지로 이동
	    
	    } else {
	    	// 정보 변경 (기본)
	    	forward.setPath("customer_edit.go"); // 회원 정보 변경 페이지로 이동
	    }

	    return forward;
	
    }
	

}