package ventanas;

import herramientas.cargadorRecursos;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.Timer;

import comunicacion.EscenarioBean;
import comunicacion.estoyListoBean;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Espera extends JFrame {

	private static final long serialVersionUID = -3130079893489606850L;
	private JPanel contentPane;
	private Lobby lobby;
	private JLabel lblTimer;
	private Timer timer;
	
	// COMUNICACION CON EL CLIENTE
	@SuppressWarnings("unused")
	private Cliente clientSocket;
	
	
	public Espera(Lobby lobby, String datos[], Cliente client) {
		clientSocket = client;
		this.lobby = lobby;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				salir();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Espera.class.getResource("/imagenes/zombie_hand.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle(datos[0]);
		setBounds(100, 100, 554, 344);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Sala de espera");
		lblTitulo.setBounds(177, 11, 371, 39);
		lblTitulo.setFont(cargadorRecursos.ZOMBI_FONT);
		lblTitulo.setForeground(Color.WHITE);
		contentPane.add(lblTitulo);
		
		JLabel lblCom = new JLabel("Comienza en:");
		lblCom.setBounds(208, 236, 100, 14);
		lblCom.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblCom.setForeground(Color.WHITE);
		contentPane.add(lblCom);
		
		JLabel lblCM = new JLabel("Cantidad Maxima");
		lblCM.setBounds(208, 112, 174, 14);
		lblCM.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblCM.setForeground(Color.WHITE);
		contentPane.add(lblCM);
		
		JLabel lblJ = new JLabel("Jugadores");
		lblJ.setBounds(208, 137, 92, 14);
		lblJ.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblJ.setForeground(Color.WHITE);
		contentPane.add(lblJ);
		
		JLabel lblP = new JLabel("Partida");
		lblP.setBounds(208, 87, 65, 14);
		lblP.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblP.setForeground(Color.WHITE);
		contentPane.add(lblP);
		
		///////////////////////
		/////VARIABLES
		
		JLabel lblNombrePartida = new JLabel(datos[0]);
		lblNombrePartida.setBounds(359, 87, 119, 14);
		lblNombrePartida.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNombrePartida.setForeground(Color.WHITE);
		contentPane.add(lblNombrePartida);
		
		JLabel lblCantidadMax = new JLabel((datos[1].split("/"))[1]);
		lblCantidadMax.setBounds(359, 112, 134, 14);
		lblCantidadMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCantidadMax.setForeground(Color.WHITE);
		contentPane.add(lblCantidadMax);
		
		JLabel lblCantidadJugadores = new JLabel((datos[1].split("/"))[0]);
		lblCantidadJugadores.setBounds(359, 137, 134, 14);
		lblCantidadJugadores.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCantidadJugadores.setForeground(Color.WHITE);
		contentPane.add(lblCantidadJugadores);
		
		lblTimer = new JLabel("0:30");
		lblTimer.setBounds(335, 236, 46, 14);
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTimer.setForeground(Color.WHITE);
		contentPane.add(lblTimer);
		
		
		
		/* ESTO DEBERIA ESTAR EN UN METODO ( ACCIONAR TIME O ALGO ASI )
		 * DEBERIA EJECUTARSE UNA VEZ QUE SE LLENO LA PARTIDA Y FRENARCE CUANDO FALTAN JUGADORES
		 * 
		 *  ( ! ) ABRIR PARTIDA DEBERIA EJECUTAR OTRO FRAME, DONDE SE VA A ESPERAR
		 *  QUE LA PARTIDA DEL LADO DEL SERVIDOR ENVIE EL MAPA Y LO PINTE
		 *
		 * 
		*/
		
		Thread hilo = new Thread( new Runnable(){
			public void run(){
				Object obj =  (Object)clientSocket.escuchar();
				if( obj instanceof EscenarioBean )
						System.out.println(clientSocket.leerMensaje());
						timer = new Timer(1000, new ActionListener() {			
							int elapsedSeconds = 5;
							@Override
							public void actionPerformed(ActionEvent e) {
								elapsedSeconds--;
						        lblTimer.setText(Integer.toString(elapsedSeconds));
						        if(elapsedSeconds == 0){
						           timer.stop();
						           System.out.println("Enviando OKEY");
						           clientSocket.enviarMensaje(new estoyListoBean(clientSocket.getJugador()));
						           abrirPartida();				// ( ! )
						           try {
									this.finalize();
								} catch (Throwable e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						        }
						}});
						timer.start();
				
			}		
		});
		hilo.start();

		
		
		/*
		 * LO DE LA LISTA HABRIA QUE VER COMO HACER
		 * POR QUE DEBERIAMOS ENVIAR TAMBIEN UN OBJETO CON EL JUGADOR QUE SE CONECTO
		 * O CON SU NOMBRE
		 * 
		 * LO QUE SI PODRIAMOS MOSTRAR ES LA CANTIDAD DE JUGADORES QUE ESTAN CONECTADOS A LA PARTIDA
		 */
		
		//cambiar String por el tipo de objeto correcto
		JList<String> listJugadores = new JList<String>();
		listJugadores.setValueIsAdjusting(true);
		listJugadores.setFont(new Font("Tahoma", Font.PLAIN, 15));
		listJugadores.setForeground(Color.WHITE);
		listJugadores.setOpaque(true);
		listJugadores.setBackground(new Color(0,0,0,100));
		listJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		for (int i = 0; i < Integer.parseInt((datos[1].split("/"))[0]); i++) {
			modelo.addElement("Jugador " + (i+1));
		}
		
		listJugadores.setModel(modelo);
		listJugadores.setOpaque(false);
		listJugadores.setBounds(22, 37, 144, 232);
		contentPane.add(listJugadores);
	
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atras();
			}
		});
		btnSalir.setBounds(208, 272, 119, 32);
		contentPane.add(btnSalir);
		
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, 548, 315);
		fondo.setIcon(cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_3.jpg", fondo));
		setVisible(true);
		contentPane.add(fondo);
	}
	
	public void salir(){
		int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public void atras(){
		lobby.setVisible(true);
		dispose();
	}
	
	public void abrirPartida(){
		//Partida partida = new Partida(this, clientSocket);
		Escenario escenario = new Escenario( clientSocket );
		escenario.setVisible(true);
		this.setVisible(false);
	}
}
