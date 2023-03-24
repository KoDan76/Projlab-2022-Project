import java.io.Serializable;

// Projlab 1.0 

/**
 * Absztrakt oszt�ly ami megval�s�tja az Effectable interf�szt �s az �sszes felszerel�s k�z�s �se,
 * azok attributumait �s met�dusait tartalmazza
 */
public abstract class Equipment implements Effectable, Serializable
{

	/**
	 * A felszerel�s le�r�sa.
	 */
	protected String description;

	/**
	 * �r�k�lve az interf�szb�l.
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
