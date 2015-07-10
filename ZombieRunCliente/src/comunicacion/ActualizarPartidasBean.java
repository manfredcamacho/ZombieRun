package comunicacion;

import java.io.Serializable;
import java.util.*;
public class ActualizarPartidasBean implements Serializable {

	
	private static final long serialVersionUID = 2282652593976881936L;

	private ArrayList<String> partidas; // NOMBRE|JUGADORES EN CURSO|CANTMAXIMA|ESTADO
	
	public ActualizarPartidasBean( ){
		partidas = new ArrayList<String>();
	}

	public ArrayList<String> getPartidas() {
		return partidas;
	}

	public void setPartidas(ArrayList<String> partidas) {
		this.partidas = partidas;
	}
	
	
}
