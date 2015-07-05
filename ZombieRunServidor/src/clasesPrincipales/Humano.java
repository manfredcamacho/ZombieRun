package clasesPrincipales;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Humano extends Jugador{
	
	
	
	public Humano(String nick,int x, int y){
		super(nick,x,y);
		img = new JLabel(new ImageIcon(Humano.class.getResource("/humano.jpg")));
	}

	
	
}
