import java.io.Serializable;
import java.util.ArrayList;

// Projlab 1.0 



/**
 * A k�l�nleges mez�k �soszt�lya illetve minden nem k�l�nleges mez� ilyen. 
Egyszerre t�bb Virol�gus lehet rajta illetve a p�lya �sszek�t�tts�g��rt felel.
 */
public class Default_Field implements Serializable  
{
	
	protected String description="Field";
	
	/**
	 * a szomsz�dos mez�k szomsz�ds�gi-list�j�t tartalmazza.
	 */
	private ArrayList<Default_Field> neighbor_list = new ArrayList<Default_Field>(); 
	/**
	 * A mez�n tal�lhat� Virol�gusok list�ja.
	 */
	protected ArrayList<Virologist> standing_here = new ArrayList<Virologist>(); 

	/**
	 * Hozz�ad egy virol�gust a virol�gusok list�j�hoz
	 * @param v a hozz�adni k�v�nt Virol�gus
	 */
	public void addVir(Virologist v) 
	{
		standing_here.add(v);
	}
	/**
	 * Kiveszi a megadott virol�gust a list�b�l.
	 * @param a hozz�adni k�v�nt Virol�gus
	 */
	public void removeVirologist(Virologist v)  
	{
		standing_here.remove(v);
	}
	/**
	 * Megn�zi, hogy az adott mez� a szomsz�ds�gi list�ban van-e. 
	 * @param n a keresend� ter�let
	 */
	public boolean findNeighbor(Default_Field n) 
	{
		 return neighbor_list.contains(n);
	}
	/**
	 * Megn�zi, hogy az adott virol�gus a mez�n van-e.
	 * @param v a keresend� Virol�gus
	 */
	public boolean findVir(Virologist v) 
	{
		 return standing_here.contains(v);
	}
	/**
	 * �j szomsz�dos mez�t ad a mez�h�z
	 * @param default_Field
	 */
	public void addNeighbor(Default_Field default_Field) {		
		neighbor_list.add(default_Field);
	}
	/**
	 * megadja a mez� le�r�s�t
	 * @return
	 */
	public String getDescription() {
		
		return description;
	}
	/**
	 * be�ll�tja a field le�r�s�t
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * visszaadja a mez� szomsz�dainak list�j�t
	 * @return
	 */
    public ArrayList<Default_Field> getNeighborList() {
		return neighbor_list;
    }
    /**
     * visszaadja a mez�n �ll� virol�gusok list�j�t
     * @return
     */
	public ArrayList<Virologist> getStandingHere() {
		return standing_here;
	}
	/**
	 * ki�rja a mez� szomsz�dait
	 * @return
	 */
	public String writeNeighbors() {
		String out = "";
		for (Default_Field n : neighbor_list) {
			out += Game_Controller.getGameController().getMap().indexOf(n) + ", ";
			
		}
		return out;
	}
	/**
	 * ki�rja az �sszes itt �ll� virol�gus adat�t
	 */
	public String toString() {
		String out = "";
		for (Virologist v : standing_here) {
			out += v.getNum() + " , ";
		}
		return out;
		
	}
}
