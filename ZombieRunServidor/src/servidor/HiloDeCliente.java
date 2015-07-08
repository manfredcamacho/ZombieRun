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
	
	private ServidorFrame frame;
	
	private String ipCliente;
	
	public HiloDeCliente( Socket s, ArrayList<Socket> u, Partida p , Connection c, ServidorFrame frame){
		clientSocket = s;
		this.frame = frame;
		setUsuarios(u);
		partida = p; // DEMO
		conn = c;
		try {
			// VINCULAMOS LOS INPUT Y OUTPUT CON LOS DEL CLIENTE( ESTOS LOS TIENE EL SOCKET )
			in = new ObjectInputStream( clientSocket.getInputStream() );
			out = new ObjectOutputStream( clientSocket.getOutputStream() );
			ipCliente = clientSocket.getInetAddress().getHostAddress();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// ESTA FUNCION SE EJECUTA UNA VEZ QUE EL SERVIDOR LE MANDA EL START AL HILO
	// ESTA SE ENCARGA DE ESCUCHAR CONSTANTEMENTE AL SOCKET DE CLIENTE
	@Override
	public void run(){
		try {
			String respuesta;
			while(true){
				//RECIBIMOS EL OBJETO
				Object peticion = in.readObject();
				System.out.println(" Se recibio un objeto " + peticion.getClass().getName());
				
				// REEMPLAZAR POR UN SWITCH
				if( peticion instanceof IngresarPartida ){
					partida.agregarJugador( new Jugador(clientSocket,out,in));
					frame.mostrarMensajeFrame("SE AGREGO EL JUGADOR " + clientSocket.getInetAddress());
					//this.finalize();
					// PERO SERIA UNA FORMA DE DETENER ESTE HILO PARA QUE LO
					
				}else if( peticion instanceof LoginBean ){
					if(loguear((LoginBean) peticion)){
						frame.mostrarMensajeFrame(ipCliente+">> Login");
						out.writeObject("INGRESADO");
					}else
						out.writeObject("ERROR LOGIN");
				}else if( peticion instanceof Peticion ){
					frame.mostrarMensajeFrame(ipCliente+">> Peticion");
					out.writeObject("INGRESADO");
				}else if( peticion instanceof RegistrarBean ){
					if(agregarUsuario((RegistrarBean) peticion)){
						frame.mostrarMensajeFrame(ipCliente+">> Registro");
						out.writeObject("REGISTRO");
					}else
						out.writeObject("ERROR REGISTRO");
				}else if( peticion instanceof DesconexionBean ){
					desconectarUsuario();
					frame.mostrarMensajeFrame(ipCliente+">> Logout");
					out.writeObject("DESCONECTADO");
				}else if( peticion.equals("KILL THREAD") ){
					frame.mostrarMensajeFrame(ipCliente+">> Eliminando hilo de cliente");					
					out.writeObject("HILO ELIMINADO");
					break;//eliminamos bucle infinito
				}else if( peticion instanceof RecuperarBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de Pregunta secreta.");
					respuesta = devolverPreguntaSecreta((RecuperarBean) peticion);
					if(respuesta == null)
						out.writeObject("NICK INVALIDO");
					else
						out.writeObject(respuesta);
				}else if( peticion instanceof ValidarRespuestaBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de validar respuesta");					
					respuesta = validarRespuesta((ValidarRespuestaBean) peticion); 
					if(respuesta == null)
						out.writeObject("RESPUESTA INVALIDA");
					else
						out.writeObject(respuesta);
				}else if( peticion instanceof estoyListoBean ){
					System.out.println(((estoyListoBean) peticion).getId()-1);
					partida.getJugadores().get( ((estoyListoBean) peticion).getId()-1).setEstoyListo(true);
				}else if( peticion instanceof DireccionBean ){
						partida.getJugadores().get( ((DireccionBean) peticion).getId()-1).setEnvieDireccion(true);
						partida.getJugadores().get( ((DireccionBean) peticion).getId()-1).setDireccion(((DireccionBean) peticion).getDireccion());
				}else{
					frame.mostrarMensajeFrame("no se reconoce al objeto.");
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
	//////////////METODOS PARA EL MANEJO DE BASE DE DATOS////////

	private String validarRespuesta(ValidarRespuestaBean peticion) {
		String respuestaSecreta = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(" select password from usuario where nick = ? and respuestaSecreta = ?");
			pstmt.setString(1, peticion.getNick());
			pstmt.setString(2, peticion.getRespuesta());
			pstmt.execute();
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.getResultSet();
			while(rs.next()){
				respuestaSecreta = rs.getString(1);
			}
				
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return respuestaSecreta;
	}

	private String devolverPreguntaSecreta(RecuperarBean peticion) {
		String preguntaSecreta = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("select preguntaSecreta from usuario where nick = ?");
			pstmt.setString(1, peticion.getNick());
			pstmt.execute();
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.getResultSet();
			while(rs.next()){
				preguntaSecreta = rs.getString(1);
			}
				
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return preguntaSecreta;
	}

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

	public boolean agregarUsuario(RegistrarBean registro) {
		PreparedStatement pstmt = null;
		try {
			//verificamos que no exista el mismo usuario
			pstmt = conn.prepareStatement("select 1 from usuario where nick=?");
			pstmt.setString(1, registro.getNick());
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();
			
			if(!rs.next()){//si no hay otro usurio con el mismo nick insertamos
				pstmt.close();
				pstmt = conn.prepareStatement("Insert into Usuario values(null, ?, ?, ?, ?)");
				pstmt.setString(1, registro.getNick());
				pstmt.setString(2, new String(registro.getPassword()));
				pstmt.setString(3, registro.getPregunta());
				pstmt.setString(4, registro.getRespuesta());
				pstmt.execute();
				return true;
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public ArrayList<Socket> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Socket> usuarios) {
		this.usuarios = usuarios;
	}
	
}
