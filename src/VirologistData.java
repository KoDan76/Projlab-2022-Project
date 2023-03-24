import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;


public class VirologistData extends AbstractTableModel {
	
    
    /**
     * visszaadja a sorok sz�m�t, ami itt az adott cell�n �ll� virol�gusok sz�ma + 1 a fejl�c miatt
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
     * visszaadja az oszlopok sz�m�t
     */
    @Override
    public int getColumnCount() {
        return 5;
    }
    /**
     * visszaadja a megfelel� cell�k tartalm�t
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Game_Controller gc = Game_Controller.getGameController();
        int v = gc.getCurrentPlayer();
        Virologist vir1 = gc.getVir(v);
        Inventory inventory = vir1.getInventory();
        ArrayList<Virologist> players = vir1.getPlace().getStandingHere();
    	if(rowIndex==0) {
    		switch(columnIndex) {				/**A fejl�c cell�i*/
    			case 0: return "Player";
                case 1: return "Genomes";
                case 2: return "Aminoacid";
                case 3: return "Nucleotid";
                default: return "Effects";
    		}
    		
    	}
        Virologist vir=players.get(rowIndex-1);
        switch (columnIndex){
            case 0: return vir.getNum()+1;		/**A virol�gus sorsz�ma*/
            case 1: 
            	String str= " ";					/**A virol�gusn�l l�v� �gensek list�ja*/
            	for(int i=0;i<vir.getInventory().getAgentList().size();i++) {
            		str+=vir.getInventory().getAgentList().get(i).getDescription();
            		if(i!= vir.getInventory().getAgentList().size()-1)
            			str+=", ";
            	}
            	return str;
            case 2: return vir.getInventory().getMaterial(0).getQuantity();	  /**A virol�gusn�l l�v� aminosav mennyis�g*/
            case 3: return vir.getInventory().getMaterial(1).getQuantity();   /**A virol�gusn�l l�v� nukleotid mennyis�g*/
            default:							/**A virol�guson l�v� effektek list�ja stringg� alak�tva*/
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
     * Az oszlopokban l�v� itemek oszt�lyait adja vissza.
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

