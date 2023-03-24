// Projlab 1.0 
import java.io.Serializable;
import java.util.Random;

/**
 * Különleges mezõ, amely aminosavakat és nukleotidokat adhat a rálépõ virológusoknak. Medvevirussal fertozottek el tudják pusztitani az itteni nyersanyagokat.
 */
public class Warehouse extends Default_Field implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean destroyed=false;
	
	
	/**
	 * növeli egy random mennyiséggel a paraméterként kapott virológus aminosavait és nukleotidjait.
	 */
	
	public int generateLoot() 
	{
		if (UserInterface.IsDeterministic()) {
			return 4;
		} else {
			return new Random().nextInt(6)+2;
		}
		
		
	}
	
	/**
	 * Hozzáadja a Virolgóushoz a Materialokat
	 * @param v a kiválasztott Virológus
	 */
	public boolean transferMaterial(Virologist v)
	{
		if(destroyed)
			return false;
		int loot = generateLoot();
		if(v.getInventory().getMaterial(0).getMax() >=  v.getInventory().getMaterial(0).getQuantity() + loot || v.getInventory().getMaterial(1).getMax() >=  v.getInventory().getMaterial(1).getQuantity() + loot ) {
			v.getInventory().getMaterial(0).add(loot);
			v.getInventory().getMaterial(1).add(loot);
		} else {
			v.getInventory().getMaterial(0).add(v.getInventory().getMaterial(0).getMax() - v.getInventory().getMaterial(0).getQuantity());
			v.getInventory().getMaterial(1).add(v.getInventory().getMaterial(1).getMax() - v.getInventory().getMaterial(1).getQuantity());
		}
		return true;
	}
	
	/**
	 * visszaadja, hogy az adott warehouse el lett-e már pusztítva
	 */
	public boolean getDestroyed() {		
		return destroyed;
	}
	
	/**
	 * A mezõ elpusztítása
	 */
	public void Destroy() {
		destroyed=true;
	}
	/**
	 * Leírás beállítása
	 */
	public Warehouse(){
        setDescription("Warehouse");
    }
}

