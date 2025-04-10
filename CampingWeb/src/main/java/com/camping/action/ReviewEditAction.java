package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.ReviewDAO;
import com.camping.model.ReviewDTO;

public class ReviewEditAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int review_no = Integer.parseInt(request.getParameter("review_no"));

        ReviewDAO dao = ReviewDAO.getInstance();
        ReviewDTO dto = dao.getReviewDetail(review_no);

        request.setAttribute("review", dto);  // ← 이거 중요!!
        
        ActionForward forward = new ActionForward();
        
        forward.setRedirect(false);
        forward.setPath("view/review_edit.jsp");
        
        return forward;
    }
}