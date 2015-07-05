package servidor;


import java.util.*;
import java.io.IOException;

import clasesPrincipales.Figura;


public class Partida {

	// DETALLES ESTATICOS DE LA PARTIDA
	private int id;
	private String nombre;
	private int cantJugadoresMax;
	private int cantJugadoresMin;
	
	
	// DETALLES DINAMICOS DE LA PARTIDA
	private boolean estado; // 0 REGISTRANDO - 1 EN CURSO
	private int cantJugadoresEnCurso;
	private Figura[][] escenario;
	private ArrayList<Jugador>jugadores;
	
	public Partida(int i, String nom, int cantMax, int cantMin ){
		id = i;
		setNombre(nom);
		cantJugadoresMax = cantMax;
		setCantJugadoresMin(cantMin);
		cantJugadoresEnCurso = 0;
		jugadores = new ArrayList<Jugador>();
		
	}
	
	public void agregarJugador( Jugador jugador ){
		if( cantJugadoresEnCurso < cantJugadoresMax ){
			jugadores.add(jugador);
			cantJugadoresEnCurso++;
			System.out.println( "CANTIDAD DE JUGADORES EN CURSO : " + cantJugadoresEnCurso );
			
			if( cantJugadoresEnCurso == cantJugadoresMax ){
				enviarMapa();
				recibirDirecciones();
			}
			
		}else{
			System.out.println("CAPACIDAD MAXIMA PARTIDA : " + this.id);
		}
	}
	
	// NO SE SI ESTA BIEN, REVISAR CREO UN HILO POR CADA JUGADOR UNA VEZ QUE SE RECIBE LA DIRECCION EL HILO SE CIERRA
	public void recibirDirecciones(){
		for (final Jugador jugador : jugadores) {
			Thread hilo = new Thread( new Runnable(){
				public void run(){
				try {
					System.out.println("escuchando....");
					Object aux = jugador.getIn().readObject(); // LEEMOS LA DIRECCION A LA QUE QUIERE IR
					jugador.setDireccion((Integer)aux);
					System.out.println("EL JUGADOR :" + jugador.getClientSocket().getInetAddress()
							+ " SE MOVIO HACIA " + jugador.getDireccion());
					jugador.getOut().writeObject("OK");
					this.finalize();
				}catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}} );
			hilo.start();
		}
	}
	
	public void enviarMapa(){ // POR CADA JUGADOR DE EL ARRAYLIST ENVIO EL MAPA
		System.out.println("ENVIANDO MAPA A LOS JUGADORES...");
		for (Jugador jugador : jugadores) {
			try {
				jugador.getOut().writeObject("ESCENARIO");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantJugadoresMin() {
		return cantJugadoresMin;
	}

	public void setCantJugadoresMin(int cantJugadoresMin) {
		this.cantJugadoresMin = cantJugadoresMin;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Figura[][] getEscenario() {
		return escenario;
	}

	public void setEscenario(Figura[][] escenario) {
		this.escenario = escenario;
	}
	
}
	
	
	
	

