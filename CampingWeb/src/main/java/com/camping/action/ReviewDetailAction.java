package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.ReviewDAO;
import com.camping.model.ReviewDTO;

public class ReviewDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int review_no = Integer.parseInt(request.getParameter("review_no"));
		String from = request.getParameter("from"); // "my(내가 쓴 리뷰)" 또는 "all(모두가 쓴 리뷰)"
		
		HttpSession session = request.getSession();
        Integer loginCustomerNo = (Integer) session.getAttribute("cus_no");
		

        ReviewDAO dao = ReviewDAO.getInstance();
        
        ReviewDTO review = dao.getReviewDetail(review_no);
        
        request.setAttribute("review", review);
        request.setAttribute("from", from);
        request.setAttribute("isMine",
        	    loginCustomerNo != null && loginCustomerNo.equals(review.getCustomer_no()));


        ActionForward forward = new ActionForward();
        
        forward.setRedirect(false);
        forward.setPath("view/review_detail.jsp");

        return forward;
    }
}

