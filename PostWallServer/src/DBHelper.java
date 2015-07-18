

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	//鏁版嵁搴撲俊鎭�
	public static final String url = "jdbc:mysql://127.0.0.1/postwall";//postwall涓烘暟鎹簱
	public static final String name = "com.mysql.jdbc.Driver";//椹卞姩
	public static final String user = "root";//鐢ㄦ埛鍚�
	public static final String password = "";//瀵嗙爜

	public Connection conn = null;
	public PreparedStatement pst = null;

	public DBHelper(String sql) {
		try {
			Class.forName(name);//鎸囧畾杩炴帴绫诲瀷
			conn = DriverManager.getConnection(url, user, password);//鑾峰彇杩炴帴
			pst = conn.prepareStatement(sql);//鍑嗗鎵ц璇彞
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
