<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deletePro</title>
</head>
<body>

	<% 
	
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/login_ex?serverTimezone=UTC";	
			String user = "root";		
			String password = "1234";   
			
			conn = DriverManager.getConnection(url, user, password);
			
	
			request.setCharacterEncoding("utf-8");
			
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID=? AND PASSWD=?");
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				pstmt = conn.prepareStatement("DELETE FROM MEMBER WHERE ID = ?");
				pstmt.setString(1, id);
				pstmt.executeUpdate();
		%>	
				<script>
					alert("회원 탈퇴 되었습니다.");
					location.href = "00_main.jsp";
				</script>		
		<% 	
			}
			else {
		%>
				<script>
					alert("아이디와 비밀번호를 확인하세요.");
					
					/* 
					
						# 한페이지 이전으로 이동하는 자바스크립트 메서드
						history.back();
						history.go(-1);
					
						+@)
						history.forward(); 한 페이지 앞으로 이동
						history.go(-2);	   두 페이지 이전으로 이동
						history.go(-3);    세 페이지 이전으로 이동
						
					*/ 
					
					history.go(-1);
				</script>
		<% 	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    try {rs.close();}    catch (Exception e) {e.printStackTrace();}
			if (pstmt != null) try {pstmt.close();} catch (Exception e) {e.printStackTrace();}
			if (conn != null)  try {conn.close();}  catch (Exception e) {e.printStackTrace();}
		}
		
	%>

</body>
</html>