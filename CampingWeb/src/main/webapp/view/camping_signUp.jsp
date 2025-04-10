<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>회원가입</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: #fff;
            padding: 40px;
        }

        h1 {
            text-align: center;
            margin-bottom: 40px;
        }

        form {
            max-width: 500px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"],
        input[type="date"],
        input[type="tel"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 25px;
            border: none;
            border-bottom: 1px solid #333;
            font-size: 14px;
        }

        .sub-text {
            font-size: 12px;
            color: #777;
            margin-top: -20px;
            margin-bottom: 10px;
        }

        .error-text {
            color: #ff4d4d;
            font-size: 12px;
            margin-top: -15px;
            margin-bottom: 15px;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: black;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #333;
        }
    </style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="bg-white font-sans text-black">

    <!-- 로고 -->
    <header class="text-center py-6 border-b">
        <h1 class="text-3xl font-bold"><span class="text-black">PacknGo</span></h1>
    </header>
    
    <!-- 회원가입 메인 창 -->
    <h1>회원가입</h1>
    <form method="post" action="<%=request.getContextPath() %>/camping_signUp_ok.go">

        <label for="id">아이디</label>
        <input type="text" id="cus_id" name="cus_id" required>

        <label for="password">비밀번호</label>
        <input type="password" id="cus_pwd" name="cus_pwd" required>

        <label for="confirmPassword">비밀번호 확인</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>
        
        <label for="name">이름</label>
        <input type="text" id="cus_name" name="cus_name" required>

        <label for="birth">생년월일</label>
        <input type="text" id="cus_birth" name="cus_birth" placeholder="9999-12-31" required>

        <label for="gender">성별</label>
        <input type="text" id="cus_gender" name="cus_gender" maxlength="1" placeholder="M 또는 F를 입력해주세요" required>

        <label for="phone">연락처</label>
        <input type="tel" id="cus_phone" name="cus_phone" placeholder="010-1234-5678" required>

        <label for="address">주소</label>
        <input type="text" id="cus_addr" name="cus_addr" required>

        <button type="submit">가입하기</button>
    </form>





</body>
</html>