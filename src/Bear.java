import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * �gens amely medvet�ncra k�szteti a virol�gust aki megfert�z�dik.
 * Ez azt jelenti, hogy csak egyetlen v�letlenszer�en v�lasztott mez�re l�phet,
 * minden �tj�ba es� rakt�rban elpuszt�tja az ott l�v� anyagokat �s
 * minden �tj�ba es� virol�gust megken a medvev�russal (kifogyhatatlan a k�szlete).
 */
public class Bear extends Agent implements Serializable{

	/**
	 * A medvev�rus param�teres konstruktora
	 * @param exp
	 * @param length
	 */
    public Bear(int exp, int length) {
        super(exp, length);
        setDescription("Bear Agent");
    } 
    /**
     * A medvev�rus nem param�teres konstruktora
     */
    public Bear() {
    	super(66666666,666666666);
    	setDescription("Bear Agent");
    }
    /**
     * Medvet�ncra k�nyszer�ti a virol�gust am�g meg nem hal.
     * Amennyiben a medv�re ker�l a sor az azonnal egy v�letlen ir�nyba l�p tov�bb.
     * @param v
     */
    public void effect(Virologist v){

            Random random = new Random();
            int randInt = random.nextInt(v.getPlace().getNeighborList().size());
            Default_Field field = v.getPlace().getNeighborList().get(randInt);
            v.move(field);
            if(field.getDescription() == "Warehouse"){
                ((Warehouse)v.getPlace()).Destroy();

            }
            ArrayList<Virologist> volt=new ArrayList<Virologist>();
            volt.add(v);
            for(Virologist vir : v.getPlace().getStandingHere()){
	        	if(!volt.contains(vir)) {
	        		volt.add(vir);
	        		vir.applyEffect(this);
	        	}
	        	
        }


    }
}
