package comunicacion;

import java.io.Serializable;

public class ActualizarDatosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1157877409111735668L;
	private int id;

	private String nick;
	private char[] password;
	private String pregunta;
	private String respuesta;
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] cs) {
		this.password = cs;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public void setId(int idUsuario) {
		this.id = idUsuario;
	}
	public int getId() {
		return this.id;		
	}
	
}
