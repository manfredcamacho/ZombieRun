package herramientas;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class cargadorRecursos {
	
	public static final Font ZOMBI_FONT = cargadorRecursos.cargarFuentes("/fuentes/zombie.ttf",50f);
	public static final Font HPSIMPLIFIED_FONT = cargadorRecursos.cargarFuentes("/fuentes/HPSimplified.ttf", 20f);
	
	public static Font cargarFuentes(final String ruta, float size){
		Font fuente = null;
		InputStream entrada = ClassLoader.class.getResourceAsStream(ruta);//leemos el archivo
		
		//convertimos los datos binarios en un fuente.
		try {
			fuente  = Font.createFont(Font.TRUETYPE_FONT, entrada);
		} catch (FontFormatException e) {
			System.out.println("Formato de fuente incorrecto");
		} catch (IOException e) {
			System.out.println("Error en el archivo de la fuente");
		}
		
		//establecemos el tamaño de la fuente por defecto
		fuente = fuente.deriveFont(size);
		
		return fuente;
	}
	
	public static ImageIcon cargarImagenParaLabel(String ruta, JLabel label){
		ImageIcon imgIcon = null;
		try {
			BufferedImage img = ImageIO.read(new File(ruta));
			Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
			imgIcon = new ImageIcon(dimg);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return imgIcon;
	}
	
	public static ImageIcon cargarIcono(int x, int y){
		ImageIcon imgIcon = null;
		try {
			BufferedImage img = ImageIO.read(new File("recursos/imagenes/iconos.png"));
			imgIcon = new ImageIcon(img.getSubimage((x-1) * 60, (y-1) * 60, 55, 55));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return imgIcon;
	}
	
	
}
