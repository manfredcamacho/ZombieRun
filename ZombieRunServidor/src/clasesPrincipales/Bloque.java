package clasesPrincipales;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bloque extends Figura{
	
	public Bloque(){
		img = new JLabel(new ImageIcon(Bloque.class.getResource("/muro.jpg")));
	}

}
