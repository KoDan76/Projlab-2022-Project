import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Popup;

public class Element_Vir extends MapElement{
	/**
	 * ez az osztály a mapon kirajzolt Virológusokat reprezentálja (a háromszögek)
	 */
	/**
	 * A Virológus, amit reprezentál
	 */
	Virologist containedVir;
	/**
	 * A konstruktor (ebben át kell adni, hogy melyik játékost jelenti. Véletlenszerû színeket kapnak
	 * @param vir
	 */
	public Element_Vir(Virologist vir){
		containedVir = vir;
		color = new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
	}
	/**
	 * Kirajzolja a háromszöget a megfelelõ helyre, színnel.	
	 */
	@Override
	public void draw(Graphics g,int i) {
		g.setColor(this.color);
		g.fillPolygon(new int[] {posX, posX-10, posX+10}, new int[] {posY, posY+15, posY+15}, 3);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * VIsszaadja a reprezentált virológust
	 * @return
	 */
	public Virologist getVir() {
		return containedVir;		
	}
	/**
	 * ez a függvény mozgatja a háromszögeket a mapon
	 * @param x
	 * @param y
	 */
	public void move(int x, int y) {
		ArrayList<Element_Field>  fields = UserInterface.getUI().getGameView().getFields();
		
		if(containedVir.getMove()==1 ) {
		for(Element_Field f : fields) {
			if(f.hitReg(x, y)) {
				if( containedVir.getPlace().findNeighbor(f.getField())) {
				containedVir.move(f.getField());
				//set(f.getX(),f.getY());
				posX = x; posY = y;
				System.out.println("moved");
				return;
				}
			}
		}
		}
		System.out.println("can't move");
	}
	public void moveRandom() {
		ArrayList<Element_Field>  fields = UserInterface.getUI().getGameView().getFields();
		boolean volt_e=false;
		for(Element_Field f : fields) {
			if(f.containedField==containedVir.getPlace()&&!volt_e) {
				posX = f.posX; posY = f.posY;
				volt_e=true;
			}
		}
		
	}
}