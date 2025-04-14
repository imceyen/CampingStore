package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;

public class AdminInquiryAnswerAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 답변 내용과 문의 번호 받아오기
        String answerContent = request.getParameter("answer_content");
        int inquiryNo = Integer.parseInt(request.getParameter("inquiry_no"));
        
        // DAO를 이용해 답변 업데이트
        InquiryDAO dao = InquiryDAO.getInstance();
        boolean isAnswered = dao.addAnswerToInquiry(inquiryNo, answerContent);
        
        PrintWriter out = response.getWriter();

        if (isAnswered) {
            out.println("<script>");
            out.println("alert('답변이 성공적으로 등록되었습니다.');");
            out.println("location.href='admin_inquiry_view.go?inquiry_no=" + inquiryNo + "';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('답변 등록에 실패했습니다. 다시 시도해 주세요.');");
            out.println("history.back();");
            out.println("</script>");
        }

        return null;
    }
}