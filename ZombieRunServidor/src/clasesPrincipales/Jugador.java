package clasesPrincipales;

public class Jugador extends Figura {

	private int x;
	private int y;
	private String nick;
	
	public Jugador(String nick , int x, int y) {
		super();
		this.nick = nick;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public void moverIzquierda(){
		this.x--;
	}
	public void moverDerecha(){
		this.x++;
	}
	public void moverArriba(){
		this.y--;
	}
	public void moverAbajo(){
		this.y++;
	}
	
}
