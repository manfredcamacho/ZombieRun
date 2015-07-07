package servidor;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Jugador {
	
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	// NO SE SI SERAN NECESARIOS
	private int x;
	private int y;
	private int direccion; // 0 arriba - 1 abajo - 2 izquierda - 3 derecha
	private String nick;
	
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	public Jugador( Socket s , ObjectOutputStream o, ObjectInputStream i ){
		clientSocket = s;
		in = i;
		out = o;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	
	
}
