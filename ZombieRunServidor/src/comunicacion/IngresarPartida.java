package comunicacion;

import java.io.Serializable;

public class IngresarPartida implements Serializable{

	private static final long serialVersionUID = 2989198129413947150L;

	private int id;
	
	public IngresarPartida(int i){
		id = i;
	}
}
