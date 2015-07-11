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
	private ArrayList<Partida> partidas; // DEMO

	private Connection conn;
	
	private ServidorFrame frame;
	
	private String ipCliente;
	
	private int idUsuario;
	
	public HiloDeCliente( Socket s, ArrayList<Socket> u, ArrayList<Partida> p , Connection c, ServidorFrame frame){
		clientSocket = s;
		this.frame = frame;
		setUsuarios(u);
		partidas = p; // DEMO
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
					buscarYagregar( (IngresarPartida)peticion );
					actualizarEspera((IngresarPartida)peticion );
					//partida.agregarJugador( new Jugador(clientSocket,out,in));
					frame.mostrarMensajeFrame("SE AGREGO EL JUGADOR " + clientSocket.getInetAddress());
					//this.finalize();
					// PERO SERIA UNA FORMA DE DETENER ESTE HILO PARA QUE LO
					
				}else if( peticion instanceof LoginBean ){
					if(loguear((LoginBean) peticion)){
						frame.mostrarMensajeFrame(ipCliente+">> Login");
						((LoginBean)peticion).setIdUsuario(idUsuario);
						out.writeObject(peticion);
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
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de pregunta secreta.");
					respuesta = devolverPreguntaSecreta((RecuperarBean) peticion);
					if(respuesta == null)
						out.writeObject("NICK INVALIDO");
					else
						out.writeObject(respuesta);
				}else if( peticion instanceof DatosUsuarioBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de datos del cliente.");
					devolverDatosCliente((DatosUsuarioBean) peticion);
					out.writeObject(peticion);
				}else if( peticion instanceof ValidarRespuestaBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de validar respuesta");					
					respuesta = validarRespuesta((ValidarRespuestaBean) peticion); 
					if(respuesta == null)
						out.writeObject("RESPUESTA INVALIDA");
					else
						out.writeObject(respuesta);
				}else if( peticion instanceof ExisteUsuarioBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de existencia de usuario.");					
					respuesta = existeUsuario((ExisteUsuarioBean) peticion); 
					out.writeObject(respuesta);
				}else if( peticion instanceof ActualizarDatosBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de actualizacion de datos.");					
					actualizarDatos((ActualizarDatosBean) peticion); 
					out.writeObject("DATOS ACTUALIZADOS");
				}else if( peticion instanceof estoyListoBean ){
					System.out.println(((estoyListoBean) peticion).getId()-1);
					partidas.get(((estoyListoBean)peticion).getIdPartida()).getJugadores().get( ((estoyListoBean) peticion).getId()-1).setEstoyListo(true);
				}else if( peticion instanceof DireccionBean ){
						partidas.get(((DireccionBean)peticion).getIdPartida()).getJugadores().get( ((DireccionBean) peticion).getId()-1).setDireccion(((DireccionBean) peticion).getDireccion());
						partidas.get(((DireccionBean)peticion).getIdPartida()).getJugadores().get( ((DireccionBean) peticion).getId()-1).setEnvieDireccion(true);
				}else if( peticion instanceof RankingBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de ranking.");
					out.writeObject(retornarRanking((RankingBean)peticion));
				}else if( peticion instanceof EstadisticasBean ){
					frame.mostrarMensajeFrame(ipCliente+">> Solicitud de estadisticas.");
					out.writeObject(retornarEstadisticas((EstadisticasBean)peticion));
				}else if( peticion instanceof ActualizarPartidasBean ){
						enviarPartidas((ActualizarPartidasBean)peticion);
						System.out.println("Se envio la actualizacion");
				}else{
					frame.mostrarMensajeFrame("no se reconoce al objeto.");
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void actualizarEspera( IngresarPartida obj ) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> jugadores = new ArrayList<String>();
		int cont = 0;
		for (Jugador jugador : partidas.get(obj.getId()).getJugadores()) {
			jugadores.add(jugador.getNick());
			cont++;
		}
		for (Jugador jugador : partidas.get(obj.getId()).getJugadores()) {
			jugador.getOut().writeObject( new ActualizarEsperaBean(cont, jugadores));
			jugador.getOut().flush();
		}
	}
	
	// NO SE SI ANDA BIEN
	private void buscarYagregar(IngresarPartida peticion) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		partidas.get(peticion.getId()).agregarJugador(new Jugador(clientSocket,out,in));
	}

	private void enviarPartidas( ActualizarPartidasBean peticion ) throws IOException{
		// DEBERIAMOS HACER ESTO PARA CADA PARTIDA
		for (Partida partida : partidas) {
			
			peticion.getPartidas().add(partida.getNombre() + " " +
										partida.getCantJugadoresEnCurso() + " " +
										partida.getCantJugadoresMax() + " " +
										partida.isEstado() );
		}
		out.writeObject(peticion);
	
	}
	//////////////METODOS PARA EL MANEJO DE BASE DE DATOS////////
	
	private EstadisticasBean retornarEstadisticas(EstadisticasBean peticion) {
		PreparedStatement pstmt = null;
		String query = "SELECT (SELECT COUNT( * ) FROM estadistica e2 WHERE e2.puntos >= e.puntos) AS posicion, e.victorias victorias, e.derrotas derrotas, e.puntos puntos "+
						"FROM usuario u JOIN estadistica e ON ( u.id = e.id_usuario ) "+ 
						"WHERE u.nick = ? ";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, peticion.getNick());
			System.out.println(pstmt.toString());
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();
			if(rs.next()){
	            peticion.setPartidasGanadas(rs.getString("victorias"));
	            peticion.setPartidasJugadas(Integer.toString(rs.getInt("victorias") + rs.getInt("derrotas")));
	            peticion.setPosicion(rs.getString("posicion"));
	            peticion.setPuntos(rs.getString("puntos"));
			}
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return peticion;
	}

	private RankingBean retornarRanking(RankingBean peticion) {
		PreparedStatement pstmt = null;
		try {
			if(peticion.isTop20())
				pstmt = conn.prepareStatement("select u.nick nick, e.puntos puntos from usuario u join estadistica e on(u.id = e.id_usuario) order by e.puntos DESC limit 20");
			else
				pstmt = conn.prepareStatement("select u.nick nick, e.puntos puntos from usuario u join estadistica e on(u.id = e.id_usuario) order by e.puntos DESC");
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();
			int posicion = 1;
			while(rs.next()){
                String[] fila = new String[3];//Creamos un Objeto con tantos parámetros como datos retorne cada fila 
                                             // de la consulta
                fila[0] = (posicion++)+"°";
                fila[1] = rs.getString("nick"); //Lo que hay entre comillas son los campos de la base de datos
                fila[2] = rs.getString("puntos");
                peticion.getModelo().addRow(fila); // Añade una fila al final del modelo de la tabla
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return peticion;
	}

	private void actualizarDatos(ActualizarDatosBean peticion) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update usuario set nick = ?, preguntaSecreta = ?, respuestaSecreta = ?, password = ? where id = ?");
			pstmt.setString(1, peticion.getNick());
			pstmt.setString(2, peticion.getPregunta());
			pstmt.setString(3, peticion.getRespuesta());
			pstmt.setString(4, new String(peticion.getPassword()));
			pstmt.setInt(5, peticion.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private String existeUsuario(ExisteUsuarioBean peticion) {
		String respuesta = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("select 1 from usuario where nick = ?");
			pstmt.setString(1, peticion.getNick());
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();
			if(rs.next()){
				respuesta = "EXISTE";
			}else{
				respuesta =  "NO EXISTE";
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
		return respuesta;
	}	

	private DatosUsuarioBean devolverDatosCliente(DatosUsuarioBean peticion) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("select password, preguntaSecreta, respuestaSecreta from usuario where nick = ?");
			pstmt.setString(1, peticion.getNick());
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();
			if(rs.next()){
				peticion.setPassword(rs.getString(1));
				peticion.setPreguntaSecreta(rs.getString(2));
				peticion.setRespuestaSecreta(rs.getString(3));
			}else{
				peticion = null;
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
		return peticion;
	}

	private String validarRespuesta(ValidarRespuestaBean peticion) {
		String respuestaSecreta = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(" select password from usuario where nick = ? and respuestaSecreta = ?");
			pstmt.setString(1, peticion.getNick());
			pstmt.setString(2, peticion.getRespuesta());
			pstmt.execute();
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
			ResultSet rs = pstmt.getResultSet();
			if(loginSuccess = rs.next())
				idUsuario = rs.getInt("id");
				
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
				pstmt = conn.prepareStatement("Insert into usuario values(null, ?, ?, ?, ?)");
				pstmt.setString(1, registro.getNick());
				pstmt.setString(2, new String(registro.getPassword()));
				pstmt.setString(3, registro.getPregunta());
				pstmt.setString(4, registro.getRespuesta());
				pstmt.execute();
				
				pstmt = conn.prepareStatement("insert into estadistica values((select max(id) from usuario),0,0,0)");
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
