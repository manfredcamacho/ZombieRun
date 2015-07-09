package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import clasesPrincipales.*;
import comunicacion.*;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Escenario extends JFrame {

	private static final long serialVersionUID = 4013032887277483935L;

	private JPanel contentPane;

	//PANEL DONDE SE DIBUJA EL ESCENARIO
	private JPanel escenario; 
	
	// SE ALMACENA HACIA DONDE SE QUIERE MOVER EL JUGADOR Y SE INICIALIZA CADA VEZ QUE LLEGA EL MAPA
	private int direccion; // 0 NO SE MOVIO - 1 ARRIBA - 2 ABAJO - 3 IZQUIERDA - 4 DERECHA
	
	// OBJETO EL CUAL NOS PERMITE MANTENER LA COMUNICACION CON EL SERVIDOR
	private Cliente clientSocket;
	
	// TIMER
	private JLabel lblTimer;
	private Timer timer;
	
	
	//IMAGENES
	private static final ImageIcon humano = new ImageIcon( Escenario.class.getResource("/humano.jpg"));
	private static final ImageIcon zombie = new ImageIcon( Escenario.class.getResource("/zombie.jpg"));
	private static final ImageIcon muro = new ImageIcon( Escenario.class.getResource("/muro.jpg"));

	
	
	
	public Escenario( Cliente client) {
		
		direccion = 0;
		clientSocket = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIzquierda = new JButton("");
		btnIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				direccion = 3;
			}
		});
		btnIzquierda.setIcon(new ImageIcon(Escenario.class.getResource("/imagenes/izquierda.jpg")));
		btnIzquierda.setSelectedIcon(null);
		btnIzquierda.setBounds(26, 498, 50, 23);
		contentPane.add(btnIzquierda);
		
		JButton btnAbajo = new JButton("");
		btnAbajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				direccion = 2;
			}
		});
		btnAbajo.setIcon(new ImageIcon(Escenario.class.getResource("/imagenes/abajo.jpg")));
		btnAbajo.setBounds(86, 521, 50, 30);
		contentPane.add(btnAbajo);
		
		JButton btnDerecha = new JButton("");
		btnDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				direccion = 4;
			}
		});
		btnDerecha.setIcon(new ImageIcon(Escenario.class.getResource("/imagenes/derecha.jpg")));
		btnDerecha.setBounds(146, 498, 50, 23);
		contentPane.add(btnDerecha);
		
		JButton btnArriba = new JButton("");
		btnArriba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				direccion = 1;
			}
		});
		btnArriba.setIcon(new ImageIcon(Escenario.class.getResource("/imagenes/arriba.jpg")));
		btnArriba.setBounds(86, 472, 50, 30);
		contentPane.add(btnArriba);
		
		escenario = new JPanel();
		escenario.setBackground(Color.BLACK);
		escenario.setLayout(null);
			
		
		lblTimer = new JLabel("4");
		lblTimer.setBounds(335, 236, 46, 14);
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTimer.setForeground(Color.WHITE);
		contentPane.add(lblTimer);
	
		
		contentPane.add(escenario);
		dibujarEscenario(((EscenarioBean)clientSocket.leerMensaje()).getMapa(),
				((EscenarioBean)clientSocket.leerMensaje()).getTamX(), 
				((EscenarioBean)clientSocket.leerMensaje()).getTamY());
		HiloDeJuego();
		
	}
	
	
	// ESTE METODO SE ENCARGA DE DIBUJAR EL MAPA
	public void dibujarEscenario( Figura[][] mapa, int tamX, int tamY ){
		
		
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa.length; j++) {
				if( mapa[i][j] instanceof Bloque )
					System.out.print("B ");
				else if ( mapa[i][j] instanceof Personaje )
					System.out.print("P ");
				else
					System.out.print("  ");
			}
			System.out.println();
		}
		
		escenario.removeAll();
		escenario.setBounds(10, 10, tamX*25, tamY*25);
		
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa.length; j++) {
				
				if( mapa[i][j] instanceof Bloque ){
					JLabel muroAux = new JLabel(muro);
					muroAux.setBounds(i*25, j*25, 25, 25);
					escenario.add(muroAux);
				}else if( mapa[i][j] instanceof Personaje ){
					if( ((Personaje)mapa[i][j]).esZombie() ){
						JLabel muroAux = new JLabel(zombie);
						muroAux.setBounds(i*25, j*25, 25, 25);
						escenario.add(muroAux);
					}else{
						JLabel muroAux = new JLabel(humano);
						muroAux.setBounds(i*25, j*25, 25, 25);
						escenario.add(muroAux);
					}
				}
			}
		}
		escenario.revalidate();
		escenario.repaint();
	}
	
	
	// ESTE METODO SE ENCARGA DE ENVIAR UNA DIRECCION
	public void enviarDireccion(){
		
	}
	
	

	public void HiloDeJuego(){
		Thread hiloDeJuego = new Thread( new Runnable(){
			public void run(){
				
				while( true ){
					System.out.println("Esperando el escenario...");
					Object peticion = clientSocket.escuchar();
					System.out.println("Termino de escuchar");
					System.out.println("Se recibio : " + peticion.getClass().getName());
					
					if( peticion instanceof EscenarioBean ){
						System.out.println("Se recibio el escenario");
				
						dibujarEscenario(((EscenarioBean)peticion).getMapa(),
										 ((EscenarioBean)peticion).getTamX(),
										 ((EscenarioBean)peticion).getTamY());
					}else if( peticion.equals("DIRECCION")){
						
						
						timer = new Timer(1000, new ActionListener() {			
							int elapsedSeconds = 4;
							@Override
							public void actionPerformed(ActionEvent e) {
								elapsedSeconds--;
						        lblTimer.setText(Integer.toString(elapsedSeconds));
						        if(elapsedSeconds == 0){
						           elapsedSeconds = 4;
						           timer.restart();
						        }
							}
						});
						timer.start();

						
						
						System.out.println("NOS PIDIERON DIRECCION");
						try {
							clientSocket.getOut().writeObject(new DireccionBean(direccion,clientSocket.getJugador()));
							System.out.println("ENVIAMOS NUESTRA DIRECCION");
							direccion = 0;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
				
			}
		});
		hiloDeJuego.start();
	}

}