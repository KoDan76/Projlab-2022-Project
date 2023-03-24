import java.io.Serializable;

// Projlab 1.0 




/**
 * Az �sszes effektust kifejteni k�pes objektum�rt felel�s oszt�ly.
 */
public interface Effectable
{

	public abstract String getDescription();
	/**
	 * Absztrakt met�dus az �gensek k�l�nb�z� hat�sainak megval�s�t�s�ra.
	 * @param v
	 */
	public abstract void effect(Virologist v);
}
