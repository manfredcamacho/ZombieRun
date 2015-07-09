package ventanas;

import herramientas.cargadorRecursos;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import comunicacion.ActualizarDatosBean;
import comunicacion.DatosUsuarioBean;
import comunicacion.ExisteUsuarioBean;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.util.Arrays;

public class Configuracion extends JFrame {

	private static final long serialVersionUID = -6545411950962728552L;
	private JPanel contentPane;
	private JTextField textFieldNick;
	private JPasswordField textFieldPasswdAct;
	private JPasswordField textFieldConfirmNewPasswd;
	private Lobby lobby;
	private JTextField tfPreguntaSecreta;
	private JTextField tfRespuestaSecreta;
	private DatosUsuarioBean datos;

	public Configuracion(final Lobby lob) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Configuracion.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		
		lobby = lob;
		setSize(417, 388);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConfiguracion = new JLabel("CONFIGURACION", SwingConstants.CENTER);
		lblConfiguracion.setFont(cargadorRecursos.ZOMBI_FONT);
		lblConfiguracion.setForeground(Color.WHITE);
		lblConfiguracion.setBounds(0, 11, 411, 43);
		contentPane.add(lblConfiguracion);
		
		JLabel lblNick = new JLabel("Nick:");
		lblNick.setForeground(Color.WHITE);
		lblNick.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblNick.setBounds(26, 87, 200, 14);
		contentPane.add(lblNick);
		
		JLabel lblContraseaActual = new JLabel("Contrase\u00F1a Actual:");
		lblContraseaActual.setForeground(Color.WHITE);
		lblContraseaActual.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblContraseaActual.setBounds(26, 205, 200, 14);
		contentPane.add(lblContraseaActual);
		
		textFieldNick = new JTextField();
		textFieldNick.setBounds(248, 81, 139, 20);
		contentPane.add(textFieldNick);
		textFieldNick.setColumns(10);
		
		JLabel lblConfirmarContrasea = new JLabel("Confirmar Contrase\u00F1a:");
		lblConfirmarContrasea.setForeground(Color.WHITE);
		lblConfirmarContrasea.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblConfirmarContrasea.setBounds(26, 240, 200, 14);
		contentPane.add(lblConfirmarContrasea);
		
		textFieldPasswdAct = new JPasswordField();
		textFieldPasswdAct.setBounds(248, 199, 139, 20);
		contentPane.add(textFieldPasswdAct);
		textFieldPasswdAct.setColumns(10);
		
		textFieldConfirmNewPasswd = new JPasswordField();
		textFieldConfirmNewPasswd.setBounds(248, 237, 139, 20);
		contentPane.add(textFieldConfirmNewPasswd);
		textFieldConfirmNewPasswd.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
		btnGuardar.setBounds(69, 291, 130, 43);
		contentPane.add(btnGuardar);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atras();
			}
		});
		btnAtras.setBounds(213, 291, 130, 43);
		contentPane.add(btnAtras);
		
		JLabel lblPreguntaSecreta = new JLabel("Pregunta Secreta");
		lblPreguntaSecreta.setForeground(Color.WHITE);
		lblPreguntaSecreta.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblPreguntaSecreta.setBounds(26, 124, 200, 14);
		contentPane.add(lblPreguntaSecreta);
		
		JLabel lblRespuestaSecreta = new JLabel("Respuesta Secreta");
		lblRespuestaSecreta.setForeground(Color.WHITE);
		lblRespuestaSecreta.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblRespuestaSecreta.setBounds(26, 163, 200, 14);
		contentPane.add(lblRespuestaSecreta);
		
		tfPreguntaSecreta = new JTextField();
		tfPreguntaSecreta.setBounds(248, 121, 139, 20);
		contentPane.add(tfPreguntaSecreta);
		tfPreguntaSecreta.setColumns(10);
		
		tfRespuestaSecreta = new JTextField();
		tfRespuestaSecreta.setBounds(248, 160, 139, 20);
		contentPane.add(tfRespuestaSecreta);
		tfRespuestaSecreta.setColumns(10);
		
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, 411, 358);
		fondo.setIcon( cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_5.jpg", fondo));
		
		contentPane.add(fondo);
		
		//cargamos los datos desde la BD
		this.lobby.getCliente().enviarMensaje(new DatosUsuarioBean(this.lobby.getLogin().getNick()));
		datos = (DatosUsuarioBean) this.lobby.getCliente().leerMensaje();
		this.textFieldNick.setText(datos.getNick());
		this.tfPreguntaSecreta.setText(datos.getPreguntaSecreta());
		this.tfRespuestaSecreta.setText(datos.getRespuestaSecreta());
		this.textFieldPasswdAct.setText(datos.getPassword());
		this.textFieldConfirmNewPasswd.setText(datos.getPassword());
		this.setVisible(true);
	}
	
	protected void guardarDatos() {
		if(validarCampos()){
			if(!textFieldNick.getText().equals(datos.getNick())){//si modifico el usuario
				this.lobby.getCliente().enviarMensaje(new ExisteUsuarioBean(textFieldNick.getText()));//pregunto si existe el nuevo usuario
				if(this.lobby.getCliente().leerMensaje().equals("EXISTE")){
					mostrarMensaje("El nick ingresado no esta disponible.", JOptionPane.WARNING_MESSAGE);
				}else{
					datos.setNick(textFieldNick.getText());//si no existe, entonces le cambio el nick. 
					//el seteo anterior es para que al salir de este if entre al siguiente y acutualize
				}
			}
			if(textFieldNick.getText().equals(datos.getNick())){
				ActualizarDatosBean ad = new ActualizarDatosBean();
				ad.setId(lobby.getLogin().getIdUsuario());
				ad.setNick(textFieldNick.getText());
				ad.setPregunta(tfPreguntaSecreta.getText());
				ad.setRespuesta(tfRespuestaSecreta.getText());
				ad.setPassword(textFieldPasswdAct.getPassword());
				this.lobby.getCliente().enviarMensaje(ad);
				mostrarMensaje("Se guardaron los cambios satisfactoriamente.", JOptionPane.INFORMATION_MESSAGE);	
			}
		}
		
	}

	public void atras(){
		lobby.setVisible(true);
		dispose();
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
	
	private boolean validarCampos() {
		if(textFieldNick.getText().trim().length() == 0 || tfPreguntaSecreta.getText().trim().length() == 0 || tfRespuestaSecreta.getText().trim().length() == 0
				|| textFieldPasswdAct.getPassword().length == 0 || textFieldConfirmNewPasswd.getPassword().length == 0){
			JOptionPane.showMessageDialog(this,"Complete todos los campos","Campo vacio", JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(!Arrays.equals(textFieldPasswdAct.getPassword(),textFieldConfirmNewPasswd.getPassword())){
			JOptionPane.showMessageDialog(this,"Las contraseñas no coinciden","Contraseña invalida", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}
