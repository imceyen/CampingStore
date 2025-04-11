package com.camping.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.model.ProductDAO;
import com.camping.model.RentalDAO;
import com.camping.model.RentalDTO;

public class AdminRentalReturnAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	String productNo = request.getParameter("product_no");
    	int rentalQty = Integer.parseInt(request.getParameter("rental_qty"));

    	// 1. 해당 product_no에 대해 '대여중' 상태인 rental 리스트 조회
    	List<RentalDTO> rentedList = RentalDAO.getRentedListByProduct(productNo);

    	// 2. 필요한 수량만큼 반납 처리
    	int remainingQty = rentalQty;

    	for (RentalDTO dto : rentedList) {
    	    if (remainingQty <= 0) break;

    	    int rentalNo = dto.getRental_no();
    	    int qty = dto.getRental_qty();

    	    int toReturn = Math.min(qty, remainingQty);

    	    // 반납 처리
    	    RentalDAO.updateRentalStatusToReturned(rentalNo, toReturn);
    	    // 재고 복구
    	    ProductDAO.increaseRentalStock(productNo, toReturn);

    	    remainingQty -= toReturn;
    	}

}
