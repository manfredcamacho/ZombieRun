package ventanas;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import clasesPrincipales.TablaRanking;
import comunicacion.RankingBean;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Ranking extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private TablaRanking modelo;
	private JFrame frame;
	private Cliente cliente;
	
	
	public Ranking(JFrame frame, Cliente cliente){
		this.frame = frame;
		this.cliente = cliente;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		
		Font zombie = cargarFuentes("/fuentes/ZOMBIE.TTF", 50L);
		Font hp = cargarFuentes("/fuentes/HPSimplified.ttf", 20L);
		
		setSize(450, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 105, 375, 281);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(new EmptyBorder(5, 20, 5, 5));
		contentPane.add(scrollPane);
		
		modelo = new TablaRanking();
		table = new JTable();	
		rellenarTabla(modelo);
		table.setFont(new Font("Tahoma", 0, 15));
		table.setForeground(Color.WHITE);
		table.getTableHeader().setBackground(new Color(255, 255, 255, 100));
		table.getTableHeader().setFont(hp);
		table.setOpaque(false);
		table.setBackground( new Color(0, 0, 0, 100) );
		scrollPane.setViewportView(table);
		JLabel lblRankingGlobal = new JLabel("Ranking Global", SwingConstants.CENTER);
		
		lblRankingGlobal.setForeground(Color.WHITE);
		lblRankingGlobal.setBounds(0, 0, 444, 60);	
		lblRankingGlobal.setFont(zombie);
		contentPane.add(lblRankingGlobal);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				volver();
			}
		});
		btnVolver.setBounds(152, 411, 125, 39);
		btnVolver.setFont(hp);
		contentPane.add(btnVolver);
		
		JLabel fondo = new JLabel("");
		fondo.setLocation(0, 0);
		fondo.setSize(444, 500);
		fondo.setIcon(cargarImagenParaLabel("/imagenes/ranking.jpg", fondo));
		
		if(frame instanceof Estadistica){
			lblRankingGlobal.setText("TOP 20");
			fondo.setIcon(cargarImagenParaLabel("/imagenes/fondo_8.jpg", fondo));
		}
		contentPane.add(fondo);
		setVisible(true);
	}
	
	public Font cargarFuentes(final String ruta, float size){
		Font fuente = null;
		InputStream entrada = getClass().getResourceAsStream(ruta);//leemos el archivo
		
		//convertimos los datos binarios en un fuente.
		try {
			fuente  = Font.createFont(Font.TRUETYPE_FONT, entrada);
		} catch (FontFormatException e) {
			System.err.println("Formato de fuente incorrecto");
		} catch (IOException e) {
			System.err.println("Error en el archivo de la fuente");
		}
		
		//establecemos el tamaño de la fuente por defecto
		fuente = fuente.deriveFont(size);
		
		return fuente;
	}
	
	public ImageIcon cargarImagenParaLabel(String ruta, JLabel label){
		ImageIcon imgIcon = null;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource(ruta));
			Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
			imgIcon = new ImageIcon(dimg);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return imgIcon;
	}
	
	private void rellenarTabla(TablaRanking modelo) {
		modelo.addColumn("Posicion");
		modelo.addColumn("Nick");
		modelo.addColumn("Puntos");
		boolean top20 = false;
		if(frame instanceof Estadistica)
			top20 = true;
		cliente.enviarMensaje(new RankingBean(modelo, top20));
		modelo = ((RankingBean)cliente.leerMensaje()).getModelo();
		table.setModel(modelo);
	}

	public void volver(){
		frame.setVisible(true);
		this.dispose();
	}
	
	public void salir(){
		int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
}