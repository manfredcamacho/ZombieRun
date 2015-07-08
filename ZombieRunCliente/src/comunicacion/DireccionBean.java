package comunicacion;

import java.io.Serializable;

public class DireccionBean implements Serializable {

	
	private static final long serialVersionUID = 4487657800612935463L;
	private int direccion;// 0 NO SE MOVIO - 1 ARRIBA - 2 ABAJO - 3 IZQUIERDA - 4 DERECHA
	private int id;
	public DireccionBean(int direccion, int id) {
		super();
		this.direccion = direccion;
		this.id = id;
	}
	public int getDireccion() {
		return direccion;
	}
	public int getId() {
		return id;
	}
	
	
}
