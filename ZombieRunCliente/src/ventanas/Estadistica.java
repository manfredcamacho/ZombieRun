package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import comunicacion.DesconexionBean;
import comunicacion.EstadisticasBean;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

public class Estadistica extends JFrame {

	private static final long serialVersionUID = -1311732552748537216L;
	private JPanel contentPane;
	private Lobby lobby;
	private JLabel lblUser;
	private JLabel lblPartidasGanadas;
	private JLabel lblPartidasJugadas;
	private JLabel lblPuntos; 
	private JLabel lblPosicion;

	public Estadistica(final Lobby lob) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Estadistica.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		
		Font zombie = cargarFuentes("/fuentes/ZOMBIE.TTF", 50L);
		Font hp = cargarFuentes("/fuentes/HPSimplified.ttf", 20L);
		
		lobby = lob;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 444, 392);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEstadisticas = new JLabel("Estadisticas",SwingConstants.CENTER);
		lblEstadisticas.setBounds(0, 11, 441, 50);
		lblEstadisticas.setFont(zombie);
		lblEstadisticas.setForeground(Color.WHITE);
		contentPane.add(lblEstadisticas);
		
		JLabel lblU = new JLabel("Usuario");
		lblU.setBounds(25, 104, 187, 14);
		lblU.setFont(hp);
		lblU.setForeground(Color.WHITE);
		contentPane.add(lblU);
		
		JLabel lblPG = new JLabel("Partidas Ganadas");
		lblPG.setBounds(25, 129, 187, 14);
		lblPG.setFont(hp);
		lblPG.setForeground(Color.WHITE);
		contentPane.add(lblPG);
		
		JLabel lblPJ = new JLabel("Partidas Jugadas");
		lblPJ.setBounds(25, 154, 187, 14);
		lblPJ.setFont(hp);
		lblPJ.setForeground(Color.WHITE);
		contentPane.add(lblPJ);
		
		JLabel lblPts = new JLabel("Puntos");
		lblPts.setBounds(25, 179, 187, 14);
		lblPts.setFont(hp);
		lblPts.setForeground(Color.WHITE);
		contentPane.add(lblPts);
		
		JLabel lblPos = new JLabel("Posicion");
		lblPos.setBounds(25, 204, 187, 14);
		lblPos.setFont(hp);
		lblPos.setForeground(Color.WHITE);
		contentPane.add(lblPos);
		
		JButton btnTop = new JButton("top 20");
		btnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirRanking();
			}
		});
		btnTop.setBounds(80, 290, 132, 50);
		btnTop.setFont(hp);
		contentPane.add(btnTop);
		
		/////////////////////
		// VARIABLES
		
		lblUser = new JLabel();
		lblUser.setBounds(250, 104, 120, 14);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUser.setForeground(Color.WHITE);
		contentPane.add(lblUser);
		
		lblPartidasGanadas = new JLabel();
		lblPartidasGanadas.setBounds(250, 129, 106, 14);
		lblPartidasGanadas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPartidasGanadas.setForeground(Color.WHITE);
		contentPane.add(lblPartidasGanadas);
		
		lblPartidasJugadas = new JLabel();
		lblPartidasJugadas.setBounds(250, 154, 106, 14);
		lblPartidasJugadas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPartidasJugadas.setForeground(Color.WHITE);
		contentPane.add(lblPartidasJugadas);
		
		lblPuntos = new JLabel();
		lblPuntos.setBounds(250, 179, 106, 14);
		lblPuntos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPuntos.setForeground(Color.WHITE);
		contentPane.add(lblPuntos);
		
		lblPosicion = new JLabel();
		lblPosicion.setBounds(250, 204, 106, 14);
		lblPosicion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPosicion.setForeground(Color.WHITE);
		contentPane.add(lblPosicion);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setFont(hp);
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrar();
			}
		});
		btnAtras.setBounds(222, 290, 120, 50);
		contentPane.add(btnAtras);
		
		JLabel fondo = new JLabel("");
		fondo.setBounds(0, 0, 441, 363);
		fondo.setIcon(cargarImagenParaLabel("/imagenes/fondo_6.jpg", fondo));
		contentPane.add(fondo);
		
		cargarEstadisticas();
		
		this.setVisible(true);
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
	
	private void cargarEstadisticas() {
		EstadisticasBean e = new EstadisticasBean(lobby.getLogin().getNick());
		lobby.getCliente().enviarMensaje(e);
		e = (EstadisticasBean)lobby.getCliente().leerMensaje();
		this.lblPartidasGanadas.setText(e.getPartidasGanadas());
		this.lblPartidasJugadas.setText((e.getPartidasJugadas()));
		this.lblPuntos.setText(e.getPuntos());
		this.lblPosicion.setText(e.getPosicion()+"°");
		
	}

	public void cerrar(){
		lobby.setVisible(true);
		dispose();
	}
	
	public void salir(){
		int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			lobby.getCliente().enviarMensaje(new DesconexionBean());
			lobby.getCliente().cerrarSocket();
			this.dispose();
		}
	}
	
	public void abrirRanking() {
		@SuppressWarnings("unused")
		Ranking ranking = new Ranking(this,lobby.getCliente());
		this.setVisible(false);
	}

}
