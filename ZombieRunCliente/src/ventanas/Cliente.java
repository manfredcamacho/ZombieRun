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
	private static final String host ="10.11.3.2";
	private static final int puerto = 5000;
	
	private Object objLeido;
		
	public Cliente() throws ClassNotFoundException{
		// GENERAMOS LA CONEXION
		try {
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
	
	public Object escuchar(){
		try {
			return in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void enviarMensaje( Object obj ){
		try {
			out.reset(); // USAR SIEMPRE
			out.writeObject(obj);
			//out.reset(); // USAR SIEMPRE
			//out.flush();
			objLeido = (Object)in.readObject();
			System.out.println(objLeido);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Object leerMensaje(){
		return objLeido;
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
