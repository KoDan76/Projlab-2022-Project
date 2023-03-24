import java.awt.Color;
import java.awt.Graphics;

public abstract class MapElement {
	/**
	 * A mapon l�v� elemek �soszt�lya
	 */
	/**
	 * van koordin�t�ja, illetve sz�ne
	 */
	protected int  posX; 
	protected int  posY;
	protected Color color;
	/**
	 * ha megfelel� helyre kattintottunk, akkor true-val, am�gy false-szal t�r vissza.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hitReg(int x, int y) {
		/**
		 * size of the hitbox int each direction from center point (posX, posY)
		 */
		int hitbox = 30;
		if(x >= (posX-hitbox) && x <= (posX+hitbox) && y >= (posY-hitbox) && y <= (posY+hitbox))
			return true;
		return false;
	}
	/**
	 * be�ll�tja az element hely�t
	 * @param x
	 * @param y
	 */
	public void set(int x, int y) {
		posX = x; 
		posY = y;
	}
	/**
	 * visszaadja az elem Y koordin�t�j�t
	 * @return
	 */
	public int getY() {
		return posX;
		
	}
	/**
	 * visszaadja az elem X koordin�t�j�t
	 * @return
	 */
	public int getX() {
		return posY;		
	}
	/**
	 * A kirajzol�s absztrakt f�ggv�nye
	 * @param g
	 * @param i
	 */
	public abstract void draw(Graphics g, int i);
	/**
	 * Az elem friss�t�s�re val� absztrakt f�ggv�ny.
	 */
	public abstract void update();

}
