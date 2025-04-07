package com.camping.action;

import com.camping.model.AdminDAO;
import com.camping.model.ProductDTO;

import javax.servlet.http.*;
import java.io.IOException;

public class ProductInsertOkAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 상품 데이터 처리
        String productName = request.getParameter("product_name");
        int inputPrice = Integer.parseInt(request.getParameter("input_price"));
        int outputPrice = Integer.parseInt(request.getParameter("output_price"));
        int stockQty = Integer.parseInt(request.getParameter("stock_qty"));
        String productImage = request.getParameter("product_image");
        String detailImage1 = request.getParameter("detail_image1");
        String detailImage2 = request.getParameter("detail_image2");
        String detailImage3 = request.getParameter("detail_image3");
        String detailImage4 = request.getParameter("detail_image4");

        // DTO 객체에 데이터 세팅
        ProductDTO product = new ProductDTO();
        product.setProduct_name(productName);
        product.setInput_price(inputPrice);
        product.setOutput_price(outputPrice);
        product.setStock_qty(stockQty);
        product.setProduct_image(productImage);
        product.setDetail_image1(detailImage1);
        product.setDetail_image2(detailImage2);
        product.setDetail_image3(detailImage3);
        product.setDetail_image4(detailImage4);

        // DB에 상품 정보 추가
        AdminDAO dao = new AdminDAO();
        boolean result = dao.insertProduct(product);

        // ActionForward 객체 생성
        ActionForward forward = new ActionForward();

        if (result) {
            // 상품 등록 성공 후 메시지와 함께 리다이렉트
            forward.setPath("admin_product_manage.jsp?message=상품 등록 성공!");
            forward.setRedirect(true);
        } else {
            // 상품 등록 실패 후 에러 메시지 리다이렉트
            forward.setPath("admin_product_insert.jsp?error=상품 등록 실패!");
            forward.setRedirect(true);
        }

        return forward;
    }
}
