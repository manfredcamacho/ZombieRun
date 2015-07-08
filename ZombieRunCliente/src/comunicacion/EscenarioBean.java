package comunicacion;
import java.io.Serializable;
import principales.*;
public class EscenarioBean implements Serializable {

	
	private static final long serialVersionUID = 8885287870629662484L;

	private Figura[][] mapa;
	private int tamX;
	private int tamY;
	
	public EscenarioBean( Figura[][] m, int x, int y){
		mapa = m;
		tamX = x;
		tamY = y;
	}

	public Figura[][] getMapa() {
		return mapa;
	}

	public int getTamX() {
		return tamX;
	}

	public int getTamY() {
		return tamY;
	}
	
	
	
}
