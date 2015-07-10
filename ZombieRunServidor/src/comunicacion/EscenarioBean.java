package comunicacion;
import java.io.Serializable;

import clasesPrincipales.*;
public class EscenarioBean implements Serializable {

	
	private static final long serialVersionUID = 8885287870629662484L;

	private Figura[][] mapa;
	private int tamX;
	private int tamY;
	private int x;
	private int y;
	
	
	public EscenarioBean( Figura[][] m, int x, int y, int px, int py){
		mapa = m;
		tamX = x;
		tamY = y;
		this.x = px;
		this.y = py;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
