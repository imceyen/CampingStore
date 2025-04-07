package com.camping.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camping.action.Action;
import com.camping.action.ActionForward;
import com.camping.action.LoginAction;

public class FrontController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uri = request.getRequestURI();
        String path = request.getContextPath();
        String command = uri.substring(path.length());

        command = command.startsWith("/") ? command.substring(1) : command;
        command = command.trim();

        System.out.println("URI >>> " + uri);
        System.out.println("Path >>> " + path);
        System.out.println("Command >>> " + command);

        Action action = null;
        ActionForward forward = null;
        Properties prop = new Properties();

        // mapping.properties 파일 경로 (본인 환경에 맞게 조정)
        String propPath = "C:\\Users\\4Class_05\\git\\CampingStore\\CampingStore\\CampingWeb\\src\\main\\java\\com\\camping\\controller\\mapping.properties";

        FileInputStream fis = new FileInputStream(propPath);
        prop.load(fis);

        String value = prop.getProperty(command);
        System.out.println("Value >>> " + value);

        // 예외 방지: 매핑 값이 없을 경우
        if (value == null) {
            System.out.println("매핑 정보가 없습니다: " + command);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // "execute|..." 형식인지 확인
        if (value.startsWith("execute")) {
            StringTokenizer st = new StringTokenizer(value, "|");

            String url_1 = st.nextToken();  // "execute"
            String url_2 = st.nextToken();  // "com.camping.action.XXX"

            try {
                Class<?> url = Class.forName(url_2);
                Constructor<?> constructor = url.getConstructor();
                action = (Action) constructor.newInstance();
                forward = action.execute(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath(value);
        }

        // 예외 처리 전용 코드 (예외적 분기) → 사실 이건 맨 위로 올리거나 아예 mapping.properties에서 관리하는 게 더 깔끔함.
        if (command.equals("login.go")) {
            action = new LoginAction();
            forward = action.execute(request, response);
        }

        // 페이지 이동 처리
        if (forward != null) {
            if (forward.isRedirect()) {
                response.sendRedirect(forward.getPath());
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
                rd.forward(request, response);
            }
        }
    }
}
