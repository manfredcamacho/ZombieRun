package comunicacion;

import java.io.Serializable;

public class DireccionBean implements Serializable {

	
	private static final long serialVersionUID = 4487657800612935463L;
	private int direccion;// 0 NO SE MOVIO - 1 ARRIBA - 2 ABAJO - 3 IZQUIERDA - 4 DERECHA
	private int id;
	private int idPartida;
	public DireccionBean(int direccion, int id, int idP) {
		super();
		this.direccion = direccion;
		this.id = id;
		this.idPartida = idP;
	}
	public int getIdPartida() {
		return idPartida;
	}
	
	public int getDireccion() {
		return direccion;
	}
	public int getId() {
		return id;
	}
	
	
}
