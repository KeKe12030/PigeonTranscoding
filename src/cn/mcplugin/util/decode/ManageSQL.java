package cn.mcplugin.util.decode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.nullatom.java.utils.sql.SQLUtils;

public class ManageSQL extends SQLUtils{
	public static ArrayList<String> getStrListBySelect(String sql) {
		PreparedStatement s = null;
		ResultSet rs = null;
		ArrayList<String> arr = new ArrayList<String>();
		try {
			s = sqlConnection.prepareStatement(sql);
			rs = s.executeQuery(sql);
			while(rs.next()) {
				arr.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				if(s!=null) {
					s.close();
				}
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return arr;
	}
	public static int updatePS(String sql,String...datas) {
		PreparedStatement s = null;
		try {
			s = sqlConnection.prepareStatement(sql);
			for(int i=0;i<datas.length;i++) {
				s.setString(i+1, datas[i]);
			}
			return s.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return -1;
		}finally {
			if(s!=null) {
				try {
					s.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
}
