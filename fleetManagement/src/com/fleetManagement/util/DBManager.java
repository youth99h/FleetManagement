package com.fleetManagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DBManager
{

	private Connection con;
	private Statement stm;
	private ResultSet rs;

	public DBManager()
	{
		try {
			// 1.��������
			String driverName = "com.mysql.cj.jdbc.Driver"; // mysql
			System.out.println("---"+driverName);	// jdbc����������,ʵ���Ͼ���driver���ڰ��е�����·��
			Class.forName(driverName);  

			// 2.���������ݿ������
			String url = "jdbc:mysql://localhost:3306/fleetManagement?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false"; // ���ݿ������ַ�����һ��ʹ��ͳһ��Դ��λ����url������ʽ

			String user = "root"; // �������ݿ�ʱ���û�
			String password = "hbw12178"; // �������ݿ�ʱ������
			System.out.println("user="+user+" password="+password);
			con = DriverManager.getConnection(url, user, password);
			
/*			Context ctx=new InitialContext();
			DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/studentmanager");//java:comp/env/Ϊǰ׺,jdbc/studentmanagerΪ����
			con=ds.getConnection();*/
			//3.����������
			stm = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public ResultSet executeQuery(String sql)
		throws Exception
	{
		stm = con.createStatement(2004, 1007);
		rs = stm.executeQuery(sql);
		return rs;
	}

	public int executeUpdate(String sql)
		throws Exception
	{
		int ret = 0;
		stm = con.createStatement();
		ret = stm.executeUpdate(sql);
		return ret;
	}


	
	
	

	public void close()
		throws Exception
	{
		if (rs != null)
			rs.close();
		if (stm != null)
			stm.close();
		if (con != null)
			con.close();
	}

	
}