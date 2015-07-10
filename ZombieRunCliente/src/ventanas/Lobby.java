package ventanas;
import java.util.*;
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
	
	private final JList<String> listPartidas;
	private final DefaultListModel<String> modelo;
	
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
				clientSocket.enviarMensaje( new ActualizarPartidasBean() );
				cargarPartidas();
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
		listPartidas = new JList<String>();
		modelo = new DefaultListModel</*
														 * AQUI VA EL TIPO DE
														 * OBJETO QUE SE
														 * ALMACENA
														 */>();
		
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
				
				clientSocket.enviarMensaje(new IngresarPartida( listPartidas.getSelectedIndex()));
				// AGREGUE EL INDICE AL MENSAJE
				
				if(listPartidas.getSelectedIndex() == -1){
					mostrarMensajeSatisfactorio("No selecciono ninguna Sala", JOptionPane.WARNING_MESSAGE);
				}
				else{
					String selected = listPartidas.getSelectedValue().toString();
					abrirEspera(selected.split(" "));
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


	protected void cargarPartidas() {
		// TODO Auto-generated method stub
		modelo.clear();
		
		ActualizarPartidasBean obj =(ActualizarPartidasBean) clientSocket.leerMensaje();
		ArrayList<String> lista = obj.getPartidas();
		for (String string : lista) {
			String linea = "";
			String[] dato = string.split(" ");
			if( dato[3].equals("true"))
				dato[3] = "LLENA";
			else
				dato[3] = "REGISTRANDO";
			linea = dato[0]+ " "+dato[1]+ " "+ dato[2]+ " "+ dato[3];
			modelo.addElement(linea);
		}
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
		Ranking ranking = new Ranking(this, clientSocket);
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
		Espera espera = new Espera(this, datos, clientSocket, listPartidas.getSelectedIndex());
		this.setVisible(false);
	}
	
	public void mostrarMensajeSatisfactorio(String msg, int tipo_mensaje){
		JOptionPane.showMessageDialog(this, msg, "", tipo_mensaje, null);
	}
	
	public Cliente getCliente(){
		return this.clientSocket;
	}
	
	public Login getLogin(){
		return this.login;
	}
}
