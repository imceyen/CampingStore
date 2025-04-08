<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	
	   <hr width="65%" color="marmoon">
	      <h3>SHOP_PRODUCTS 테이블 상품 수정 폼 페이지</h3>
	   <hr width="65%" color="marmoon">
	   <br> <br>
	   
	   <form method="post" enctype="multipart/form-data"
	        action="<%=request.getContextPath() %>/admin_product_modify_ok.go">
	      
	      <c:set var="dto" value="${ProductCont }" />
	      
	      <input type="hidden" name="product_no" value="${dto.getProduct_no() }">
	      
	      <table border="1" width="500">
	         <tr>
	            <th>카테고리 번호</th>
	            <td>
	               <input name="category_no" readonly
	               		value="${dto.getCategory_no() }">
	            </td>
	         </tr>
	         	         <tr>
	            <th>상품명</th>
	            <td>
	               <input name="product_name" 
	               		value="${dto.getProduct_name() }">
	            </td>
	         </tr>
	         <tr>
	            <th>입고가</th>
	            <td>
	               <input name="input_price" readonly
	               		value="${dto.getInput_price() }">
	            </td>
	         </tr>
	         <tr>
	            <th>출고가</th>
	            <td>
	               <input name="output_price" 
	               		value="${dto.getOutput_price() }">
	            </td>
	         </tr>
	         <tr>
	            <th>재고수량</th>
	            <td>
	               <input name="stock_qty" 
	               		value="${dto.getStock_qty() }">
	            </td>
	         </tr>
	         <tr>
	            <th>품절여부</th>
	            <td>
	               <input name="is_sold_out" 
	               		value="${dto.getIs_sold_out() }">
	            </td>
	         </tr>
	         <tr>
	            <th>렌탈금액</th>
	            <td>
	               <input name="rental_unit_price" 
	               		value="${dto.getRental_unit_price() }">
	            </td>
	         </tr>
	         <tr>
	            <th>렌탈가능여부</th>
	            <td>
	               <input name="is_rent_available" 
	               		value="${dto.getIs_rent_available() }">
	            </td>
	         </tr>
	         
	         
	         <tr>
	            <th>상품 이미지</th>
	            <td>
	               <img src="<%=request.getContextPath() %>/upload/${dto.getProduct_image() }"
	               			width="100" height="80">
	               			
	               <input type="file" name="p_image_new">
	               
	               <%-- 이미지를 수정하지 않고 그대로 제품수정(submit) 버튼을
	                    누르면 삼품 등록 시 입력했던 상픔의 이미지를 그대로
	                    사용하여 넘겨줄 예정. --%>
	               <input type="hidden" name="p_image_old"
	               			value="${dto.getProduct_image() }">
	            </td>
	         
	         <tr>
	            <td colspan="2" align="center">
	               <input type="submit" value="상품수정">&nbsp;&nbsp;
	               <input type="reset" value="다시작성">
	            </td>
	         </tr>
	         
	      </table>
	   
	   </form>
	
	
</body>
</html>