package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class AdminInquiryAnswerEditFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int inquiry_no = Integer.parseInt(request.getParameter("inquiry_no"));
        
        // 기존 문의 내용과 답변을 포함한 상세보기 조회
        InquiryDAO dao = InquiryDAO.getInstance();
        InquiryDTO dto = dao.getInquiryDetailWithAnswer(inquiry_no);
        
        // request에 기존 데이터 저장
        request.setAttribute("dto", dto);
        
        ActionForward forward = new ActionForward();
        
        forward.setRedirect(false);
        forward.setPath("view/admin_inquiry_answer_edit.jsp");  // 수정 폼 JSP로 포워딩
        
        return forward;
    }
}