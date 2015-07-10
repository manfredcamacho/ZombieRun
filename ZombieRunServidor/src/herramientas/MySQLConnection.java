package herramientas;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import servidor.ServidorFrame;

public class MySQLConnection {

	private static Connection conn;
	private static ServidorFrame frame;
	
	private MySQLConnection() {
	}
	
	public static Connection getConnection() {
		try {
			if(conn == null) {	
				String driver="com.mysql.jdbc.Driver"; //el driver varia segun la BD que usemos
				String url="jdbc:mysql://sql3.freesqldatabase.com:3306/sql383231";
				String pwd="iH2*xV3!";
				String usr="sql383231";
				Class.forName(driver);
				conn = DriverManager.getConnection(url,usr,pwd);
				frame.mostrarMensajeFrame("Se realizó la conexión con éxito");
			}
			else{
				frame.mostrarMensajeFrame("La conexión se encuentra realizada.");
			}
		} catch (ClassNotFoundException cnfe) {
			frame.mostrarMensajeFrame("No se encuentra el Driver.");
		} catch (SQLException sqle) {
			frame.mostrarMensajeFrame("Error al intentar la conexión.");
		}	
		return conn;
	}
	
	public static void close() {
		try {
			if(conn != null) {
				conn.close();
				frame.mostrarMensajeFrame("Desconexión de la BD exitosa.");
			}
		} catch (SQLException sqle) {
			frame.mostrarMensajeFrame("Error al intentar la conexión.");
		}
	}

	public static void setFrame(ServidorFrame frameserver) {
		frame = frameserver;		
	}
}
