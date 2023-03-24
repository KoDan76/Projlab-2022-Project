import java.awt.Color;
import java.awt.Graphics;

public class Element_Field  extends MapElement{
	/**
	 * Ez az oszt�ly a mapon l�v� Fieldeket reprezent�lja
	 */
	
	/**
	 * Ez a Field, amit reprezent�l
	 */
	Default_Field containedField;
	/**
	 * Ez a konstruktora, alapb�l fekete sz�nt kap.
	 * meg kell neki adni a fieldet, amit reprezent�lnia kell
	 * @param cont
	 */
	Element_Field(Default_Field cont){
		containedField = cont;
		color = Color.BLACK;
	}
	/**
	 * visszaadja, hogy a warehouse destroyed-e
	 * @return
	 */
	public boolean isDestroyed() {
		if (containedField.getDescription() == "Warehouse") {
			return ((Warehouse)containedField).getDestroyed();
		}
		return false; 
	}
	
	/**
	 * Kirajzolja a megfelel� helyre a mez�t
	 */
	@Override
	public void draw(Graphics g, int i ) {
		g.setColor(this.color);
		String out = ""+i;
		out += isDestroyed() ? " (D)" : "";
		g.drawString(out, posX, posY);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * visszaadja a t�rolt mez�t
	 * @return
	 */
	public Default_Field getField() {
		return containedField ;
	}
}
