package ventanas;

import herramientas.cargadorRecursos;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class Estadistica extends JFrame {

	private static final long serialVersionUID = -1311732552748537216L;
	private JPanel contentPane;
	private Lobby lobby;

	public Estadistica(final Lobby lob) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Estadistica.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		lobby = lob;
		this.setVisible(true);
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
		lblEstadisticas.setFont(cargadorRecursos.ZOMBI_FONT);
		lblEstadisticas.setForeground(Color.WHITE);
		contentPane.add(lblEstadisticas);
		
		JLabel lblU = new JLabel("Usuario");
		lblU.setBounds(25, 104, 187, 14);
		lblU.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblU.setForeground(Color.WHITE);
		contentPane.add(lblU);
		
		JLabel lblPG = new JLabel("Partidas Ganadas");
		lblPG.setBounds(25, 129, 187, 14);
		lblPG.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblPG.setForeground(Color.WHITE);
		contentPane.add(lblPG);
		
		JLabel lblPJ = new JLabel("Partidas Jugadas");
		lblPJ.setBounds(25, 154, 187, 14);
		lblPJ.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblPJ.setForeground(Color.WHITE);
		contentPane.add(lblPJ);
		
		JLabel lblPts = new JLabel("Puntos");
		lblPts.setBounds(25, 179, 187, 14);
		lblPts.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblPts.setForeground(Color.WHITE);
		contentPane.add(lblPts);
		
		JLabel lblPos = new JLabel("Posicion");
		lblPos.setBounds(25, 204, 187, 14);
		lblPos.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblPos.setForeground(Color.WHITE);
		contentPane.add(lblPos);
		
		JButton btnTop = new JButton("top 20");
		btnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirRanking();
			}
		});
		btnTop.setBounds(80, 290, 132, 50);
		btnTop.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		contentPane.add(btnTop);
		
		/////////////////////
		// VARIABLES
		
		JLabel lblUser = new JLabel("L33T");
		lblUser.setBounds(250, 104, 120, 14);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUser.setForeground(Color.WHITE);
		contentPane.add(lblUser);
		
		JLabel lblPartidasGanadas = new JLabel("127");
		lblPartidasGanadas.setBounds(250, 129, 106, 14);
		lblPartidasGanadas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPartidasGanadas.setForeground(Color.WHITE);
		contentPane.add(lblPartidasGanadas);
		
		JLabel lblPartidasJugadas = new JLabel("145");
		lblPartidasJugadas.setBounds(250, 154, 106, 14);
		lblPartidasJugadas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPartidasJugadas.setForeground(Color.WHITE);
		contentPane.add(lblPartidasJugadas);
		
		JLabel lblPuntos = new JLabel("1278");
		lblPuntos.setBounds(250, 179, 106, 14);
		lblPuntos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPuntos.setForeground(Color.WHITE);
		contentPane.add(lblPuntos);
		
		JLabel lblPosicion = new JLabel("1°");
		lblPosicion.setBounds(250, 204, 106, 14);
		lblPosicion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPosicion.setForeground(Color.WHITE);
		contentPane.add(lblPosicion);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrar();
			}
		});
		btnAtras.setBounds(222, 290, 120, 50);
		contentPane.add(btnAtras);
		
		JLabel fondo = new JLabel("");
		fondo.setBounds(0, 0, 441, 363);
		fondo.setIcon(cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_6.jpg", fondo));
		contentPane.add(fondo);
	}
	
	public void cerrar(){
		lobby.setVisible(true);
		dispose();
	}
	
	public void salir(){
		int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public void abrirRanking() {
		@SuppressWarnings("unused")
		Ranking ranking = new Ranking(this,lobby.getCliente());
		this.setVisible(false);
	}

}
