package comunicacion;

import java.io.Serializable;

public class ExisteUsuarioBean implements Serializable{

	private static final long serialVersionUID = -472019938563802627L;
	private String nick;
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
