// UtillDB.java

package model.com.myproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtillDB {
	
	public static Connection getCon(Connection con) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//con = DriverManager.getConnection("jdbc:mysql://127.0.0.1" ,"root" ,"0000");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "koreait", "0000");
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		} // try - catch
		return con;
	} // getCon() : Connection 객체를 DBCP 연결 후에 리턴
	
	public static void closeDB(Connection con, PreparedStatement ps, ResultSet rs) {
		if (rs != null) { try { rs.close(); } catch (Exception e) { } }
		if (ps != null) { try { ps.close(); } catch (Exception e) { } }
		if (con != null) { try { con.close(); } catch (Exception e) { } }
	}  // closeDB() : SELECT DB 연결 종료
	
	public static void closeDB(Connection con, PreparedStatement ps) {
		if (ps != null) { try { ps.close(); } catch (Exception e) { } }
		if (con != null) { try { con.close(); } catch (Exception e) { } }
	} // closeDB() : SELECT 외 DB 연결 종료
	
} // UtillDB