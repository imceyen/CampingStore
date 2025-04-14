package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class AdminInquiryViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int no = Integer.parseInt(request.getParameter("inquiry_no"));  // 문의 번호 받아옴

        InquiryDAO dao = InquiryDAO.getInstance();
        InquiryDTO dto = dao.getInquiryByNo(no);  // 상세 정보 가져오기

        request.setAttribute("dto", dto);  // JSP로 전달

        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        forward.setPath("view/admin_inquiry_view.jsp");

        return forward;
    }


}
