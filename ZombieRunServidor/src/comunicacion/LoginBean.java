package comunicacion;

import java.io.Serializable;

public class LoginBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -616238696036193888L;
	private String user;
	private String password;
	private int idUsuario;
	
	public LoginBean(String user, char[] pass){
		this.user = user;
		this.password = new String(pass);
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

}
