package clasesPrincipales;

import java.io.Serializable;

import javax.swing.table.DefaultTableModel;

public class TablaRanking extends DefaultTableModel implements Serializable{

	 //Indica si la casilla identificada por fila y columna es editable
	 public boolean isCellEditable( int fila,int col ) { 
		 return( false ); 
	 }
}