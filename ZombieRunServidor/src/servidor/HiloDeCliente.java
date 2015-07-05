package servidor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import comunicacion.*;

public class HiloDeCliente extends Thread{

	// SOCKET PARA COMUNICARSE CON EL CLIENTE QUE SE CONECTO
	private Socket clientSocket;
	// CLASES PARA ESCRIBIR Y LEER, NECESARIAS PARA LA COMINICACION
	private ObjectInputStream in;
	private ObjectOutputStream out;
	//ARRAYLIST DE LOS USUARIOS
	private ArrayList<Socket> usuarios;
	// PARTIDAS EN PROCESO
	private Partida partida; // DEMO

	private Connection conn;
	
	public HiloDeCliente( Socket s, ArrayList<Socket> u, Partida p , Connection c ){
		clientSocket = s;
		setUsuarios(u);
		partida = p; // DEMO
		conn = c;
		try {
			// VINCULAMOS LOS INPUT Y OUTPUT CON LOS DEL CLIENTE( ESTOS LOS TIENE EL SOCKET )
			in = new ObjectInputStream( clientSocket.getInputStream() );
			out = new ObjectOutputStream( clientSocket.getOutputStream() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	// ESTA FUNCION SE EJECUTA UNA VEZ QUE EL SERVIDOR LE MANDA EL START AL HILO
	// ESTA SE ENCARGA DE ESCUCHAR CONSTANTEMENTE AL SOCKET DE CLIENTE
	@Override
	public void run(){
		try {
	
			while(true){
				//RECIBIMOS EL OBJETO
				Object peticion = in.readObject();
				System.out.println(" Se recibio un objeto " + peticion.getClass().getName());
				
				// REEMPLAZAR POR UN SWITCH
				if( peticion instanceof IngresarPartida ){
					partida.agregarJugador( new Jugador(clientSocket,out,in)); // DUDO QUE ESTE BIEN
					System.out.println("SE AGREGO EL JUGADOR " + clientSocket.getInetAddress());
					out.writeObject("AGREGADO");
					this.finalize();
					in.wait();
					
					//this.finalize(); // DUDO QUE ESTE BIEN
					// PERO SERIA UNA FORMA DE DETENER ESTE HILO PARA QUE LO
					
				}else if( peticion instanceof LoginBean ){
					System.out.println("login");
					if(loguear((LoginBean) peticion))
						out.writeObject("INGRESADO");
					else
						out.writeObject("ERROR LOGIN");
				}else if( peticion instanceof Peticion ){
					System.out.println("Peticion");
					out.writeObject("INGRESADO");
				}else if( peticion instanceof RegistrarBean ){
					System.out.println("Registro");
					agregarUsuario((RegistrarBean) peticion);
					out.writeObject("te registraste");
				}else if( peticion instanceof DesconexionBean ){
					System.out.println("Usuario Desconectado");
					desconectarUsuario();
					out.writeObject("DESCONECTADO");
				}else if( peticion.equals("KILL THREAD") ){
					System.out.println("Eliminando hilo de cliente");					
					out.writeObject("HILO ELIMINADO");
					break;//eliminamos bucle infinito
				}else{
					System.out.println("no se reconoce al objeto.");
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
	//////////////METODOS PARA EL MANEJO DE BASE DE DATOS////////

	private void desconectarUsuario() {
		//QUITAMOS AL USURIO DE LA LISTA DE USUARIOS CONECTADOS Y DEJAMOS QUE EL CLIENTE CIERRE LA
		//LA CONEXION
		usuarios.remove(clientSocket);		
	}

	private boolean loguear(LoginBean login) {
		boolean loginSuccess = false;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(" select * from usuario where nick = ? and password = ?");
			pstmt.setString(1, login.getUser());
			pstmt.setString(2, new String(login.getPassword()));
			pstmt.execute();
			//System.out.println(pstmt.toString());
			ResultSet rs = pstmt.getResultSet();
			loginSuccess = rs.next();
				
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
				return loginSuccess;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return loginSuccess;
	}

	public void agregarUsuario(RegistrarBean registro) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("Insert into Usuario values(null, ?, ?, ?, ?)");
			pstmt.setString(1, registro.getNick());
			pstmt.setString(2, new String(registro.getPassword()));
			pstmt.setString(3, registro.getPregunta());
			pstmt.setString(4, registro.getRespuesta());
			pstmt.execute();			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public ArrayList<Socket> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Socket> usuarios) {
		this.usuarios = usuarios;
	}
	
}
