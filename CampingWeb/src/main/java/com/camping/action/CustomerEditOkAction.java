package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.CustomerDAO;

public class CustomerEditOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("cus_id");

        String phone = request.getParameter("cus_phone");
        String address = request.getParameter("cus_addr");

        CustomerDAO dao = CustomerDAO.getInstance();
        int result = dao.updateCustomerInfo(id, phone, address);

        PrintWriter out = response.getWriter();

        if (result > 0) {
            // 회원 정보 수정 성공 시
            out.println("<script>");
            out.println("alert('회원 정보가 성공적으로 수정되었습니다.');");
            out.println("location.href='customer_mypage.go';");
            out.println("</script>");
        } else {
            // 회원 정보 수정 실패 시
            out.println("<script>");
            out.println("alert('회원 정보 수정에 실패했습니다. 다시 시도해주세요.');");
            out.println("history.back();");
            out.println("</script>");
        }

        return null; 
    }


}
