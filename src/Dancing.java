// Projlab 1.0 


import java.io.Serializable;
import java.util.Random;

/**
 * Ágens amely vitustáncra készteti a virológust akin használják. Ez azt jelenti,
 * hogy csak egyetlen véletlenszerûen választott mezõre léphet.
 */
public class Dancing extends Agent implements Serializable
{
	public Dancing(int exp, int length) {
		super(exp, length);
		setDescription("Dancing");
	}

	/**
	 * A v Virológus csak a metódus által random választott szomszédos mezõre léphet.
	 * @param v
	 */
	public void effect(Virologist v)
	{
			Random random = new Random();
			int randInt = random.nextInt(v.getPlace().getNeighborList().size());
			Default_Field field = v.getPlace().getNeighborList().get(randInt);
			v.move(field);
	}



}
