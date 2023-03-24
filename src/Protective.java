import java.io.Serializable;

// Projlab 1.0 



/**
 * Olyan �gens amely megv�di haszn�l�j�t minden m�s �gens hat�s�t�l
 */
public class Protective extends Agent implements Serializable
{
	/**
	 * A protective agent konstruktora
	 * @param exp
	 * @param l
	 */
	Protective(int exp, int l){
		super(exp, l);
		setDescription("Protection");
	}
	/**
	 * Azonnal t�r�l minden m�s �genst a param�ter applied_effects list�j�b�l.
	 * @param v
	 */
	public void effect(Virologist v)
	{
		v.eraseEffects();
		//v.applyEffect(this);
		setDescription("Protection");
	}
	

}
