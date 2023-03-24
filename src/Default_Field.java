import java.io.Serializable;
import java.util.ArrayList;

// Projlab 1.0 



/**
 * A különleges mezõk õsosztálya illetve minden nem különleges mezõ ilyen. 
Egyszerre több Virológus lehet rajta illetve a pálya összekötöttségéért felel.
 */
public class Default_Field implements Serializable  
{
	
	protected String description="Field";
	
	/**
	 * a szomszédos mezõk szomszédsági-listáját tartalmazza.
	 */
	private ArrayList<Default_Field> neighbor_list = new ArrayList<Default_Field>(); 
	/**
	 * A mezõn található Virológusok listája.
	 */
	protected ArrayList<Virologist> standing_here = new ArrayList<Virologist>(); 

	/**
	 * Hozzáad egy virológust a virológusok listájához
	 * @param v a hozzáadni kívánt Virológus
	 */
	public void addVir(Virologist v) 
	{
		standing_here.add(v);
	}
	/**
	 * Kiveszi a megadott virológust a listából.
	 * @param a hozzáadni kívánt Virológus
	 */
	public void removeVirologist(Virologist v)  
	{
		standing_here.remove(v);
	}
	/**
	 * Megnézi, hogy az adott mezõ a szomszédsági listában van-e. 
	 * @param n a keresendõ terület
	 */
	public boolean findNeighbor(Default_Field n) 
	{
		 return neighbor_list.contains(n);
	}
	/**
	 * Megnézi, hogy az adott virológus a mezõn van-e.
	 * @param v a keresendõ Virológus
	 */
	public boolean findVir(Virologist v) 
	{
		 return standing_here.contains(v);
	}
	/**
	 * új szomszédos mezõt ad a mezõhöz
	 * @param default_Field
	 */
	public void addNeighbor(Default_Field default_Field) {		
		neighbor_list.add(default_Field);
	}
	/**
	 * megadja a mezõ leírását
	 * @return
	 */
	public String getDescription() {
		
		return description;
	}
	/**
	 * beállítja a field leírását
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * visszaadja a mezõ szomszédainak listáját
	 * @return
	 */
    public ArrayList<Default_Field> getNeighborList() {
		return neighbor_list;
    }
    /**
     * visszaadja a mezõn álló virológusok listáját
     * @return
     */
	public ArrayList<Virologist> getStandingHere() {
		return standing_here;
	}
	/**
	 * kiírja a mezõ szomszédait
	 * @return
	 */
	public String writeNeighbors() {
		String out = "";
		for (Default_Field n : neighbor_list) {
			out += Game_Controller.getGameController().getMap().indexOf(n) + ", ";
			
		}
		return out;
	}
	/**
	 * kiírja az összes itt álló virológus adatát
	 */
	public String toString() {
		String out = "";
		for (Virologist v : standing_here) {
			out += v.getNum() + " , ";
		}
		return out;
		
	}
}
