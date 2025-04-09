package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class InquiryWriteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		

		// ✅ 1단계: 요청 파라미터 로그
        System.out.println("[LOG] 요청 파라미터 확인");
        System.out.println("customer_no = " + request.getParameter("customer_no"));
        System.out.println("product_no = " + request.getParameter("product_no"));
        System.out.println("content = " + request.getParameter("content"));

        int customer_no = Integer.parseInt(request.getParameter("customer_no"));
        int product_no = Integer.parseInt(request.getParameter("product_no"));
        String content = request.getParameter("content");

        InquiryDTO dto = new InquiryDTO();
        dto.setCustomer_no(customer_no);
        dto.setProduct_no(product_no);
        dto.setContent(content);

        // ✅ 2단계: DTO 세팅 후 로그
        System.out.println("[LOG] InquiryDTO 확인");
        System.out.println("dto.customer_no = " + dto.getCustomer_no());
        System.out.println("dto.product_no = " + dto.getProduct_no());
        System.out.println("dto.content = " + dto.getContent());

        InquiryDAO dao = InquiryDAO.getInstance();
        int result = dao.insertInquiry(dto);

        // ✅ 3단계: DAO 결과 로그
        System.out.println("[LOG] insertInquiry 실행 결과 = " + result);

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