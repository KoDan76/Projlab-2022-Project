import java.io.Serializable;

// Projlab 1.0 


/**
 * Az Amn�zia okoz� �gens oszt�lya, feladata,
 * hogy effect f�ggv�ny�vel kit�r�lje az adott virol�gus �ltal birtokolt genetikai k�dokat,
 * illetve, hogy t�rolja az �gens lej�rati �s hat�s�nak idej�t
 */
public class Amnesia extends Agent implements Serializable{

	public Amnesia(int exp, int length) {
		super(exp,length);
		setDescription("Amnesia");
	}

	/**
	 * A megadott virol�gus knowledge base-�nek �sszes elem�t t�rli.
	 * @param v
	 */
	public void effect(Virologist v)
	{
			v.forgetAll();

	}
}
