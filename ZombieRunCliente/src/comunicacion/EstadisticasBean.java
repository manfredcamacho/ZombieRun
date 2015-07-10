package comunicacion;

import java.io.Serializable;

public class EstadisticasBean implements Serializable{

	private static final long serialVersionUID = -1608786568016379922L;
	private String nick;
	private String partidasGanadas;
	private String partidasJugadas;
	private String puntos;
	private String posicion;
	
	public EstadisticasBean(String nick){
		this.nick = nick;
	}

	public String getPartidasGanadas() {
		return partidasGanadas;
	}

	public void setPartidasGanadas(String partidasGanadas) {
		this.partidasGanadas = partidasGanadas;
	}

	public String getPartidasJugadas() {
		return partidasJugadas;
	}

	public void setPartidasJugadas(String partidasJugadas) {
		this.partidasJugadas = partidasJugadas;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getNick() {
		return nick;
	}

	public String getPosicion() {
		return posicion;
	}
	
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

}
