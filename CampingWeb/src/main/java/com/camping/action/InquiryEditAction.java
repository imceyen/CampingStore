package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class InquiryEditAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int inquiryNo = Integer.parseInt(request.getParameter("inquiry_no"));
		
		InquiryDAO dao = InquiryDAO.getInstance();
		InquiryDTO dto = dao.getInquiryDetail(inquiryNo); // 기존 문의 데이터 불러오기
		
		request.setAttribute("dto", dto);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("view/inquiry_edit.jsp");
		
		return forward;
		
    }
}
		
