package clasesPrincipales;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Zombie extends Jugador{

	public Zombie(String nick,int x, int y){
		super(nick,x,y);
		img = new JLabel(new ImageIcon(Zombie.class.getResource("/zombie.jpg")));
	}
}
