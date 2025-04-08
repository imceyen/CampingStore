package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.camping.action.Action;
import com.camping.action.ActionForward;
import com.camping.model.ProductDAO;
import com.camping.model.ProductDTO;

public class AdminProductModifyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 상픔 수정 폼 페이지에서 넘어온 데이터들을
		// 가지고 DB에 수정하는 비지니스 로직.

		// 첨부파일이 저장될 위치(경로) 설정.
		String saveFolder = "C:\\Users\\4Class_05\\git\\CampingStore\\CampingStore\\CampingWeb\\src\\main\\webapp\\upload";

		// 첨부파일 용량(크기) 제한. - 파일 업로드 최대 크기
		int fileSize = 10 * 1024 * 1024; // 10MB

		// 파일 업로드를 위한 객체 생성.
		MultipartRequest multi = new MultipartRequest(request, // request 객체
				saveFolder, // 첨부파일이 저장될 위치
				fileSize, // 첨부 파일 최대 크기
				"UTF-8", // 한글 처리
				new DefaultFileRenamePolicy());

		// 상품 등록 폼 페이지에서 넘어온 데이터들을 받아 주자.
		int category_no = Integer.parseInt(multi.getParameter("category_no").trim());

		String product_name = multi.getParameter("product_name").trim();

		int input_price = Integer.parseInt(multi.getParameter("input_price").trim());

		int output_price = Integer.parseInt(multi.getParameter("output_price").trim());

		int stock_qty = Integer.parseInt(multi.getParameter("stock_qty").trim());

		int sold_qty = Integer.parseInt(multi.getParameter("sold_qty").trim());

		String is_sold_out = multi.getParameter("is_sold_out").trim();

		String is_rental_available = multi.getParameter("is_rental_available").trim();

		int rental_unit_price = Integer.parseInt(multi.getParameter("rental_unit_price").trim());

		// getFilesystemName()
		// ==> 업로드될 파일의 이름을 문자열로 반환해 주는 메서드.
		String product_image = multi.getFilesystemName("p_image_new");

		if (product_image == null) {
			product_image = multi.getParameter("p_image_old");
		}

		int product_no = Integer.parseInt(multi.getParameter("product_no").trim());

		ProductDTO dto = new ProductDTO();

		dto.setProduct_no(product_no);
		dto.setCategory_no(category_no);
		dto.setProduct_name(product_name);
		dto.setInput_price(input_price);
		dto.setOutput_price(output_price);
		dto.setStock_qty(stock_qty);
		dto.setSold_qty(sold_qty);
		dto.setIs_sold_out(is_sold_out);
		dto.setIs_rent_available(is_rent_available);
		dto.setProduct_image(product_image);
		dto.setRental_unit_price(rental_unit_price);

		ProductDAO dao = ProductDAO.getInstance();

		int check = dao.updateProduct(dto);

		PrintWriter out = response.getWriter();

		if (check > 0) {
			out.println("<script>");
			out.println("alert('상품 정보 수정 성공!!!')");
			out.println("location.href='admin_product_list.go'");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('상품 정보 수정 실패~~~')");
			out.println("history.back()");
			out.println("</script>");
		}

		return null;
	}

}
