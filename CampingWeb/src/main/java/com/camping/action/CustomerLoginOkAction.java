package com.camping.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camping.model.AdminDAO;
import com.camping.model.CustomerDAO;

public class CustomerLoginOkAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("user_id");
        String pwd = request.getParameter("user_pwd");

        // 1. 관리자 로그인 체크
        AdminDAO adminDao = AdminDAO.getInstance();
        int adminResult = adminDao.adminCheck(id, pwd);

        if (adminResult == 1) {
            // 관리자 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("admin_id", id);
            ActionForward forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("view/admin_main.jsp");
            return forward;

        } else if (adminResult == -1) {
            // 관리자 비밀번호 틀림
            request.setAttribute("error", "관리자 비밀번호가 틀렸습니다.");
            ActionForward forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("view/camping_login.jsp");
            return forward;
        }

        // 2. 일반 회원 로그인 체크
        CustomerDAO customerDao = CustomerDAO.getInstance();
        int result = customerDao.customerLogin(id, pwd);

        if (result == 1) {
            // 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("cus_id", id);
            ActionForward forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("main.jsp");
            return forward;

        } else {
            // 로그인 실패
            request.setAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            ActionForward forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("view/camping_login.jsp");
            return forward;
        }
    }
}
