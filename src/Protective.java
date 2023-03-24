import java.io.Serializable;

// Projlab 1.0 



/**
 * Olyan ágens amely megvédi használóját minden más ágens hatásától
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
	 * Azonnal töröl minden más ágenst a paraméter applied_effects listájából.
	 * @param v
	 */
	public void effect(Virologist v)
	{
		v.eraseEffects();
		//v.applyEffect(this);
		setDescription("Protection");
	}
	

}
