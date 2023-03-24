import java.io.Serializable;

// Projlab 1.0 

/**
 *  Absztrakt oszt�ly, mely megval�s�tja az  Effectable interf�szt, �s a k�l�nb�z� �gensek �se.
 */
public abstract class Agent implements Effectable, Serializable
{
	public Agent(int exp, int length){
		Timer timer = Game_Controller.getGameController().getTimer();
		this.expiration = timer.getTurn() + exp;
		this.length = timer.getTurn()+length;
	}
	
	/**
	 * Amennyiben az �genst r�kenik valakire hat�sa ennyi k�rig tart.
	 */
	private int length;

	/**
	 * az �gens ennyi k�r alatt j�r le. Azaz ennyi k�r ut�n mag�t�l elt�nik az inventoryb�l.
	 */
	private int expiration;

	/**
	 * Az �gens k�pess�g�nek le�r�sa.
	 */
	private  String description;

	/**
	 * Absztrakt met�dus az �gensek k�l�nb�z� hat�sainak megval�s�t�s�ra.
	 * @param v
	 */
	public abstract void effect(Virologist v);

	/**
	 * csokkenti az experationt
	 */
	public void decreaseExp()
	{
		--expiration;
	}

	/**
	 * beallitja az agens leirasat
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * beallitja meddig al el az agens a taskaban
	 * @param expiration
	 */
	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	/**
	 * beallitja az agens hatasanak hosszat
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * lekeri meddig al el az agens a taskaban
	 * @return
	 */
	public int getExpiration() {
		return expiration;
	}

	/**
	 * lekeri meddig tart az agens hatasa
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * lekeri az agens leirasat
	 * @return
	 */
	public String getDescription() {
		return description;
	}
}
