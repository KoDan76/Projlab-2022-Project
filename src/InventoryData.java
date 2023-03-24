import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InventoryData extends AbstractTableModel {
    /**
     * Lekeri az aktualis jatekos altal birtokolt itemek listait
     */

    public Effectable getItemInRow(int row) {
    	Game_Controller gc = Game_Controller.getGameController();
        int i = gc.getCurrentPlayer();
        Virologist vir = gc.getVir(i);
        Inventory inventory = vir.getInventory();
        ArrayList<Material> invmat = new ArrayList<>(inventory.getMaterialList());
        ArrayList<Agent> invag = new ArrayList<>(inventory.getAgentList());
        ArrayList<Equipment> inveq = new ArrayList<>(inventory.getEquipmentList());
    	if(invag.size() <= row) {
    		return inveq.get(row - invag.size());
    	}
    	return invag.get(row);
    }
    
    /**
     * Beallitja hany sora legyen a tablazatnak
     * @return
     */
    @Override
    public int getRowCount() {
    	Game_Controller gc = Game_Controller.getGameController();
        int i = gc.getCurrentPlayer();
        Virologist vir = gc.getVir(i);
        Inventory inventory = vir.getInventory();
        ArrayList<Material> invmat = new ArrayList<>(inventory.getMaterialList());
        ArrayList<Agent> invag = new ArrayList<>(inventory.getAgentList());
        ArrayList<Equipment> inveq = new ArrayList<>(inventory.getEquipmentList());
        return 2+ invag.size()+inveq.size()+1;
    }

    /**
     * be�ll�tja,hogy 5 oszlop legyen
     * @return oszlopok sz�ma
     */
    @Override
    public int getColumnCount() {
        return 5;
    }
    /**
     * Ez a f�ggv�ny �p�ti fel tulajdonk�ppen a t�bl�zatot. Felt�lti a megfelel� adatokkal.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Game_Controller gc = Game_Controller.getGameController();
        int i = gc.getCurrentPlayer();
        Virologist vir = gc.getVir(i);
        Inventory inventory = vir.getInventory();
        /**
         * Mivel az inventory 3 k�l�nb�z� ArrayListb�l �ll, ezt kezelni kell.
         */
        ArrayList<Material> invmat = new ArrayList<>(inventory.getMaterialList());
        ArrayList<Agent> invag = new ArrayList<>(inventory.getAgentList());
        ArrayList<Equipment> inveq = new ArrayList<>(inventory.getEquipmentList());
        /**
         * A fejl�c felt�lt�se
         */
    	if (rowIndex==0) {
    		switch(columnIndex) {
    		case 0: return "Item_Type";
    		case 1: return "Item_Name";
            case 2: return "Expiration";
            case 3: return "Effect_Length";
            default: return "Uses_left";
    		}
    	}
    	/**
    	 * Mivel csup�n 2-f�le Material t�pus� dolog van, ez�rt ha a sorindex 3-n�l kisebb, 
    	 * akkor kell ezeket visszaadni (a fejl�c a plusz 1)
    	 */
    	else if (rowIndex<3) {
    		switch(columnIndex) {
    		case 0: return "Material";
    		case 1: if(rowIndex==1) return "Aminoacid"; else return "Nucleotid";
    		case 2: return "";
    		case 3: return "";
    		default : return null;
    		}
    	}
    	/**
    	 * Ha a sorindex kisebb, mint a material m�rete + a fejl�c m�rete + az �gensek sz�ma, akkor m�g
    	 * az �genseket tartalmaz� ArrayListben vagyunk (m�g ellen�rizni kell, hogy ez a size nem =0, mivel
    	 * ha igen, akkor a materialok ut�n egyb�l equipmentek j�nnek.
    	 */
    	else if(rowIndex<invag.size()+3 && invag.size()!=0) {
    		Agent ag=invag.get(rowIndex-3);
    		switch(columnIndex) {
    		case 0: return "Agent";
    		case 1:
                return ag.getDescription();
            case 2:
                return ag.getExpiration();
            case 3:
                return ag.getLength();
            default:
            	return 1;
    		}
    	}
    	/**
    	 * Ez az eset akkor k�vetkezik be, ha az equipmenteket tartalmaz� ArrayListben vagyunk.
    	 */
    	else if (rowIndex>=invag.size()+3 || rowIndex>2 && invag.size()==0) {
    		Equipment eq=inveq.get(rowIndex-3-invag.size());
    		switch(columnIndex) {
	    		case 0: return "Equipment";
	    		case 1: return eq.getDescription();
	    		case 2: return "";
	    		case 3: return "";
	    		default: {
	    			if (eq.getDescription()=="Axe")
	    				return 1;
	    			else if(eq.getDescription()=="Gloves") {	/**A keszty�ket csak 3-szor lehet haszn�lni, ez�rt ennek a v�ltoz�s�t jel�lni kell*/
	    				Gloves g=(Gloves)eq;
	    				return g.getExp();		
	    			}
	    				
	    		}
    		}
    	}
		return " " ;
    }

    /**
     * Beallitja az oszlopok neveit
     * @param column
     * @return
     */
    @Override
    public String getColumnName(int column){
        switch (column){
            case 0: return "Item_Type";
            case 1: return "Item_Name";
            case 2: return "Expiration";
            case 3: return "Effect_Length";
            default: return "Uses_left";
        }
    }
    /**
     * Beallitja hogy a cell�kat ne lehessen �t�ll�tani
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}
    /**
     * Beallitja az oszlopok osztajait
     * @param columnIndex
     * @return
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return String.class;
            case 1: return String.class;
            case 2: return Integer.class;
            case 3: return Integer.class;
            default: return Integer.class;
        }
    }

    }

