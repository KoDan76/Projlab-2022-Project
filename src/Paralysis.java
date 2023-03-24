import java.io.Serializable;

// Projlab 1.0 



/**
 * Megbénítja a virológust így az néhány körig nem képes mozogni
 */
public class Paralysis extends Agent implements Serializable
{
	/**
	 * A paralysis agent konrstuktora
	 * @param exp
	 * @param length
	 */

	public Paralysis(int exp, int length) {
		super(exp, length);
		setDescription("Paralysis");
	}

	/**
	 * A paraméterben kapott virológus azonnal befejezi a körét
	 * @param v
	 */
	public void effect(Virologist v)
	{
		v.endTurn();
	}
}