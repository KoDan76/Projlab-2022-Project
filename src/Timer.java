import java.io.Serializable;

// Projlab 1.0 




public class Timer implements Serializable
{
	/**
	 * A jelenlegi k�r
	 */
	private int current_turn;				
	/**
	 * Inicializ�ljuk a konstruktorban a current_turn-�t 1-re.
	 */
	Timer(){				
		current_turn=1;
	}
	/**
	 * n�veli a current_turn �rt�k�t
	 */
	public void increaseTurns()	
	{
		current_turn++; 
	}
	/**
	 * visszaadja a current_turn �rt�k�t
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
