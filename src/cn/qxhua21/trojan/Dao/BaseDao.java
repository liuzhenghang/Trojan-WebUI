package cn.qxhua21.trojan.Dao;

import java.sql.*;

public class BaseDao {
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String U_PASS = "密码";
	private static final String U_NAME = "用户名";
	private static final String URL="jdbc:mysql://地址:端口/数据库?useUnicode=true&characterEncoding=utf8";
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public void getConncetion()
	{
		try {
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(URL, U_NAME, U_PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet query(String sql, Object... param)
	{
		getConncetion();
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0;i<param.length;i++)
			{
				ps.setObject(i+1, param[i]);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public int update(String sql, Object... param)
	{
		int num = 0;
		getConncetion();
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0;i<param.length;i++)
			{
				ps.setObject(i+1, param[i]);
			}
			num = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return num;
	}
	
	public void closeConnection()
	{
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
