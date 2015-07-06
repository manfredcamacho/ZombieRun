package comunicacion;

import java.io.Serializable;

public class ValidarRespuestaBean implements Serializable{

	private static final long serialVersionUID = -5178350032425495107L;
	private String nick;
	private String respuesta;
	
	public ValidarRespuestaBean(String nick, String respuesta){
		this.nick = nick;
		this.respuesta = respuesta;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
