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
				frame.mostrarMensajeFrame("Se realiz� la conexi�n con �xito");
			}
			else{
				frame.mostrarMensajeFrame("La conexi�n se encuentra realizada.");
			}
		} catch (ClassNotFoundException cnfe) {
			frame.mostrarMensajeFrame("No se encuentra el Driver.");
		} catch (SQLException sqle) {
			frame.mostrarMensajeFrame("Error al intentar la conexi�n.");
		}	
		return conn;
	}
	
	public static void close() {
		try {
			if(conn != null) {
				conn.close();
				frame.mostrarMensajeFrame("Desconexi�n de la BD exitosa.");
			}
		} catch (SQLException sqle) {
			frame.mostrarMensajeFrame("Error al intentar la conexi�n.");
		}
	}

	public static void setFrame(ServidorFrame frameserver) {
		frame = frameserver;		
	}
}
