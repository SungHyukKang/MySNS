<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="core.*" %>    
   
<%
request.setCharacterEncoding("UTF-8");
FeedDAO dao = new FeedDAO();

out.print(dao.insert(request.getParameter("id"),request.getParameter("feed")));
%>