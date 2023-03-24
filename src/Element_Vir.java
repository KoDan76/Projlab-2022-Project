import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Popup;

public class Element_Vir extends MapElement{
	/**
	 * ez az oszt�ly a mapon kirajzolt Virol�gusokat reprezent�lja (a h�romsz�gek)
	 */
	/**
	 * A Virol�gus, amit reprezent�l
	 */
	Virologist containedVir;
	/**
	 * A konstruktor (ebben �t kell adni, hogy melyik j�t�kost jelenti. V�letlenszer� sz�neket kapnak
	 * @param vir
	 */
	public Element_Vir(Virologist vir){
		containedVir = vir;
		color = new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
	}
	/**
	 * Kirajzolja a h�romsz�get a megfelel� helyre, sz�nnel.	
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
	 * VIsszaadja a reprezent�lt virol�gust
	 * @return
	 */
	public Virologist getVir() {
		return containedVir;		
	}
	/**
	 * ez a f�ggv�ny mozgatja a h�romsz�geket a mapon
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