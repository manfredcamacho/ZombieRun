package comunicacion;

import java.io.Serializable;
import clasesPrincipales.TablaRanking;
public class RankingBean implements Serializable{

	private static final long serialVersionUID = -8377376334021710503L;
	
	private TablaRanking modelo;
	private boolean top20 = false;
	
	public RankingBean(TablaRanking modelo){
		this(modelo, false);
	}
	
	public RankingBean(TablaRanking modelo, boolean top20){
		this.modelo = (TablaRanking)modelo;
		this.top20 = top20;
	}
	
	public boolean isTop20() {
		return top20;
	}

	public void setTop20(boolean top20) {
		this.top20 = top20;
	}

	public TablaRanking getModelo() {
		return modelo;
	}

	public void setModelo(TablaRanking modelo) {
		this.modelo = modelo;
	}
}
