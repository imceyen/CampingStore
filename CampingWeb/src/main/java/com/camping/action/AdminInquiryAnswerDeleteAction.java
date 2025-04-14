package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;

public class AdminInquiryAnswerDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int inquiryNo = Integer.parseInt(request.getParameter("inquiry_no"));
        
        InquiryDAO dao = InquiryDAO.getInstance();
        
        int result = dao.deleteInquiryAnswer(inquiryNo);  // 삭제 결과
        
        PrintWriter out = response.getWriter();
        
        if (result > 0) {
            // 수정 성공 시
            out.println("<script>");
            out.println("alert('답변 내용이 성공적으로 삭제되었습니다.');");
            out.println("location.href='admin_inquiry_view.go?inquiry_no=" + inquiryNo + "';");
            out.println("</script>");
        } else {
            // 수정 실패 시
            out.println("<script>");
            out.println("alert('답변 삭제에 실패했습니다. 다시 시도해주세요.');");
            out.println("history.back();");
            out.println("</script>");
        }
        
        return null;  // ActionForward 사용하지 않음
    }
}