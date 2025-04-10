package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;

public class InquiryDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int inquiryNo = Integer.parseInt(request.getParameter("inquiry_no"));
		
		InquiryDAO dao = InquiryDAO.getInstance();
		
		int result = dao.deleteInquiry(inquiryNo);
		
		
		if (result > 0) {
            dao.updateInquiryNoAfterDelete(inquiryNo);

            response.getWriter().println("<script>");
            response.getWriter().println("alert('문의가 성공적으로 삭제되었습니다.');");
            response.getWriter().println("location.href='inquiry_list.go';");
            response.getWriter().println("</script>");
        } else {

            response.getWriter().println("<script>");
            response.getWriter().println("alert('문의 삭제에 실패했습니다.');");
            response.getWriter().println("history.back();");
            response.getWriter().println("</script>");
        }
		

		
		return null;
	}

}
