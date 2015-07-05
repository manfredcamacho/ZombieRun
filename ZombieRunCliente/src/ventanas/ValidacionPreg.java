package ventanas;

import herramientas.cargadorRecursos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ValidacionPreg extends JFrame {

	private JPanel contentPane;
	private JTextField TFrespuesta;
	private Login login;
	private JLabel contrasenia;
	


	
	public ValidacionPreg(final Login log) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ValidacionPreg.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
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
		
		String pregunta = "Cual fue el nombre de tu primera mascota? ";
		JTextArea JTpregunta = new JTextArea();
		JTpregunta.setForeground(new Color(240, 255, 255));
		JTpregunta.setText(pregunta);
		JTpregunta.setBounds(32, 33, 431, 75);
		contentPane.add(JTpregunta);
		multilineaTextArea(JTpregunta);
		
		TFrespuesta = new JTextField();
		TFrespuesta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyChar() == '\n')
					validar();
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
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validar();
			}
		});
		btnAceptar.setBounds(32, 213, 111, 36);
		contentPane.add(btnAceptar);
		
		contrasenia = new JLabel("***********");
		contrasenia.setForeground(new Color(245, 255, 250));
		contrasenia.setBounds(154, 230, 210, 29);
		contrasenia.setVisible(true);
		contentPane.add(contrasenia);
		
		JLabel lblContrasea = new JLabel("TU CONTRASEÑA: ");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblContrasea.setForeground(new Color(245, 255, 250));
		lblContrasea.setBounds(154, 213, 110, 14);
		lblContrasea.setVisible(true);
		contentPane.add(lblContrasea);
	
		
		JButton btnAtras = new JButton("Atr\u00E1s");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.setVisible(true);
				login.setIntento();
				dispose();
			}
		});
		btnAtras.setBounds(374, 252, 89, 23);
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
	
	public void salir(){
		int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public void validar(){
		contrasenia.setText("amoroso69");
	}
}
