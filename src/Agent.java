import java.io.Serializable;

// Projlab 1.0 

/**
 *  Absztrakt osztály, mely megvalósítja az  Effectable interfészt, és a különbözõ ágensek õse.
 */
public abstract class Agent implements Effectable, Serializable
{
	public Agent(int exp, int length){
		Timer timer = Game_Controller.getGameController().getTimer();
		this.expiration = timer.getTurn() + exp;
		this.length = timer.getTurn()+length;
	}
	
	/**
	 * Amennyiben az ágenst rákenik valakire hatása ennyi körig tart.
	 */
	private int length;

	/**
	 * az Ágens ennyi kör alatt jár le. Azaz ennyi kör után magától eltûnik az inventoryból.
	 */
	private int expiration;

	/**
	 * Az ágens képességének leírása.
	 */
	private  String description;

	/**
	 * Absztrakt metódus az ágensek különbözõ hatásainak megvalósítására.
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
