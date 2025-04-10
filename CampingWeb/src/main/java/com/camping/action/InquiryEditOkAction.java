package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;

public class InquiryEditOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int inquiryNo = Integer.parseInt(request.getParameter("inquiry_no"));
		String content = request.getParameter("content");
		
		InquiryDAO dao = InquiryDAO.getInstance();
		
		int result = dao.updateInquiryContent(inquiryNo, content);
		
		PrintWriter out = response.getWriter();
		
		
		if (result > 0) {
            out.println("<script>");
            out.println("alert('수정이 완료되었습니다.');");
            out.println("location.href='inquiry_detail.go?inquiry_no=" + inquiryNo + "';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('수정에 실패했습니다.');");
            out.println("history.back();");
            out.println("</script>");
        }
		
		return null;
	}

}
