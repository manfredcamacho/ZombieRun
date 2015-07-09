package comunicacion;

import java.io.Serializable;

public class DatosUsuarioBean implements Serializable{
	
	private String nick;
	private String preguntaSecreta;
	private String respuestaSecreta;
	private String password;

	private static final long serialVersionUID = 1L;
	
	public DatosUsuarioBean(String nick){
		this.nick = nick;
	}
	
	public String getPreguntaSecreta() {
		return preguntaSecreta;
	}

	public void setPreguntaSecreta(String preguntaSecreta) {
		this.preguntaSecreta = preguntaSecreta;
	}

	public String getRespuestaSecreta() {
		return respuestaSecreta;
	}

	public void setRespuestaSecreta(String respuestaSecreta) {
		this.respuestaSecreta = respuestaSecreta;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@Override
	public String toString() {
		return nick+password+preguntaSecreta+respuestaSecreta;
	}
	
}
