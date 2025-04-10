package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.RentalDAO;
import com.camping.model.RentalStockDTO;

public class AdminRentalProductAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DAO 객체 생성
        RentalDAO dao = RentalDAO.getInstance();
        
        // 대여 재고 상태 목록 가져오기
        List<RentalStockDTO> rentalStockList = dao.getRentalStockStatus();

        // request에 데이터 담기
        request.setAttribute("rentalStockList", rentalStockList);

        // 포워딩 경로 설정
        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        forward.setPath("view/admin_rental_product.jsp");  // 여기에 JSP 만들 예정

        return forward;
    }
	

}
