package clasesPrincipales;

import java.io.Serializable;

import javax.swing.table.DefaultTableModel;

public class TablaRanking extends DefaultTableModel implements Serializable{

	private static final long serialVersionUID = 3715331269332582401L;

	//Indica si la casilla identificada por fila y columna es editable
	 public boolean isCellEditable( int fila,int col ) { 
		 return( false ); 
	 }
}