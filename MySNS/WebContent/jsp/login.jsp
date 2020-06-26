<%@page import="core.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%request.setCharacterEncoding("UTF-8");
	String usrobj=(String)session.getAttribute("usrobj"); 
	if(usrobj!=null){
		out.print("EX");
		return;
	}
    String uid = request.getParameter("id");
    String upass = request.getParameter("ps");
    UserDAO dao = new UserDAO();
    String code=dao.login(uid,upass);
    if(code!="NA" && code!="PS"){
    	session.setAttribute("usrobj",code);
    	out.print("OK");
    	out.print(code);
    }else{
    	out.print("NA");
    }
    %>