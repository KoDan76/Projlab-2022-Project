import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A virológus összes birtokolt tárgyát és az azok kezeléséhez szükséges
 * metódusokat tároló objektum.
 * 
 * @author urban
 *
 */
public class Inventory implements Serializable{
	/**
	 * Tárolja, hogy mennyi különböző ágens, és felszerelés van az Inventory-ban.
	 */
	private int number_of_items = 0;
	/** Az itt tárolt ágensek */
	private List<Agent> agents;
	/** Az itt tárolt nukleotidok éss aminosavak. */
	private List<Material> my_materials;
	/** Az itt tárolt felszerelések. */
	private List<Equipment> equipment_list;

	/**
	 * Inventory osztály konstruktora, inicializálja a listákat.
	 */
	public Inventory() {
		agents = new ArrayList<Agent>();
		my_materials = new ArrayList<Material>();
		my_materials.add(new Material("Aminosav"));
		my_materials.add(new Material("Nukleotid"));
		equipment_list = new ArrayList<Equipment>();
		}

	/**
	 * Visszaadja a tárolt itemek számát
	 * 
	 * @return Tárolt itemek száma
	 */
	public int getnumber_of_items() {
		return number_of_items;
	}

	/**
	 * Az ádozat inventoryját a támadóéba rakja.
	 * 
	 * @param i cél inventory
	 */
	public void transferInventory(Inventory i) {
		for(Equipment eq : equipment_list) {
			i.addEq(eq);
		}
	}

	/** Törli a virológus inventoryját. */
	public void clearInventory() {
		for (Equipment e : equipment_list) {
			equipment_list.remove(e);
		}
	}

	/**
	 * A paraméterként adott típusú materialt adja vissza.
	 * 
	 * @param i aminosav vagy nukleotid
	 * @return addott típusú material
	 */
	public Material getMaterial(int i) {
		return my_materials.get(i);
	}

	/**
	 * Hozzáadja a kiválasztott equipmentet az inventoryhoz.
	 * 
	 * @param eq hozzáadandó felszerelés
	 */
	public void addEq(Equipment eq) {
		//TODO ez itt nem jó, mert nem ismeri a virológust
		//System.out.println("gained Equipment " + eq.description);
		equipment_list.add(eq);
	}

	/**
	 * Hozzáadja a kiválasztott agentet az inventoryhoz.
	 * 
	 * @param a hozzáadandó ágens
	 */
	public void addAgent(Agent a) {
		agents.add(a);
	}

	/**
	 * Eltávolítja az adott felszerelést és a hozzá tartozó effekteket.
	 * 
	 * @param e Az eltávolítandó felszerelés
	 */
	public void remove(Equipment e) {
		equipment_list.remove(e);
	}

	/**
	 * Visszaadja az Equipment lista egy elemét
	 * 
	 * @param i A sorszám amelyen a keresett felszerelés van.
	 * @return kiválasztott felszerelés
	 */
	public Equipment getEquipment(int i) {
		return equipment_list.get(i);
	}
	/**
	 * visszaadja az inventoryban levo agenseket
	 * @return
	 */
	public List<Agent> getAgentList() {
		return agents ;
	}
	/**
	 * visszaadja az inventoryban levo equipmenteket
	 * @return
	 */
	public List<Equipment> getEquipmentList(){
		return equipment_list;
	}

	/**
	 * visszaadja az inventoryban levo materialokat
	 * @return
	 */
	public List<Material> getMaterialList(){return my_materials;};
}
