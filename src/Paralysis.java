import java.io.Serializable;

// Projlab 1.0 



/**
 * Megb�n�tja a virol�gust �gy az n�h�ny k�rig nem k�pes mozogni
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
	 * A param�terben kapott virol�gus azonnal befejezi a k�r�t
	 * @param v
	 */
	public void effect(Virologist v)
	{
		v.endTurn();
	}
}