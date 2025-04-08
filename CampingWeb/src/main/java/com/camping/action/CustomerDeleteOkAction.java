package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.CustomerDAO;

public class CustomerDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("cus_id");
		
		CustomerDAO dao = CustomerDAO.getInstance();
		
		
		// 탈퇴할 회원의 번호를 id로 조회
		int cusNo = dao.getCustomerNo(id);
		
		// 회원 탈퇴(삭제)
		int result = dao.deleteCustomer(id);
		
		
		if (result > 0) {
			// 탈퇴한 회원 이후의 회원들의 회원 번호를 앞으로 당김
			dao.updateAfterDelete(cusNo);
			
			// 세션 초기화
			session.invalidate();
			
	        PrintWriter out = response.getWriter();

	        out.println("<script>");
	        out.println("alert('회원 탈퇴가 완료되었습니다.');");
	        out.println("location.href='main.jsp';");
	        out.println("</script>");

	        out.close();
	        return null;  // forward 안 하고 여기서 종료
	        
		} // if end
		
		
        // 탈퇴 실패했을 경우
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('회원 탈퇴에 실패했습니다. 다시 시도해주세요.');");
        out.println("history.back();");
        out.println("</script>");
        out.close();

        return null;
		
		
	}
	

}
