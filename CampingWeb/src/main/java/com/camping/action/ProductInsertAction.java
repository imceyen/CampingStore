package com.camping.action;

import com.camping.model.AdminDAO;
import com.camping.model.ProductDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.Part;

@WebServlet("/product_add_process.jsp")
@MultipartConfig
public class ProductInsertAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 업로드할 파일의 경로 설정
        String uploadPath = getServletContext().getRealPath("/") + "upload/";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // 폼 필드에서 정보 가져오기
        String productName = request.getParameter("product_name");
        String inputPrice = request.getParameter("input_price");
        String outputPrice = request.getParameter("output_price");
        String stockQty = request.getParameter("stock_qty");

        // 이미지 파일 처리
        String productImage = null, detailImage1 = null, detailImage2 = null, detailImage3 = null, detailImage4 = null;

        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            String fileName = extractFileName(part);
            if (fileName != null && !fileName.isEmpty()) {
                String filePath = uploadPath + fileName;
                part.write(filePath);  // 파일 저장

                // 파일 이름을 특정 이미지 필드에 할당
                if (part.getName().equals("product_image")) {
                    productImage = "upload/" + fileName;
                } else if (part.getName().equals("detail_image1")) {
                    detailImage1 = "upload/" + fileName;
                } else if (part.getName().equals("detail_image2")) {
                    detailImage2 = "upload/" + fileName;
                } else if (part.getName().equals("detail_image3")) {
                    detailImage3 = "upload/" + fileName;
                } else if (part.getName().equals("detail_image4")) {
                    detailImage4 = "upload/" + fileName;
                }
            }
        }

        // 상품 정보 DB에 저장
        ProductDTO product = new ProductDTO();
        product.setProduct_name(productName);
        product.setInput_price(Integer.parseInt(inputPrice));
        product.setOutput_price(Integer.parseInt(outputPrice));
        product.setStock_qty(Integer.parseInt(stockQty));
        product.setProduct_image(productImage);
        product.setDetail_image1(detailImage1);
        product.setDetail_image2(detailImage2);
        product.setDetail_image3(detailImage3);
        product.setDetail_image4(detailImage4);

        AdminDAO dao = new AdminDAO();
        boolean result = dao.insertProduct(product);

        if (result) {
            response.sendRedirect("index.jsp"); // 상품 목록 페이지로 리다이렉트
        } else {
            response.sendRedirect("admin_product_insert.jsp"); // 상품 추가 실패 시 다시 상품 추가 페이지로 리다이렉트
        }
    }

    // 파일 이름을 추출하는 메서드
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 2, cd.length() - 1);
            }
        }
        return null;
    }
}
