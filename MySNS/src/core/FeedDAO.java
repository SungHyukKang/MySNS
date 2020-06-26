package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import util.ConnectionPool;

public class FeedDAO {
	public String insert(String uid , String feed)throws Exception{
		Connection conn =null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql =null;
		try {
			 
			conn = ConnectionPool.getInstance().getConn();
			synchronized(this) {
			sql = "Select auto_increment from information_schema.tables ";
			sql +="Where table_schema = database() and table_name = 'feed'";
			st=conn.prepareStatement(sql);
			rs= st.executeQuery();
			if(!rs.next()) return "ER";
			int feedNo = rs.getInt(1);
			JSONObject jsonobj = (JSONObject)(new JSONParser().parse(feed));
			jsonobj.put("no",feedNo);
			System.out.println(jsonobj);
			System.out.println(feedNo);
			sql ="Insert into feed(id,content) values(?,?)";
			st.close();
			st=conn.prepareStatement(sql);
			st.setString(1, uid);
			st.setString(2, jsonobj.toJSONString());
			int cnt = st.executeUpdate();
			return (cnt == 0) ? "ER" : "OK";
			}
		}finally {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn !=null) conn.close();
		}
		}
	public String fetch(String uid )throws Exception {
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs =null;
		try {
		conn = ConnectionPool.getInstance().getConn();
		String sql ="Select content from feed where id = ? ORDER BY ts DESC LIMIT 20";
		st = conn.prepareStatement(sql);
		st.setString(1, uid);
		String res ="[";
		int cnt=0;
		rs = st.executeQuery();
		while(rs.next()){
			if(cnt++>0)res+=",";
			res +=rs.getString("content");
		}
		res+="]";
		System.out.println(res);
		return res;
		}finally {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		}
	}
	public String delete(String no) throws Exception{
		Connection conn =null;
		PreparedStatement st = null;
		String sql =null;
		try {
			conn = ConnectionPool.getInstance().getConn();
			sql ="Delete from feed where no=?";
			st=conn.prepareStatement(sql);
			st.setString(1, no);
			int cnt = st.executeUpdate();
			return (cnt == 0) ? "ER" : "OK";
			}
		finally {
			if(st!=null) st.close();
			if(conn !=null) conn.close();
		}
		}
	public String update(String no,String feed) throws Exception{
		Connection conn =null;
		PreparedStatement st = null;
		String sql =null;
		try {
			conn=ConnectionPool.getInstance().getConn();
			JSONObject jsonobj = (JSONObject)(new JSONParser().parse(feed));
			jsonobj.put("no",Integer.parseInt(no));
			sql ="Update feed SET content = ? , ts=now() where no="+no;
			st=conn.prepareStatement(sql);
			st.setString(1, jsonobj.toJSONString());
			int cnt = st.executeUpdate();
			return (cnt == 0) ? "ER" : "OK";
		}
		finally {
			if(st!=null) st.close();
			if(conn !=null) conn.close();
		}
		}
	
}
