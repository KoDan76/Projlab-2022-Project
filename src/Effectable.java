import java.io.Serializable;

// Projlab 1.0 




/**
 * Az összes effektust kifejteni képes objektumért felelõs osztály.
 */
public interface Effectable
{

	public abstract String getDescription();
	/**
	 * Absztrakt metódus az ágensek különbözõ hatásainak megvalósítására.
	 * @param v
	 */
	public abstract void effect(Virologist v);
}
