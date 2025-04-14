package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class AdminInquiryListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		InquiryDAO dao = InquiryDAO.getInstance();
	        
	    // 전체 문의 내역 가져오기 (관리자 전용)
	    List<InquiryDTO> inquiryList = dao.getAllInquiriesForAdmin();
	    
	    // 각 문의의 content에서 첫 번째 줄만 추출하여 설정
	    for (InquiryDTO dto : inquiryList) {
	        String firstLine = dao.getFirstLine(dto.getContent()); 
	        dto.setContent(firstLine);
	    }

	    
	        
	    // inquiryList를 request에 저장하여 JSP에서 사용할 수 있도록 전달
	    request.setAttribute("inquiryList", inquiryList);

	    ActionForward forward = new ActionForward();
	        
	    forward.setRedirect(false);
	    forward.setPath("view/admin_inquiry_list.jsp"); 

	    return forward;
	    
	}


	
}