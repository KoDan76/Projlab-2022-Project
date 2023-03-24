import java.io.Serializable;

// Projlab 1.0 




public class Timer implements Serializable
{
	/**
	 * A jelenlegi kör
	 */
	private int current_turn;				
	/**
	 * Inicializáljuk a konstruktorban a current_turn-öt 1-re.
	 */
	Timer(){				
		current_turn=1;
	}
	/**
	 * növeli a current_turn értékét
	 */
	public void increaseTurns()	
	{
		current_turn++; 
	}
	/**
	 * visszaadja a current_turn értékét
	 * @return
	 */
	public int getTurn()			
	{
		return current_turn;
	}
	
	/**
	 * bealitja a jelenlegi kort
	 * @param i
	 */
    public void setTurn(int i) {
		current_turn = i;
    }
}
