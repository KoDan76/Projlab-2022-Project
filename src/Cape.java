import java.io.Serializable;
import java.util.Random;

// Projlab 1.0 

/**
 * Olyan Equipment amely a virol�gust egy bizonyos hat�s fokig v�di az �gensek hat�sait�l
 */
public class Cape extends Equipment implements Serializable
{
	Cape(){
		description = "Cape";
	}
	
	
	/**
	 * 82.6% es�llyel semleges�ti az �gens hat�s�t a virol�guson.
	 * @param v
	 */
	public void effect(Virologist v)
	{		
		/*Timer timer = Game_Controller.getGameController().getTimer();
		timer.getTurn();*/
		int chance = new Random().nextInt(1000);
		if(chance <= 826) { v.applyEffect(new Protective(0, 1)); }
	}
}
