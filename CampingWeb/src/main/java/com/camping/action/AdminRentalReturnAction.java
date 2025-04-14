package com.camping.action;

import java.io.IOException;
import java.sql.SQLException;
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

        List<RentalDTO> rentedList = RentalDAO.getInstance().getRentedListByProduct(productNo);
        int remainingQty = rentalQty;

        for (RentalDTO dto : rentedList) {
            if (remainingQty <= 0) break;

            int rentalNo = dto.getRental_no();
            int qty = dto.getRental_qty();
            int toReturn = Math.min(qty, remainingQty);

            RentalDAO.getInstance().updateRentalStatusToReturned(rentalNo, toReturn);
            ProductDAO.getInstance().increaseRentalStock(productNo, toReturn);

            remainingQty -= toReturn;
        }

        ActionForward forward = new ActionForward();
        forward.setRedirect(true);
        forward.setPath("admin_rental_list.go");
        return forward;
    }
}
