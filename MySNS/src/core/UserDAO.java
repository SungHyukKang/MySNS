package core;

import java.sql.*;

import org.json.simple.JSONObject;

import util.ConnectionPool;

public class UserDAO {
	
	public String signup(String uid , String ps,String name,String nick)throws Exception{
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		String sql =null;
		String code=null;
		try {
			conn = ConnectionPool.getInstance().getConn();
			sql = "select id from user where id =?";
			st = conn.prepareStatement(sql);
			st.setString(1, uid);
			rs = st.executeQuery();
			if(rs.next()) {
				code ="EX";
			}else {
				st.close();
			sql ="Insert into user(id,password,name,nick) values(?,?,?,?)";
			st=conn.prepareStatement(sql);
			st.setString(1, uid);
			st.setString(2, ps);
			st.setString(3, name);
			st.setString(4, nick);
			int X = st.executeUpdate();
			if(X==1) {
				code="OK";
				return code;
			}else
				code ="ER";
			}
		}catch(Exception e) {
			e.printStackTrace();
			code="ER";
			return code;
		}finally {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		}
		return code;
		}
	@SuppressWarnings("unchecked")
	public String login(String uid , String upass)throws Exception {
		String code=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs =null;
		try {
		conn = ConnectionPool.getInstance().getConn();
		
		String sql ="Select * from user where id = ?";
		st = conn.prepareStatement(sql);
		st.setString(1, uid);
		rs = st.executeQuery();
		if(!rs.next()) {
			code="NA";
		}else if(!rs.getString("password").equals(upass)) {
			code="PS";
		}else {
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("id",rs.getString("id"));
			jsonobj.put("name",rs.getString("name"));
			code=jsonobj.toJSONString();
			}
		}finally {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		}
		return code;
	}
}
