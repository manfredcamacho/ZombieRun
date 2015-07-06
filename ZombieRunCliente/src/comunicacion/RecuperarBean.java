package comunicacion;

import java.io.Serializable;

public class RecuperarBean implements Serializable{
	
	private static final long serialVersionUID = -8673967930862891834L;
	private String nick = null;
	private String pass = null;
	
	public RecuperarBean(String nick, String pass){
		this.setNick(nick);
		this.setPass(pass);
	}
	
	public RecuperarBean(String nick){
		this(nick, null);
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
