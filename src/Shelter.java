// Projlab 1.0 



/**
 * K�l�nleges mez� amely k�l�nb�z� Equipmenteket tartalmazhat.
 */
import java.io.Serializable;
import java.util.Random;
public class Shelter extends Default_Field implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static int generate = 2;
	
	/**
	 * Az itt t�rolt felszerel�s
	 */	
	private Equipment e;
	/**
	 * L�trehozza, hogy milyen fajta Equipmentet t�rolunk itt.
	 */
	public void generateEq() 
	{
		Random rand = new Random();		
		int random;
		if(UserInterface.IsDeterministic()) {
			random = generate+1;
			generate -= generate <= 0 ? 0 : 1; 
		} else {
			random =rand.nextInt(4);
		}		
		switch(random) {
		case 0:e = new Cape();break;
		case 1:e = new Bag();break;
		case 2:e = new Gloves();break;
		case 3:e = new Axe();break;
		}		
	}
	/**
	 * K�sz�t egy hard copy-t az itt t�rolt Equipmentb�l a c�lzott Virol�gus inventor�-j�ban,
	 */
	public void transferEq(Virologist v) 
	{
		boolean volte=false;
		Inventory myInventory =v.getInventory();
		for(int i=0;i<myInventory.getEquipmentList().size();i++)
			if(e.getDescription()==myInventory.getEquipmentList().get(i).getDescription())
				volte=true;
		if(myInventory.getnumber_of_items()<=4 && !volte)
			myInventory.addEq(e);	
	}
	/**
	 * le�r�s be�ll�t�sa
	 */
	public Shelter(){
        setDescription("Shelter");
    }
}
