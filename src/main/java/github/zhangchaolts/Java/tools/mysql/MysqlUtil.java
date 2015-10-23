package github.zhangchaolts.Java.tools.mysql;

import java.sql.*;

public class MysqlUtil {

	private static MysqlUtil instance = null;

	private MysqlUtil() {}

	public static MysqlUtil getInstance() {
		if (instance == null) {
			instance = new MysqlUtil();
		}
		return instance;
	}

	private static Connection connection = null;
	
	public Connection getConnection(String ip, String port, String database, String user, String password) throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=GBK";
		Class.forName(driver);
		connection = DriverManager.getConnection(url, user, password);
		if (!connection.isClosed())
			System.out.println("Succeeded connecting to the Database!");
		return connection;
	}
	
	public void closeConnection() throws Exception {
		connection.close();
	}

	public static void main(String[] args) {
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名game
		String url = "jdbc:mysql://10.14.136.96:3306/video_film?useUnicode=true&characterEncoding=GBK";
		// MySQL配置时的用户名
		String user = "chanpinyunying";
		// MySQL配置时的密码
		String password = "m6i1m2a3";
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			Connection conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
			String sql = "select starring from star_class_info limit 10;";
			// 结果集
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("starring"));
			}
			rs.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}