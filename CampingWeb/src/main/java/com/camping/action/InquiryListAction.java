package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class InquiryListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 로그인 되어있는 정보
		Integer customerNo = (Integer)request.getSession().getAttribute("cus_no");
		
		
		InquiryDAO dao = InquiryDAO.getInstance();
		
		List<InquiryDTO> list = dao.getInquiryList(customerNo);
		
		request.setAttribute("inquiryList", list);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("view/inquiry_list.jsp");
		
		return forward;
	}

}
