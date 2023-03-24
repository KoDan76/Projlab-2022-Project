import java.io.Serializable;

// Projlab 1.0 



/**
 * Olyan kesztyû amellyel a felkent ágens a kenõre visszadobható. Harom hasznalat utan eltunik.
 */
public class Gloves extends Equipment implements Serializable
{
	/**
	 * Hasznalatok szamat jelzo valtozo.
	 */
	int expiration = 3;
	public Gloves() {
		this.setDescription("Gloves");
	}
	/**
	 * visszadobja a célpontként kapott virológusra az ágenst
	 * @param v
	 */
	public void effect(Virologist v)
	{
		expiration--;
		v.getPlace().getStandingHere();
	}

	/**
	 * Csokkenti a hasznalatok lehetseges szamat.
	 */
	public void decExperation(){
		expiration -= 1;
	}
	/**
	 * visszaadja, hogy hányszor használhatjuk még a kesztyût
	 * @return
	 */
	public int getExp() {
		return expiration;
	}
}
