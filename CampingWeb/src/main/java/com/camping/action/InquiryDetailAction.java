package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class InquiryDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		int inquiryNo = Integer.parseInt(request.getParameter("inquiry_no"));
		
		
		InquiryDAO dao = InquiryDAO.getInstance();
		
		InquiryDTO dto = dao.getInquiryDetail(inquiryNo);
		
		request.setAttribute("dto", dto);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("view/inquiry_detail.jsp");
		
		return forward;
		
	    
	}

}
