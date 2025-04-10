package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.ReviewDAO;
import com.camping.model.ReviewDTO;

public class ReviewAllListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        HttpSession session = request.getSession();
        
        Integer customer_no = (Integer) session.getAttribute("cus_no");
        
        // 로그인 여부 재확인
        if (customer_no == null) {
            ActionForward forward = new ActionForward();
            
            forward.setRedirect(true);
            forward.setPath("customer_login.go"); // 로그인 안되어있으면 로그인 페이지로
            
            return forward;
        }
        

        // 전체 리뷰 가져오기
        ReviewDAO dao = ReviewDAO.getInstance();
        
        List<ReviewDTO> allReviews = dao.getAllReviews();

        request.setAttribute("allReviews", allReviews);

        ActionForward forward = new ActionForward();
        
        forward.setRedirect(false);
        forward.setPath("view/review_all.jsp");  // 전체 리뷰 페이지
        
        
        return forward;
        
    }
}