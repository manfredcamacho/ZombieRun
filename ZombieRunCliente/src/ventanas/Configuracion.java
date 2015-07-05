package ventanas;

import herramientas.cargadorRecursos;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class Configuracion extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNick;
	private JTextField textFieldPasswdAct;
	private JTextField textFieldNewPasswd;
	private JTextField textFieldConfirmNewPasswd;
	private Lobby lobby;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Configuracion(final Lobby lob) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Configuracion.class.getResource("/imagenes/zombie_hand.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		this.setVisible(true);
		lobby = lob;
		setSize(417, 425);
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
		lblContraseaActual.setBounds(26, 131, 200, 14);
		contentPane.add(lblContraseaActual);
		
		textFieldNick = new JTextField();
		textFieldNick.setText("L33T");
		textFieldNick.setBounds(248, 81, 139, 20);
		contentPane.add(textFieldNick);
		textFieldNick.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nueva Contrase\u00F1a:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblNewLabel.setBounds(26, 168, 200, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblConfirmarContrasea = new JLabel("Confirmar Contrase\u00F1a:");
		lblConfirmarContrasea.setForeground(Color.WHITE);
		lblConfirmarContrasea.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		lblConfirmarContrasea.setBounds(26, 207, 200, 14);
		contentPane.add(lblConfirmarContrasea);
		
		textFieldPasswdAct = new JTextField();
		textFieldPasswdAct.setBounds(248, 125, 139, 20);
		contentPane.add(textFieldPasswdAct);
		textFieldPasswdAct.setColumns(10);
		
		textFieldNewPasswd = new JTextField();
		textFieldNewPasswd.setBounds(248, 162, 139, 20);
		contentPane.add(textFieldNewPasswd);
		textFieldNewPasswd.setColumns(10);
		
		textFieldConfirmNewPasswd = new JTextField();
		textFieldConfirmNewPasswd.setBounds(248, 201, 139, 20);
		contentPane.add(textFieldConfirmNewPasswd);
		textFieldConfirmNewPasswd.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarMensaje("Se guardaron los cambios satisfactoriamente.", JOptionPane.INFORMATION_MESSAGE);
				atras();
			}
		});
		btnGuardar.setBounds(68, 306, 130, 43);
		contentPane.add(btnGuardar);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setFont(cargadorRecursos.HPSIMPLIFIED_FONT);
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atras();
			}
		});
		btnAtras.setBounds(208, 306, 130, 43);
		contentPane.add(btnAtras);
		
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, 411, 396);
		fondo.setIcon( cargadorRecursos.cargarImagenParaLabel("recursos/imagenes/fondo_5.jpg", fondo));
		
		contentPane.add(fondo);
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
}
