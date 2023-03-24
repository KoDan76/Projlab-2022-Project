import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;


public class VirologistData extends AbstractTableModel {
	
    
    /**
     * visszaadja a sorok számát, ami itt az adott cellán álló virológusok száma + 1 a fejléc miatt
     */
    
    @Override
    public int getRowCount() {
    	Game_Controller gc = Game_Controller.getGameController();
        int i = gc.getCurrentPlayer();
        Virologist vir = gc.getVir(i);
        Inventory inventory = vir.getInventory();
        ArrayList<Virologist> players = vir.getPlace().getStandingHere();
        return players.size()+1;
    }
    /**
     * visszaadja az oszlopok számát
     */
    @Override
    public int getColumnCount() {
        return 5;
    }
    /**
     * visszaadja a megfelelõ cellák tartalmát
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Game_Controller gc = Game_Controller.getGameController();
        int v = gc.getCurrentPlayer();
        Virologist vir1 = gc.getVir(v);
        Inventory inventory = vir1.getInventory();
        ArrayList<Virologist> players = vir1.getPlace().getStandingHere();
    	if(rowIndex==0) {
    		switch(columnIndex) {				/**A fejléc cellái*/
    			case 0: return "Player";
                case 1: return "Genomes";
                case 2: return "Aminoacid";
                case 3: return "Nucleotid";
                default: return "Effects";
    		}
    		
    	}
        Virologist vir=players.get(rowIndex-1);
        switch (columnIndex){
            case 0: return vir.getNum()+1;		/**A virológus sorszáma*/
            case 1: 
            	String str= " ";					/**A virológusnál lévõ ágensek listája*/
            	for(int i=0;i<vir.getInventory().getAgentList().size();i++) {
            		str+=vir.getInventory().getAgentList().get(i).getDescription();
            		if(i!= vir.getInventory().getAgentList().size()-1)
            			str+=", ";
            	}
            	return str;
            case 2: return vir.getInventory().getMaterial(0).getQuantity();	  /**A virológusnál lévõ aminosav mennyiség*/
            case 3: return vir.getInventory().getMaterial(1).getQuantity();   /**A virológusnál lévõ nukleotid mennyiség*/
            default:							/**A virológuson lévõ effektek listája stringgé alakítva*/
            	String st= "";
            	for(int i=0;i<vir.getApplied_effects().size();i++) {
            		st+=vir.getApplied_effects().get(i).getDescription();
            		if(i!= vir.getApplied_effects().size()-1)
            			st+=", ";
            		
            	}
            	
            	return st;
        }
    }
    /**
     * Az oszlopokban lévõ itemek osztályait adja vissza.
     */

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Integer.class;
            case 3: return Integer.class;
            default: return String.class;
        }
    }


}

