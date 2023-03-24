import java.io.Serializable;

// Projlab 1.0 



/**
 * Olyan Equipment amely a virológus megszerezhetõ Material-jainak számát növeli.
 * Egyszerre csak egy Bag hatása érvényesül.
 */
public class Bag extends Equipment implements Serializable
{
	/**
	 * ha a virolgógus használta-e már a táskát
	 */
	private boolean used = false;

	public Bag(){
		setDescription("Bag");
	}

	/**
	 * a megadott paraméter my_materials gyûjteményének összes elemére meghívja a setMax függvényt.
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
	 * ha használtuk, igazra állítja a used értékét
	 */
	public void setUsed() {
		this.used = true;
	}
	/**
	 * megadja a used értékét
	 * @return
	 */
	public boolean getUsed(){
		return this.used;
	}
}
