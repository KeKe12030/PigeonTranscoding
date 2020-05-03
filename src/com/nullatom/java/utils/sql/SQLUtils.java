package com.nullatom.java.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class SQLUtils {
	public static Connection sqlConnection = null;
	public SQLUtils(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	private static String getConfig(String paramName) {//����������Ҫ��ȡ������ǰ׺
		ResourceBundle resource = ResourceBundle.getBundle("sql");//config.properties ��src
		String value = "";
		try {
			value = resource.getString(paramName);  
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println
			("***��ȡ���ݿ������ļ���������***");
		}
		return value;
	}
	/**
	 * �������ݿ�����
	 * */
	public static void conSql() {
		try {
			if(sqlConnection!=null) {
				if(!sqlConnection.isClosed()) {
					sqlConnection.close();
				}
			}
			sqlConnection = DriverManager.getConnection(getConfig("address"),getConfig("userName"),getConfig("userPass"));

		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	/**
	 * ͨ�����������Vecotr����������insert���� ���������������������ݡ�
	 * */
	public static <T> void insert(Vector<String> tableNames,Vector<String> columns,Vector<T> datas,int num) {
		String sql = "INSERT INTO ? (?) VALUES (?)";
		PreparedStatement ps = null;
		try {
			ps = sqlConnection.prepareStatement(sql);
			for(int i =0;i<num;i++) {
				ps.setString(1,tableNames.get(i));
				ps.setString(2, columns.get(i));
				if(datas.get(i).getClass().equals(String.class)) {
					ps.setString(3,(String) datas.get(i));
				}else if(datas.get(i).getClass().equals(Integer.class)) {
					ps.setInt(3, (int) datas.get(i));
				}
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			if(null!=ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}

	}
	public static <T> void insert(String tableName,String column,T data) {
		Vector<String> tableNames = new Vector<String>();
		tableNames.add(tableName);
		Vector<String> columns = new Vector<String>();
		columns.add(column);
		Vector<T> datas = new Vector<T>();
		datas.add(data);
		insert(tableNames, columns, datas, 1);
	}
	public static int update(String sql) {
		Statement s = null;
		try {
			s = sqlConnection.createStatement();
			return s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return -1;
		}finally {
			if(s!=null) {
				try {
					s.close();
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 *���͡�WHERE�Ĳ����� 
	 * ͨ�������������select����
	 * ����Ҫ���ѵ�������
	 * ��Ŀ�������
	 * ���Ƿ�����WHERE��
	 * ���������WHERE ��WHERE�������ǣ���
	 * 
	 * Vector<TreeMap<String,TreeMap<String,Object>>>
	 * 
	 * ��Ҫ�����������ǣ�TreeMap����Ҫ�������������磺(sid,1)������TreeMap������ֵ�����ͣ��磺(Integer,100)
	 * 
	 * Vector<TreeMap<String,TreeMap<String,Object>> v = new Vector<TreeMap<String,TreeMap<String,Object>>();
	 * TreeMap<String,TreeMap<String,Object> tm1 = new TreeMap<String,TreeMap<String,Object>();
	 * TreeMap<String,Object> tm2 = new TreeMap<String,Object>();
	 * tm2.add("String","Ke_Ke");
	 * tm1.add("user_name",tm2);
	 * v.add(tm1);
	 * select("sid","server_list",true,tm1);
	 * 
	 * =====================
	 * ��δ�깤
	 * */
	public static void select(String searchColumn,String tableName,boolean isWhere,Vector<TreeMap<String,TreeMap<String,Object>>> where) {
		StringBuilder sql= new StringBuilder();
		PreparedStatement ps  = null;
		sql.append("SELECT ? FROM ?");
		if(isWhere) {
			if(where.size()>1) {
				int size = where.size();
				sql.append(" WHERE ");
				for(int i=0;i<where.size();i++) {
					sql.append("?=?");
				}
			}else if(where.size()==1) {
				sql.append(" WHERE ?=?");
			}
		}
		try {
			ps.setString(1, searchColumn);
			ps.setString(2, tableName);
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		try {
			ps = sqlConnection.prepareStatement(sql.toString());
			if(isWhere) {
				for(int i =0;i<where.size();i++) {
					TreeMap<String,TreeMap<String,Object>> tm1 = where.get(i);
					TreeSet<String> keys = (TreeSet<String>) tm1.keySet();//��ȡkey��set
					for(int j=0;j<tm1.size();j++) {
						TreeMap<String,Object> tm2 = tm1.get(j);
						TreeSet<String> types = (TreeSet<String>) tm2.keySet();//��ȡdata������set
						for(String type : types) {
							switch(type) {
								case "String":
//								ps.setString(i+3);
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			if(null!=ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * ֹͣ���ݿ�����
	 * */
	public static void conStop() {
		try {
			sqlConnection.close();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
