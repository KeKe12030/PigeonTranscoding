package cn.mcplugin.util.decode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WINToUTF {
	public static String getApiText(String url,Map<String,String> map) {
		int i =0;
		while(i<3) {
			i++;
			//获取请求连接
			Connection con = Jsoup.connect(url);
			//遍历生成参数
			if(map!=null){
				for (Entry<String, String> entry : map.entrySet()) {     
					//添加参数
					con.data(entry.getKey(), entry.getValue());
				} 
			}
			//插入cookie（头文件形式）
			Document doc = null;
			try {
				doc = con.post();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				if(i==2) {
					System.out.println("===转换接口出错了===");
					e.printStackTrace();
				}
				
				continue;
			}  
			return con==null? null:doc.text().toString();
		}
		return null;
	}
	public static String getDecodeString(String badStr) {
		badStr = badStr.replaceAll("\n", "<br/>").replaceAll("\r\n", "<br />");
		Map<String,String> datas = new TreeMap<String,String>();
		datas.put("myAction", "autoRecover");
		datas.put("inputStr", badStr);
		String str = "windows-1252 UTF-8 (.*?) GBK iso-8859-1";
		String info = getApiText("http://www.mytju.com/classCode/tools/messyCodeRecover.asp",datas);
		Pattern r = Pattern.compile(str);
		Matcher m = r.matcher(info);
		if (m.find()) {
			return m.group(1);
		}
		return badStr;
	}
	public static void main(String[] args) {
		//		System.out.println(getDecodeString("è‚è‚è‚è‚è‚ï¼Œï¼Œè‚¾ç–¼ðŸ’”\r\n" + 
		//				"<img src=\"https://pic.downk.cc/item/5e720f1be83c3a1e3afe8ebc.png\">"));
		ManageSQL.conSql();
		System.out.println("已经连接完毕");
		ArrayList<String> arr = ManageSQL.getStrListBySelect("SELECT `content` FROM `posts` WHERE id!=103 AND id!=104 AND id!=105 AND id>50");
		int i =0;
		for(String str : arr) {
			i++;
			System.out.println("=======================");
			System.out.println("第"+i+"号正在处理！！\n正在处理"+str);
			String str1 = getDecodeString(str);
			System.out.println("已经处理完毕，内容为"+str1+"，\n正在存入数据库");
			ManageSQL.updatePS("UPDATE `posts` SET `content`=? WHERE content=?;",str1,str);
			System.out.println("已经存入数据库！");
		}
		ManageSQL.conStop();
	}

}
