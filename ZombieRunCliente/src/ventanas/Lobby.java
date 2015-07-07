package ventanas;

import herramientas.cargadorRecursos;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

import comunicacion.*;
public class Lobby extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Login login;
	
	// COMUNICACION CON EL CLIENTE
	private Cliente clientSocket;
	
	//ARRAYLIST DE PARTIDAS
	//....
	
	public Lobby(final Login login, Cliente client) {
		
		clientSocket = client;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Lobby.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				desconectar();			
			}
		});

		this.login = login;
		setTitle("ZombieRush");
		setSize(573, 406);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(35, 106, 133, 23);
		contentPane.add(btnActualizar);
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ENVIAR UN MENSAJE AL SERVIDOR PARA QUE EL SERVIDOR NOS RETORNE 
				// LA LISTA DE PARTIDAS
				
				// A CONTINUACION CARGAR ESE ARRAYLIST QUE NOS DIO EN EL JLIST
				
				// DEBERIAMOS TENER UN METODO QUE ACTUALICE LA LISTA
			}
		});

		
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(35, 140, 133, 23);
		contentPane.add(btnIngresar);

		JButton btnConfiguraciones = new JButton("Configuraciones");
		btnConfiguraciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirConfiguraciones();
			}
		});
				
		btnConfiguraciones.setBounds(35, 208, 133, 23);
		contentPane.add(btnConfiguraciones);

		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				desconectar();
			}
		});
		btnDesconectar.setBounds(35, 274, 133, 23);
		contentPane.add(btnDesconectar);

		
		
		/////////////////////////////////////////////////////////////////////
		
		/*
		 * NO DEBERIAMOS CARGAR NADA AL PRICIPIO.... SOLO TENDRIAMOS QUE TENER UN METODO PARA ACTUALIZAR
		 * MEDIANTE UN ARRAYLIST CADA VEZ QUE EL CLIENTE LO SOLICITA...
		 */
		
		/////////////////////////////////////////////////////////////////////
		
		//cambiar String por tipo de dato correcto
		final JList<String> listPartidas = new JList<String>();
		DefaultListModel<String> modelo = new DefaultListModel</*
														 * AQUI VA EL TIPO DE
														 * OBJETO QUE SE
														 * ALMACENA
														 */>();
		modelo.addElement("Zombilandia               6/9         REGISTRANDO");
		modelo.addElement("GuisoDeCerebros        1/4         REGISTRANDO");
		modelo.addElement("JuegosDelHambre       4/4         EN CURSO");
		listPartidas.setModel(modelo);
		listPartidas.setOpaque(true);
		listPartidas.setBackground(new Color(0,0,0,100));
		listPartidas.setForeground(Color.WHITE);
		listPartidas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		// SETEA EL TAMAÑO
		listPartidas.setBounds(222, 109, 334, 188);
		// ESTA LINEA DE ABAJO HACE QUE LA SELECCION DE LOS ELEMENTOS SEA UNICA
		listPartidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(listPartidas);
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clientSocket.enviarMensaje(new IngresarPartida( 1 ));
				
				if(listPartidas.getSelectedIndex() == -1){
					mostrarMensajeSatisfactorio("No selecciono ninguna Sala", JOptionPane.WARNING_MESSAGE);
				}
				else{
					String selected = listPartidas.getSelectedValue().toString();
					abrirEspera(selected.split(" +"));
				}
				
			}
		});
		JButton btnEstadisticas = new JButton("Estadisticas");
		btnEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirEstadisticas();
			}
		});
		btnEstadisticas.setBounds(35, 174, 133, 23);
		contentPane.add(btnEstadisticas);
		
		JButton btnRanking = new JButton("Ranking");
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirRanking();
			}
		});
		btnRanking.setBounds(35, 242, 133, 23);
		contentPane.add(btnRanking);
		
		JLabel lblLobby = new JLabel("Lobby", SwingConstants.CENTER);
		lblLobby.setBounds(0, 11, 546, 42);
		lblLobby.setFont(cargadorRecursos.ZOMBI_FONT);
		lblLobby.setForeground(Color.WHITE);
		contentPane.add(lblLobby);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 567, 377);
		label.setIcon(cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_2.jpg", label));
		
		contentPane.add(label);
		setVisible(true);
	}

	public void abrirConfiguraciones() {
		@SuppressWarnings("unused")
		Configuracion configuracion = new Configuracion(this);
		this.setVisible(false);
	}

	public void abrirEstadisticas() {
		@SuppressWarnings("unused")
		Estadistica estadistica = new Estadistica(this);
		this.setVisible(false);
	}
	
	public void abrirRanking() {
		@SuppressWarnings("unused")
		Ranking ranking = new Ranking(this);
		this.setVisible(false);
	}

	public void desconectar() {
		if(pedirConfirmacion() == JOptionPane.YES_OPTION){
			clientSocket.enviarMensaje(new DesconexionBean());
			login.setVisible(true);
			this.dispose();
		}
	}
	
	public int pedirConfirmacion(){
		return JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
	}
	
	public void abrirEspera(String datos[]){
		@SuppressWarnings("unused")
		Espera espera = new Espera(this, datos, clientSocket);
		this.setVisible(false);
	}
	
	public void mostrarMensajeSatisfactorio(String msg, int tipo_mensaje){
		JOptionPane.showMessageDialog(this, msg, "", tipo_mensaje, null);
	}
}
