package servidor;


import java.util.*;
import java.io.IOException;

import clasesPrincipales.*;

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
	
	
	///////////////MAPAS////////////////////////
	
	private static final char[][] murosMapa1 ={
		{'X','X','X','X','X'},
		{'X',' ',' ',' ','X'},
		{'X',' ',' ',' ','X'},
		{'X',' ',' ',' ','X'},
		{'X','X','X','X','X'}
	};
	
	////////////////////////////////////////////
	
	
	
	
	public Partida(int i, String nom, int cantMax, int cantMin, int idMapa ){
		id = i;
		setNombre(nom);
		cantJugadoresMax = cantMax;
		setCantJugadoresMin(cantMin);
		cantJugadoresEnCurso = 0;
		jugadores = new ArrayList<Jugador>();
		
		
		if( idMapa == 1 ){
			escenario = new Figura[5][5];
			for (int j = 0; j < 5; j++) {
				for (int j2 = 0; j2 < 5; j2++) {
					if( murosMapa1[j][j2] == 'X'){
						escenario[j][j2] = new Bloque();
					}
				}
			}
		}
		
	}
	
	public void agregarJugador( Jugador jugador ){
		if( cantJugadoresEnCurso < cantJugadoresMax ){
			jugadores.add(jugador);
			cantJugadoresEnCurso++;
			System.out.println( "CANTIDAD DE JUGADORES EN CURSO : " + cantJugadoresEnCurso );
			
			if( cantJugadoresEnCurso == cantJugadoresMax ){
				generarPosiciones();
				enviarMapa();
			}
			
		}else{
			System.out.println("CAPACIDAD MAXIMA PARTIDA : " + this.id);
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
	
	
	public void generarPosiciones(){
		Random rand = new Random();
		for (Jugador jugador : jugadores) {
			boolean seColoco = false;
			while( seColoco == false ){
				int x = rand.nextInt()%murosMapa1.length;
				System.out.println(x);
				int y = rand.nextInt()%murosMapa1.length;
				System.out.println(y);
				if( x >= 0 && y >= 0 && escenario[x][y] == null ){
					escenario[x][y] = new Personaje(jugador.getNick(), x, y, false);
					seColoco = true;
				}
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
	
	
	
	

