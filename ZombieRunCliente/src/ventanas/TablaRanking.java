package ventanas;

import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TablaRanking extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private Object datos[][] = {
	    {"1°","L33T","1020"},
	    {"2°","KillerATXT","1010"},
	    {"3°","LadyFeline","988"},
	    {"4°","Friendl2","888"},
	    {"5°","Xendox","884"},
	    {"6°","Rush92","880"},
	    {"7°","Aki35","700"},
	    {"8°","Zoydee","600"},
	    {"9°","luptatorthenk","500"},
	    {"10°","BazooHD","488"},
	    {"11°","Yukister","452"},
	    {"12°","Otaku3","420"},
	    {"13°","SirGabby","400"},
	    {"14°","quiquiro","356"},
	    {"15°","DayDreeam","355"},
	    {"16°","KillerATXT","321"},
	    {"17°","Eowynns","320"},
	    {"18°","Qu3S7i0N","200"},
	    {"19°","JD_X2R2","120"},
	    {"20°","Ndx","101"},
	    {"21°","xWildOnesx","98"},
	    {"22°","BazooHD","65"},
	    {"23°","Yukister","60"},
	    {"24°","Otaku3","20"},
	    {"25°","SirGabby","17"},
	   
	      
	};
	
	private String[] columnNames = {"Posicion","Usuario","Puntos"};
	
	private JFrame frame;
  
	 class TablaListener implements TableModelListener {
	    public void tableChanged( TableModelEvent evt ) {
	    
	      
	      for( int i=0; i < datos.length; i++ ) {
	    	  
	        for( int j=0; j < datos[0].length; j++ )
	          System.out.print( datos[i][j] + " " );
	        System.out.println();
	      }
	    }
	 }
 
	 // Constructor
	 public TablaRanking(JFrame frame) {
		 this.frame = frame;
		 addTableModelListener( new TablaListener() );
	 }
	 
	 // Devuelve el nombre de una columna
	 public String getColumnName(int col) {
	      return columnNames[col];
	    }

	 
	 // Devuelve el número de columnas de la tabla
	 public int getColumnCount() { 
		 return( datos[0].length ); 
	 }
  
	 // Devuelve el número de filas de la tabla
	 public int getRowCount() { 
		 int res = datos.length;
		 if(frame instanceof Estadistica)
			 res = 20;
		 return res;
	 }
  
	 // Devuelve el valor de una determinada casilla de la tabla identificada mediante fila y columna
	 public Object getValueAt( int fila,int col ) { 
		 return( datos[fila][col] ); 
	 }
  
	 // Cambia el valor que contiene una determinada casilla de la tabla
	 public void setValueAt( Object valor,int fila,int col ) {
		 datos[fila][col] = valor;
		 // Indica que se ha cambiado
		 fireTableDataChanged();
	 }
	 
	 //Indica si la casilla identificada por fila y columna es editable
	 public boolean isCellEditable( int fila,int col ) { 
		 return( false ); 
	 }
}       
 