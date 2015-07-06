package principales;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Jugador extends Figura implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311220005520269731L;
	private int x;
	private int y;
	private boolean esZombie;
	private String nick;
	
	/***CONDICION AGREGADA POR CAMILA**/
	
	public Jugador(String nick , int x, int y, boolean condicion) {
		super();
		this.esZombie = condicion; 
		this.nick = nick;
		this.x = x;
		this.y = y;
		
		/***MODIFICADO POR CAMILA***/
//		if( esZombie == false){ // SI ES FALSO, ES HUMANO
//			img = new JLabel(new ImageIcon(Jugador.class.getResource("/humano.jpg")));
//		}else{
//			img = new JLabel(new ImageIcon(Jugador.class.getResource("/zombie.jpg")));			
//		}
		/*****/
		
	}
	
	public Jugador(boolean bandera){
		esZombie = true;
	}
	public boolean getCondicion(){
		return esZombie;
		
	}
	
	public void seConvierte(boolean seConvirtio){
		esZombie = seConvirtio;
//		img.setIcon(new ImageIcon(Jugador.class.getResource("/zombie.jpg")));
	}
	
	public void setCondicion(boolean cond){
		esZombie = cond;
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
