<%@page import="step3_00_boardEx.BoardDao"%>
<%@page import="step3_00_boardEx.BoardDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>04_bList</title>
</head>
<body>
	<h1>게시글 보기</h1>
	<table border="1">
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>
	<%
		ArrayList<BoardDto> boardList = BoardDao.getInstance().getAllBoard(); 
		for (BoardDto boardDto : boardList){
	%>
			<tr>
				<td><%=boardDto.getNum() %></td>
				<td><a href="05_bInfo.jsp?num=<%= boardDto.getNum() %>"><%= boardDto.getSubject() %></a></td>
				<td><%= boardDto.getWriter() %></td>
				<td><%= boardDto.getRegDate() %></td>
				<td><%= boardDto.getReadCount() %></td>
			</tr>
	<%
		}
	%>
		<tr>
			<td colspan="5">
				<input type="button" value="글쓰기" onclick="location.href='02_bWrite.jsp'">
			</td>
		</tr>
	</table>
</html>