import java.awt.Color;
import java.awt.Graphics;

public class Element_Field  extends MapElement{
	/**
	 * Ez az osztály a mapon lévõ Fieldeket reprezentálja
	 */
	
	/**
	 * Ez a Field, amit reprezentál
	 */
	Default_Field containedField;
	/**
	 * Ez a konstruktora, alapból fekete színt kap.
	 * meg kell neki adni a fieldet, amit reprezentálnia kell
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
	 * Kirajzolja a megfelelõ helyre a mezõt
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
	 * visszaadja a tárolt mezõt
	 * @return
	 */
	public Default_Field getField() {
		return containedField ;
	}
}
