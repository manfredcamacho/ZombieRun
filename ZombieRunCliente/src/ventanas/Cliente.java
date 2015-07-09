package ventanas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
public class Cliente extends Thread{

	private Socket socket;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	// DATOS NECESARIOS PARA LA CONEXION
	private static final String host ="127.0.0.1";
	private static final int puerto = 5000;
	private int jugador;
	private Object objLeido;
		
	public Cliente() throws ClassNotFoundException{
		// GENERAMOS LA CONEXION
		try {
			jugador = 0;
			socket = new Socket(host,puerto);
			out = new ObjectOutputStream( socket.getOutputStream());
			in = new ObjectInputStream( socket.getInputStream() );
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
//	@Override
//	public void run(){
//		while( true ){
//			try {
//				objLeido = (Object)in.readObject();
//				System.out.println(objLeido);
//			} catch (ClassNotFoundException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	public ObjectOutputStream getOut() {
		return out;
	}

	public Object escuchar(){
		try {
			System.out.println(in.toString());
			Object peticion = in.readObject();
			objLeido = peticion;
			return peticion;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void enviarMensaje( Object obj ){
		try {
			out.reset(); // USAR SIEMPRE
			out.writeObject(obj);
			//out.reset(); // USAR SIEMPRE
			out.flush();
			
			objLeido = (Object)in.readObject();
			if( objLeido instanceof Integer ){
				jugador = (Integer)objLeido; // REPRESENTA AL JUGADOR EN JUEGO
			}
			System.out.println(objLeido);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int getJugador() {
		return jugador;
	}

	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	public Object leerMensaje(){
		return objLeido;
	}
	
	public ObjectInputStream getIn() {
		return in;
	}

	public void cerrarSocket(){
		try {
			enviarMensaje("KILL THREAD");
			out.close();
			in.close();					
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
