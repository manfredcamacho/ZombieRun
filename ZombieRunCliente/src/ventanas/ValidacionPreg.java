package ventanas;

import herramientas.cargadorRecursos;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import comunicacion.RecuperarBean;
import comunicacion.ValidarRespuestaBean;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ValidacionPreg extends JFrame {

	private static final long serialVersionUID = -7374416633787035037L;
	private JPanel contentPane;
	private JTextField TFrespuesta;
	private Login login;
	private JTextArea TFpregunta;


	
	public ValidacionPreg(final Login log, final String nick) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ValidacionPreg.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cerrarPrograma();
			}
		});
		login = log;
		setTitle("\u00BFHa olvidado su contrase\u00F1a?");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		this.setVisible(true);
		setBounds(100, 100, 489, 324);		
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		TFpregunta = new JTextArea();
		TFpregunta.setForeground(new Color(240, 255, 255));
		TFpregunta.setBounds(32, 33, 431, 75);
		contentPane.add(TFpregunta);
		multilineaTextArea(TFpregunta);
		cargarPreguntaSecreta(nick);
		
		TFrespuesta = new JTextField();
		TFrespuesta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TFrespuesta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyChar() == '\n')
					validar(nick);
			}
		});
		TFrespuesta.setBounds(154, 165, 252, 20);
		TFrespuesta.setColumns(10);
		contentPane.add(TFrespuesta);
		
		
		JLabel lblRespuesta = new JLabel("RESPUESTA");
		lblRespuesta.setForeground(new Color(240, 255, 255));
		lblRespuesta.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRespuesta.setBounds(32, 165, 110, 20);
		contentPane.add(lblRespuesta);
		
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validar(nick);
			}
		});
		btnAceptar.setBounds(236, 245, 111, 36);
		contentPane.add(btnAceptar);
	
		
		JButton btnAtras = new JButton("Salir");
		btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salir();
			}
		});
		btnAtras.setBounds(357, 245, 106, 36);
		contentPane.add(btnAtras);

		

		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 473, 296);
		label.setIcon(cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_5.jpg", label));
		
		TFrespuesta.requestFocus();
		contentPane.add(label);
	}
	
	public static void multilineaTextArea(JTextArea area) {
	   area.setEditable(false);
	   area.setOpaque(false); 
	   area.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 15));
	   //if (area instanceof JTextArea) {
	     area.setWrapStyleWord(true);
	     area.setLineWrap(true);
	   //}
	 }
	
	public void cerrarPrograma(){
		int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public void salir(){
		login.setVisible(true);
		login.setIntento();
		dispose();
	}
	
	private void cargarPreguntaSecreta(String nick){
		login.getClient().enviarMensaje(new RecuperarBean(nick));
		if(login.getClient().leerMensaje().equals("NICK INVALIDO"))
			JOptionPane.showMessageDialog(this,"El usuario no existe.","Nick Invalido", JOptionPane.WARNING_MESSAGE);
		else
			this.TFpregunta.setText((String) login.getClient().leerMensaje());
	}
	
	private void validar(String nick){
		login.getClient().enviarMensaje(new ValidarRespuestaBean(nick, this.TFrespuesta.getText()));
		if(login.getClient().leerMensaje().equals("RESPUESTA INVALIDA")){
			JOptionPane.showMessageDialog(this,"La respuesta ingresa no es la correcta.","Respuesta Invalida", JOptionPane.WARNING_MESSAGE);
			TFrespuesta.setText("");
			TFrespuesta.requestFocus();
		}else{
			JOptionPane.showMessageDialog(this,(String) login.getClient().leerMensaje(), "Tu contraseña", JOptionPane.INFORMATION_MESSAGE);
			salir();
		}
	}
}
