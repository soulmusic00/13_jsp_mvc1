<%@page import="step2_00_loginEx.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginPro</title>
</head>
<body>

	<% 
		
		request.setCharacterEncoding("utf-8");
	
		String id     = request.getParameter("id");
		String passwd = request.getParameter("passwd");
	
		boolean isValidMember = MemberDao.getInstance().login(id, passwd);
		
		if (isValidMember) {
			session.setAttribute("id", id);
	%>
			<script>
				alert("Logged In");
				location.href = "00_main.jsp";
			</script>
	<% 			
		}
		else {
	%>
			<script>
				alert("Check your Id or Password");
				history.go(-1);
			</script>
	<% 		
		}
	%>

</body>
</html>