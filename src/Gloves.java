import java.io.Serializable;

// Projlab 1.0 



/**
 * Olyan keszty� amellyel a felkent �gens a ken�re visszadobhat�. Harom hasznalat utan eltunik.
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
	 * visszadobja a c�lpontk�nt kapott virol�gusra az �genst
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
	 * visszaadja, hogy h�nyszor haszn�lhatjuk m�g a keszty�t
	 * @return
	 */
	public int getExp() {
		return expiration;
	}
}
