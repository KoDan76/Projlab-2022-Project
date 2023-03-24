// Projlab 1.0 
import java.io.Serializable;
import java.util.Random;

/**
 * K�l�nleges mez�, amely aminosavakat �s nukleotidokat adhat a r�l�p� virol�gusoknak. Medvevirussal fertozottek el tudj�k pusztitani az itteni nyersanyagokat.
 */
public class Warehouse extends Default_Field implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean destroyed=false;
	
	
	/**
	 * n�veli egy random mennyis�ggel a param�terk�nt kapott virol�gus aminosavait �s nukleotidjait.
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
	 * Hozz�adja a Virolg�ushoz a Materialokat
	 * @param v a kiv�lasztott Virol�gus
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
	 * visszaadja, hogy az adott warehouse el lett-e m�r puszt�tva
	 */
	public boolean getDestroyed() {		
		return destroyed;
	}
	
	/**
	 * A mez� elpuszt�t�sa
	 */
	public void Destroy() {
		destroyed=true;
	}
	/**
	 * Le�r�s be�ll�t�sa
	 */
	public Warehouse(){
        setDescription("Warehouse");
    }
}

