package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.ReviewDAO;
import com.camping.model.ReviewDTO;

public class ReviewListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
	    HttpSession session = request.getSession();

	    // 로그인된 고객의 번호 가져오기
	    Integer customer_no = (Integer) session.getAttribute("cus_no");
	    
	    
	    // 로그인 여부 확인
	    if (customer_no == null) {
	        ActionForward forward = new ActionForward();
	        
	        forward.setRedirect(true);
	        forward.setPath("customer_login.go");  // 로그인 페이지로
	        
	        return forward;
	    }
	    
	    

	    ReviewDAO dao = ReviewDAO.getInstance();

	    List<ReviewDTO> myReviews = dao.getReviewsByCustomer(customer_no);
	    
	    request.setAttribute("myReviews", myReviews);


	    // 내가 쓴 후기 목록으로 이동
	    ActionForward forward = new ActionForward();
	    forward.setRedirect(false);
	    forward.setPath("view/review_list.jsp");  // 내가 쓴 리뷰 목록 페이지로 이동

	    return forward;
	}
	
}
