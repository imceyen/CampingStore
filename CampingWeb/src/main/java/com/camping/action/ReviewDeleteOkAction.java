package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.InquiryDAO;
import com.camping.model.ReviewDAO;

public class ReviewDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int reviewNo = Integer.parseInt(request.getParameter("review_no"));
		
		ReviewDAO dao = ReviewDAO.getInstance();
		
		int result = dao.deleteReview(reviewNo);
		
		
		if (result > 0) {
            dao.updateReviewNoAfterDelete(reviewNo);

            response.getWriter().println("<script>");
            response.getWriter().println("alert('후기가 성공적으로 삭제되었습니다.');");
            response.getWriter().println("location.href='review_list.go';");
            response.getWriter().println("</script>");
        
		} else {
            response.getWriter().println("<script>");
            response.getWriter().println("alert('후기 삭제에 실패했습니다.');");
            response.getWriter().println("history.back();");
            response.getWriter().println("</script>");
        }
		
		
		return null;
	}

}
