package clasesPrincipales;

import javax.swing.JLabel;

public class Figura {
	protected JLabel img;
	
	public void setImg(int x, int y, int dx, int dy){
		img.setBounds(x, y, dx, dy);
	}

}
