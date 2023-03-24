import java.io.Serializable;

// Projlab 1.0 


/**
 * Az Amnézia okozó ágens osztálya, feladata,
 * hogy effect függvényével kitörölje az adott virológus által birtokolt genetikai kódokat,
 * illetve, hogy tárolja az ágens lejárati és hatásának idejét
 */
public class Amnesia extends Agent implements Serializable{

	public Amnesia(int exp, int length) {
		super(exp,length);
		setDescription("Amnesia");
	}

	/**
	 * A megadott virológus knowledge base-ének összes elemét törli.
	 * @param v
	 */
	public void effect(Virologist v)
	{
			v.forgetAll();

	}
}
