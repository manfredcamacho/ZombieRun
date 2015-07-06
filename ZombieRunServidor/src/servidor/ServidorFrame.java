package servidor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;

public class ServidorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private JTextArea mensajes;
	private JLabel titulo;

	public ServidorFrame() {
		setTitle("SERVIDOR ZOMBIE RUN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setBounds(0, 0, 444, 365);
		contentPane.add(scrollPane);
		
		mensajes = new JTextArea();
		mensajes.setEditable(false);
		scrollPane.setViewportView(mensajes);
		
		titulo = new JLabel("IP DEL SERVIDOR: ");
		titulo.setFont(new Font("Consolas", Font.PLAIN, 13));
		titulo.setBounds(10, 404, 294, 14);
		contentPane.add(titulo);
		setResizable(false);
		setVisible(true);
	}

	public void mostrarMensajeFrame(String str){
		this.mensajes.append(str+"\n");
		this.mensajes.setCaretPosition(this.mensajes.getDocument().getLength());
	}
	
	public void setIPServer(String ip){
		this.titulo.setText("IP DEL SERVIDOR: "+ip);
	}
}
