import java.io.Serializable;
import java.util.Random;

// Projlab 1.0 

/**
 * Olyan Equipment amely a virológust egy bizonyos hatás fokig védi az ágensek hatásaitól
 */
public class Cape extends Equipment implements Serializable
{
	Cape(){
		description = "Cape";
	}
	
	
	/**
	 * 82.6% eséllyel semlegesíti az Ágens hatását a virológuson.
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
