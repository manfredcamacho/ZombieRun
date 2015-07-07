package ventanas;




import herramientas.cargadorRecursos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import comunicacion.LoginBean;







import comunicacion.RecuperarBean;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Login extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField tfPassword;
	private int cantIntentos = 0;
	private Cliente client;
	private String nick;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		
		/*
		 * ACA GENERAMOS EL CLIENTE Y DEBERIAMOS TRANSPORTARLOS A TODOS LOS FRAMES PARA QUE PUEDA COMUNICARSE EN CUALQUIER LUGAR
		 */
		
		try {
			client = new Cliente();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cerrar();			
			}
		});
		addComponentListener(new ComponentAdapter() {
			public void componentHidden(ComponentEvent e) 
			{
			    /* code run when component hidden*/
			}
			public void componentShown(ComponentEvent e) {
			    tfUsuario.requestFocus();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagenes/zombie_hand.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(477, 329);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirRegistro();
			}
		});
		btnRegistro.setBounds(345, 231, 117, 43);
		btnRegistro.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		contentPane.add(btnRegistro);
		
		JLabel lblZombieRush = new JLabel("Zombie Rush");
		lblZombieRush.setForeground(Color.WHITE);
		lblZombieRush.setFont(cargadorRecursos.ZOMBI_FONT);
		lblZombieRush.setBounds(81, 28, 300, 78);
		contentPane.add(lblZombieRush);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblUsuario.setBounds(91, 140, 89, 24);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a: ");
		lblContrasea.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblContrasea.setForeground(new Color(255, 255, 255));
		lblContrasea.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblContrasea.setBounds(91, 175, 117, 24);
		contentPane.add(lblContrasea);
		
		tfUsuario = new JTextField();
		tfUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyChar() == '\n')
					validar();						
			}
		});
		tfUsuario.setBounds(218, 142, 243, 20);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);
		
		tfPassword = new JPasswordField();
		tfPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyChar() == '\n')
					validar();
			}
		});
		tfPassword.setBounds(218, 177, 243, 20);
		contentPane.add(tfPassword);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				
				if(key.getKeyChar() == '\n')
					validar();
			}
		});
		btnIngresar.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validar();
			}
		});
		btnIngresar.setBounds(218, 231, 117, 43);
		contentPane.add(btnIngresar);
		
		JLabel label = new JLabel();
		label.setBounds(0, 0, 471, 300);
		label.setIcon(cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_4.jpg",label));
		
		
		
		
		contentPane.add(label);
	}
	
	public void cerrar(){
		client.cerrarSocket();
		System.exit(0);
	}
	
	public void abrirLobby(){
		@SuppressWarnings("unused")
		Lobby lobby = new Lobby(this, this.client);
		this.setVisible(false);
	}
	
	public void abrirRegistro(){
		@SuppressWarnings("unused")
		Registro registro = new Registro(this, client);
		this.setVisible(false);
	}
	
	public void abrirValidacionPreg(){
		@SuppressWarnings("unused")
		ValidacionPreg vp = new ValidacionPreg(this, nick);
		this.setVisible(false);	
	}
	
	public void intento(){
		cantIntentos++;
	}
	
	public void setIntento(){
		cantIntentos = 0;
	}
	
	public void validar(){
		//validamos que los campos no esten vacios
		client.enviarMensaje(new LoginBean(tfUsuario.getText(), tfPassword.getPassword()));
		if(tfUsuario.getText().trim().length() == 0 || tfPassword.getPassword().length == 0){
			JOptionPane.showMessageDialog(this,"Complete todos los campos","Campo vacio", JOptionPane.WARNING_MESSAGE);
		}else if( client.leerMensaje().equals("INGRESADO") ){
			abrirLobby();
			tfUsuario.setText("");
			tfPassword.setText("");
		}else{
			intento();
			JOptionPane.showMessageDialog(null, "Usuario / Contraseña incorrecta");
			this.nick = tfUsuario.getText();
			tfUsuario.setText("");
			tfPassword.setText("");
			tfUsuario.requestFocus();
		}
		if( cantIntentos == 3 ){
			client.enviarMensaje(new RecuperarBean(nick));
			if(client.leerMensaje().equals("NICK INVALIDO")){
				JOptionPane.showMessageDialog(this,"El usuario no existe.","Nick Invalido", JOptionPane.WARNING_MESSAGE);
				setIntento();
			}else
				abrirValidacionPreg();
		}
	}
	
	public Cliente getClient(){
		return client;
	}
	
}
