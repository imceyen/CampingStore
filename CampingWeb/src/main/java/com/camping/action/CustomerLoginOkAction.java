package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.CustomerDAO;

public class CustomerLoginOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		CustomerDAO dao = CustomerDAO.getInstance();
		
		int result = dao.customerLogin(id, pwd);
		
		 if (result == 1) {
	            // 로그인 성공
	            HttpSession session = request.getSession();
	            session.setAttribute("cus_id", id);
	            
	            String name = dao.getCustomerName(id);  
	            session.setAttribute("cus_name", name);
	            
	            ActionForward forward = new ActionForward();
	            
	            forward.setRedirect(true);
	            forward.setPath("main.jsp");
	            return forward;
		 
		 } else {
	            // 로그인 실패
	            request.setAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
	            ActionForward forward = new ActionForward();
	            forward.setRedirect(false);
	            forward.setPath("view/customer_login.jsp");
	            return forward;
	        }

	}

}
