package comunicacion;

import java.io.Serializable;

public class estoyListoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5597839139034819352L;
	private int id;
	
	public estoyListoBean(int i){
		id = i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
