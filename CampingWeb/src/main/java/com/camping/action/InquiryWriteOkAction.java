package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class InquiryWriteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
        int customer_no = Integer.parseInt(request.getParameter("customer_no"));
        int product_no = Integer.parseInt(request.getParameter("product_no"));
        String content = request.getParameter("content");

        InquiryDTO dto = new InquiryDTO();
        dto.setCustomer_no(customer_no);
        dto.setProduct_no(product_no);
        dto.setContent(content);

        InquiryDAO dao = InquiryDAO.getInstance();
        int result = dao.insertInquiry(dto);

        ActionForward forward = new ActionForward();
        if (result > 0) {
            forward.setRedirect(true);
            forward.setPath("inquiry_list.go");
        } else {
            forward.setRedirect(false);
            forward.setPath("inquiry_write.go");
        }

        return forward;
    }
}