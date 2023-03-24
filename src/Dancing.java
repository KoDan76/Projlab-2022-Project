// Projlab 1.0 


import java.io.Serializable;
import java.util.Random;

/**
 * �gens amely vitust�ncra k�szteti a virol�gust akin haszn�lj�k. Ez azt jelenti,
 * hogy csak egyetlen v�letlenszer�en v�lasztott mez�re l�phet.
 */
public class Dancing extends Agent implements Serializable
{
	public Dancing(int exp, int length) {
		super(exp, length);
		setDescription("Dancing");
	}

	/**
	 * A v Virol�gus csak a met�dus �ltal random v�lasztott szomsz�dos mez�re l�phet.
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
