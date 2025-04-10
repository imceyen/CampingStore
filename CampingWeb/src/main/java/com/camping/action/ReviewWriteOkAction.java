package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.ReviewDAO;
import com.camping.model.ReviewDTO;

public class ReviewWriteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int customer_no = Integer.parseInt(request.getParameter("customer_no")); 
        int product_no = Integer.parseInt(request.getParameter("product_no"));
        // 실제 구현 시: 구매내역 페이지에서 후기 버튼 누르면 해당 상품 번호가 넘어옴
        // int product_no = Integer.parseInt(request.getParameter("product_no"));
        
        String content = request.getParameter("content"); // 후기 내용

        ReviewDTO dto = new ReviewDTO();
        
        dto.setCustomer_no(customer_no);	// 고객(회원) 번호
        dto.setProduct_no(product_no);		// 상품 번호
        dto.setContent(content);			// 후기 내용

        ReviewDAO dao = ReviewDAO.getInstance();
        int result = dao.insertReview(dto);

        PrintWriter out = response.getWriter();

        if (result > 0) {
            out.println("<script>");
            out.println("alert('리뷰가 성공적으로 등록되었습니다.');");
            out.println("location.href='review_list.go';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('리뷰 등록에 실패했습니다. 다시 시도해주세요.');");
            out.println("history.back();");
            out.println("</script>");
        }

        return null;
    }
}