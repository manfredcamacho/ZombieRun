package comunicacion;
import java.io.Serializable;
import java.util.*;
public class ActualizarEsperaBean implements Serializable {

	private static final long serialVersionUID = -2319303891433855011L;

	private int cantJugadores;
	private ArrayList<String>jugadores;
	
	public ActualizarEsperaBean(int cantJug, ArrayList<String> j){
		cantJugadores = cantJug;
		jugadores = j;
	}

	public int getCantJugadores() {
		return cantJugadores;
	}

	public ArrayList<String> getJugadores() {
		return jugadores;
	}
	
}
