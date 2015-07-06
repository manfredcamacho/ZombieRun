package ventanas;

import herramientas.cargadorRecursos;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import comunicacion.RegistrarBean;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_2;
	private Login login;
	private Cliente client;
	

	/**
	 * Create the frame.
	 */
	public Registro(final Login login,final Cliente client) {
		this.client = client;
		setIconImage(Toolkit.getDefaultToolkit().getImage(Registro.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		this.login = login;
		setResizable(false);
		setSize(450, 404);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRegistro = new JLabel("Registro");
		lblRegistro.setForeground(new Color(0, 204, 0));
		lblRegistro.setFont(cargadorRecursos.ZOMBI_FONT);
		lblRegistro.setBounds(103, 11, 252, 50);
		contentPane.add(lblRegistro);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblUsuario.setBounds(10, 67, 103, 29);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setForeground(new Color(255, 255, 255));
		lblContrasea.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblContrasea.setBounds(10, 110, 144, 29);
		contentPane.add(lblContrasea);
		
		JLabel lblRepetirContrasea = new JLabel("Repetir Contrase\u00F1a:");
		lblRepetirContrasea.setForeground(new Color(255, 255, 255));
		lblRepetirContrasea.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblRepetirContrasea.setBounds(10, 150, 188, 29);
		contentPane.add(lblRepetirContrasea);
		
		JLabel lblPreguntaSecreta = new JLabel("Pregunta Secreta:");
		lblPreguntaSecreta.setForeground(new Color(255, 255, 255));
		lblPreguntaSecreta.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblPreguntaSecreta.setBounds(10, 190, 188, 29);
		contentPane.add(lblPreguntaSecreta);
		
		JLabel lblRespuestaSecreta = new JLabel("Respuesta Secreta");
		lblRespuestaSecreta.setForeground(new Color(255, 255, 255));
		lblRespuestaSecreta.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblRespuestaSecreta.setBounds(10, 230, 188, 29);
		contentPane.add(lblRespuestaSecreta);
		
		textField = new JTextField();
		textField.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		textField.setBounds(209, 71, 215, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setMaximumSize(new Dimension(10, 2147483647));
		textField_1.setFont(new Font("Cambria", Font.PLAIN, 20));
		textField_1.setBounds(208, 194, 216, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		passwordField.setBounds(209, 114, 215, 20);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		passwordField_1.setBounds(208, 154, 216, 20);
		contentPane.add(passwordField_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		textField_2.setBounds(208, 234, 216, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnNewButton.setBounds(65, 298, 133, 44);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		btnNewButton_1.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnNewButton_1.setBounds(243, 298, 133, 44);
		contentPane.add(btnNewButton_1);
		
		JLabel label = new JLabel("");
		label.setBounds(0, -25, 444, 547);
		label.setIcon(cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_9.jpg", label));
		
		contentPane.add(label);
		setVisible(true);
	}
	
	public void cancelar(){
		login.setVisible(true);
		this.dispose();
	}
	
	public void salir(){
		int res = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?","Cerrando la aplicación...", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public void mostrarMensaje(String msg, int tipo_mensaje){
		JOptionPane.showMessageDialog(this, msg, "", tipo_mensaje, null);
	}
	
	public void registrar(){
		
		if(validarCampos()){
			RegistrarBean bean = new RegistrarBean();
			bean.setNick(textField.getText());
			bean.setPassword(passwordField.getPassword());
			bean.setPregunta(textField_1.getText());
			bean.setRespuesta(textField_2.getText());
			client.enviarMensaje(bean);
			if(client.leerMensaje().equals("REGISTRO")){
				mostrarMensaje("Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
				login.setVisible(true);
				this.dispose();
			}else{
				int resp = JOptionPane.showConfirmDialog(this, "Usuario ya existente. ¿Desea recuperar la contraseña?", "Usuario ya existente", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(resp == JOptionPane.YES_OPTION){
					@SuppressWarnings("unused")
					ValidacionPreg vp = new ValidacionPreg(login, textField.getText());
					this.dispose();
				}
			}
		}else
			mostrarMensaje("Campo inválido", JOptionPane.WARNING_MESSAGE);
		
	}

	private boolean validarCampos() {
		// TODO Auto-generated method stub
		return true;
	}

}
