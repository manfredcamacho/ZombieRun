package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import principales.*;
import comunicacion.*;


public class Escenario extends JFrame {

	private JPanel contentPane;

	// SE ALMACENA HACIA DONDE SE QUIERE MOVER EL JUGADOR Y SE INICIALIZA CADA VEZ QUE LLEGA EL MAPA
	private int direccion;
	
	// OBJETO EL CUAL NOS PERMITE MANTENER LA COMUNICACION CON EL SERVIDOR
	private Cliente clientSocket;
	
	//IMAGENES
	
	
	
	

	public Escenario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	
	// ESTE METODO SE ENCARGA DE DIBUJAR EL MAPA
	public void dibujarEscenario( Figura[][] mapa ){
		
	}
	
	
	// ESTE METODO SE ENCARGA DE ENVIAR UNA DIRECCION
	public void enviarDireccion(){
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Escenario frame = new Escenario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

}