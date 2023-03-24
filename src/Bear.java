import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Ágens amely medvetáncra készteti a virológust aki megfertõzõdik.
 * Ez azt jelenti, hogy csak egyetlen véletlenszerûen választott mezõre léphet,
 * minden útjába esõ raktárban elpusztítja az ott lévõ anyagokat és
 * minden útjába esõ virológust megken a medvevírussal (kifogyhatatlan a készlete).
 */
public class Bear extends Agent implements Serializable{

	/**
	 * A medvevírus paraméteres konstruktora
	 * @param exp
	 * @param length
	 */
    public Bear(int exp, int length) {
        super(exp, length);
        setDescription("Bear Agent");
    } 
    /**
     * A medvevírus nem paraméteres konstruktora
     */
    public Bear() {
    	super(66666666,666666666);
    	setDescription("Bear Agent");
    }
    /**
     * Medvetáncra kényszeríti a virológust amíg meg nem hal.
     * Amennyiben a medvére kerül a sor az azonnal egy véletlen irányba lép tovább.
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
