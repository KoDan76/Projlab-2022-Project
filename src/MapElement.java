import java.awt.Color;
import java.awt.Graphics;

public abstract class MapElement {
	/**
	 * A mapon lévõ elemek õsosztálya
	 */
	/**
	 * van koordinátája, illetve színe
	 */
	protected int  posX; 
	protected int  posY;
	protected Color color;
	/**
	 * ha megfelelõ helyre kattintottunk, akkor true-val, amúgy false-szal tér vissza.
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
	 * beállítja az element helyét
	 * @param x
	 * @param y
	 */
	public void set(int x, int y) {
		posX = x; 
		posY = y;
	}
	/**
	 * visszaadja az elem Y koordinátáját
	 * @return
	 */
	public int getY() {
		return posX;
		
	}
	/**
	 * visszaadja az elem X koordinátáját
	 * @return
	 */
	public int getX() {
		return posY;		
	}
	/**
	 * A kirajzolás absztrakt függvénye
	 * @param g
	 * @param i
	 */
	public abstract void draw(Graphics g, int i);
	/**
	 * Az elem frissítésére való absztrakt függvény.
	 */
	public abstract void update();

}
