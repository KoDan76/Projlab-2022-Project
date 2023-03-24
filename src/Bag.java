import java.io.Serializable;

// Projlab 1.0 



/**
 * Olyan Equipment amely a virol�gus megszerezhet� Material-jainak sz�m�t n�veli.
 * Egyszerre csak egy Bag hat�sa �rv�nyes�l.
 */
public class Bag extends Equipment implements Serializable
{
	/**
	 * ha a virolg�gus haszn�lta-e m�r a t�sk�t
	 */
	private boolean used = false;

	public Bag(){
		setDescription("Bag");
	}

	/**
	 * a megadott param�ter my_materials gy�jtem�ny�nek �sszes elem�re megh�vja a setMax f�ggv�nyt.
	 * @param v
	 */
	public void effect(Virologist v)
	{
		if(this.getUsed() == false){
		v.getInventory().getMaterial(0).setMax(20);
		v.getInventory().getMaterial(1).setMax(20);
		setUsed();
		}

	}
	/**
	 * ha haszn�ltuk, igazra �ll�tja a used �rt�k�t
	 */
	public void setUsed() {
		this.used = true;
	}
	/**
	 * megadja a used �rt�k�t
	 * @return
	 */
	public boolean getUsed(){
		return this.used;
	}
}
