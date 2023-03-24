import java.io.Serializable;

// Projlab 1.0 


/**
 * Vakcina, amely nem csinál semmit
 */
public class Vaccine extends Agent implements Serializable
{
	/**Az alap konstruktor*/
	public Vaccine() {
		super(666,666);
		setDescription("Vaccine");
	}

	/**
	 * beoltodik a virologus
	 * @param v
	 */
	public void effect(Virologist v)
	{
		System.out.println("3 olats utan mehetsz bulizni");

	}
}
