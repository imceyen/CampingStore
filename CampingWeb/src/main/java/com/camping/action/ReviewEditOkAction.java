package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.ReviewDAO;
import com.camping.model.ReviewDTO;

public class ReviewEditOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int review_no = Integer.parseInt(request.getParameter("review_no"));
		String content = request.getParameter("content");
		
		ReviewDTO dto = new ReviewDTO();
		
		dto.setReview_no(review_no);
		dto.setContent(content);
		
		ReviewDAO dao = ReviewDAO.getInstance();
		
		int result = dao.updateReview(dto);
		
		PrintWriter out = response.getWriter();
		
		
		if (result > 0) {
            out.println("<script>");
            out.println("alert('리뷰가 수정되었습니다.');");
            out.println("location.href='review_detail.go?review_no=" + review_no + "&from=my';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('리뷰 수정에 실패했습니다.');");
            out.println("history.back();");
            out.println("</script>");
        }
		
		
        return null;
	}

}

