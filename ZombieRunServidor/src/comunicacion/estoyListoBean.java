package comunicacion;

import java.io.Serializable;

public class estoyListoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5597839139034819352L;
	private int id;
	private int idPartida;
	
	public estoyListoBean(int i, int partida){
		id = i;
		idPartida = partida;
	}

	
	public int getIdPartida() {
		return idPartida;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
