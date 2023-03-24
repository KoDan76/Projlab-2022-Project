import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GenomeData extends AbstractTableModel {
    /**
     * Lekeri az aktualis jatekos altal ismert genomok listajat
     */
    /**
     * Beallitja hany sora legyen a tablazatnak
     * @return
     */
    @Override
    public int getRowCount() {
    	Game_Controller gc = Game_Controller.getGameController();
        int i = gc.getCurrentPlayer();
        Virologist vir = gc.getVir(i);
        ArrayList<Genome> genomeArrayList = vir.getKnownGenomes();
        return genomeArrayList.size()+1;
    }

    /**
     * Beallitja hogy 4 oszlop legyen egy a kivalasztashoz egy a genome nevenek es
     * 1-1 az aminosavnak es nucleotidnak
     * @return
     */
    @Override
    public int getColumnCount() {
    	return 3;
    }

    /**
     * Feltolzi a tablazatot
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Game_Controller gc = Game_Controller.getGameController();
        int i = gc.getCurrentPlayer();
        Virologist vir = gc.getVir(i);
        ArrayList<Genome> genomeArrayList = vir.getKnownGenomes();
    	if(rowIndex==0) {
    		switch(columnIndex) {
    			case 0: return "Name";
    			case 1: return "Aminoacid";
    			default: return "Nucleotid";
    		}
    	}
        Genome genome = genomeArrayList.get(rowIndex-1);
        switch (columnIndex){
            case 0: return genome.getCode_and_name();
            case 1: return genome.getAminoacid();
            default: return genome.getNucleotid();
        }
    }

    /**
     * Beallitja az oszlopok neveit
     * @param column
     * @return
     */
    @Override
    public String getColumnName(int column){
    	return "";

    }
    /**
     * Beallitja az oszlopok osztajait
     * @param columnIndex
     * @return
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return String.class;
            case 1: return Integer.class;
            default: return Integer.class;
        }
    }

    /**
     * Beallitja hogy a cellákat ne lehessen átállítani
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
