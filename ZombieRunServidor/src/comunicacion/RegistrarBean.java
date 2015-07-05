package comunicacion;

import java.io.Serializable;

public class RegistrarBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 252719454715932143L;
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
	
	@Override
	public String toString() {
		return nick+password+pregunta+respuesta;
	}
	
}
