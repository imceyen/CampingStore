package com.camping.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.camping.model.InquiryDAO;
import com.camping.model.InquiryDTO;

public class AdminInquiryAnswerEditAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    	 // 요청 파라미터 받기
        int inquiry_no = Integer.parseInt(request.getParameter("inquiry_no"));
        String answer_content = request.getParameter("answer_content");
        
        // 기존 답변을 불러오기 위해 DAO 호출
        InquiryDAO dao = InquiryDAO.getInstance();
        InquiryDTO dto = dao.getInquiryDetail(inquiry_no); // 기존 답변 데이터 불러오기
        
        // 기존 답변 내용도 set
        dto.setAnswer_content(answer_content); // 수정된 답변 내용만 새로 설정
        
        // 답변 수정 처리
        int result = dao.updateInquiryAnswerContent(dto);
        
        // 응답을 위한 PrintWriter 객체 생성
        PrintWriter out = response.getWriter();

        if (result > 0) {
            // 수정 성공 시
            out.println("<script>");
            out.println("alert('답변 내용이 성공적으로 수정되었습니다.');");
            out.println("location.href='admin_inquiry_view.go?inquiry_no=" + inquiry_no + "';");
            out.println("</script>");
        } else {
            // 수정 실패 시
            out.println("<script>");
            out.println("alert('답변 수정에 실패했습니다. 다시 시도해주세요.');");
            out.println("history.back();");
            out.println("</script>");
        }

        return null; // alert 후 null 반환, 페이지 리디렉션은 alert에서 처리
    }
}