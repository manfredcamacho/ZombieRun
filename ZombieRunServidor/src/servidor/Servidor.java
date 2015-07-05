package servidor;

import herramientas.MySQLConnection;//************************************

import java.net.*;
import java.sql.Connection;//*************************************
import java.util.*;

import comunicacion.*;
public class Servidor {

	// ESCUCHARA CONSTANTEMENTE A LOS JUGADORES
	
	private ServerSocket sSocket;
	// ESCUCHA EN EL PUERTO 5000
	private static final int puerto = 5000;
	
	// ARRAYLIST DE LAS PERSONAS QUE SE CONECTAN A LA PARTIDA
	private ArrayList<Socket> usuarios;
	
	// DEMO PROBAMOS CON UNA PARTIDA
	private Partida partida;
	
	//ABRIMOS CONECCION A BD***********************************
	private Connection conn;//*********************************
	
	
	
	public Servidor(){
		
		partida = new Partida( 1, "Zombie", 1, 1); // ES PARA INTENTAR CONECTAR A DOS JUGADORES EN UN MISMO PROCESO
		usuarios = new ArrayList<Socket>();
		conn = MySQLConnection.getConnection();//***********************************
		
		try {
			// GENERO EL SERVERSOCKET
			sSocket = new ServerSocket(puerto);
			// ESCUCHO SIEMPRE A VER SI SE CONECTA UN CLIENTE
			// UNA VEZ QUE SE CONECTA GENERO UN HILO Y LO MANDO A EJECUTAR, A ESO
			// LO GUARDO EN UN ARRAYLIST
			System.out.println("ESCUCHANDO EN PUERTO 5000");
			while( true ){
				// LO PONGO A ESCUCHAR HASTA QUE RECIBA UNA CONEXION
				Socket clientSocket = sSocket.accept();
				
				System.out.println("SE CONECTO : " + clientSocket.getInetAddress() );
				usuarios.add(clientSocket);
				HiloDeCliente hilo = new HiloDeCliente( clientSocket, usuarios, partida, conn );//*******************************
				hilo.start();
				// MANDO A EJECUTAR EL HILO
				// VUELVO AL PRINCIPIO DEL WHILE A EMPEZAR A ESCUCHAR DE NUEVO
			}
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Servidor sv = new Servidor();
	}
	
	///////////////////METODOS PARA EL MANEJO DE BASE DE DATOS/////////
	public void desconectar() {
		MySQLConnection.close();
	}
}
