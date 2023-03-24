import java.io.Serializable;

/**
 * A virológus által birtokolt nukleotidokat és aminosavakat tároló objektum.
 * Benne megadva, hogy az adott anyagból mennyi lehet, és mennyi van, valamint a
 * metódusokat melyek ezek bővítésére szolgálnak.
 * 
 * @author urban
 *
 */
public class Material implements Serializable{
	/** Az anyag jelenleg birtokolt mennyisége. */
	private int quantity;
	/** az anyag maximális mennyisége, amit birtokolni lehet. */
	private int max;
	/** Az anyag neve amelyet reprezentál */
	private String name;

	/**
	 * Konstruktor
	 * @param name Anyag neve amit reprezentál
	 */
	public Material(String name) {
		this.name = name;
		max = 20; 
	}
	
	/**
	 * Paraméterben megadott mennyiséget ad hozzá a tárolt anyaghoz.
	 * 
	 * @param num hozzáadandó mennyiség
	 */
	public void add(int num) {
		quantity+=num;
	}

	/**
	 * Paraméterben megadott mennyiséget vesz el a tárolt anyagból
	 * 
	 * @param num elvevendő mennyiség
	 */
	public void dec(int num) {
		quantity-=num;
	}

	/**
	 * Adott számra állítja a Material max attribútumát.
	 * 
	 * @param num beállítandó mennyiség
	 */
	public void setMax(int num) {
		max=num;
	}

	/**
	 * Visszaadja a maximum mennyiséget.
	 * 
	 * @return maximum mennyiség
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Visszaadja a Material jelenlegi mennyiségét.
	 * 
	 * @return jelenlegi mennyiség
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 *
	 */
	public String getName() {
		return name;
	}
}
