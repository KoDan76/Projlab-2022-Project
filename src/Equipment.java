import java.io.Serializable;

// Projlab 1.0 

/**
 * Absztrakt osztály ami megvalósítja az Effectable interfészt és az összes felszerelés közös õse,
 * azok attributumait és metódusait tartalmazza
 */
public abstract class Equipment implements Effectable, Serializable
{

	/**
	 * A felszerelés leírása.
	 */
	protected String description;

	/**
	 * Örökölve az interfészbõl.
	 * @param v
	 */
	public void effect(Virologist v) {
		
	}

	/**
	 * lekeri a felszereles leirasat
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * beallitja a felszereles leirasat
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
