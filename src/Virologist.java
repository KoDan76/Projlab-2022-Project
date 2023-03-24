import java.io.Serializable;
import java.util.ArrayList;

// Projlab 1.0 

/**
 * A virológus irányításáért felelős osztály. Az összes virológusra jellemző adatot osztályt és metódust tartalmazza. 
 * @author Koppa Dani
 *
 */
public class Virologist implements Serializable
{
	private int movepoints = 1;
	
	/**
	 * A virológus sorszáma
	 */
	private int num = 0;
	/**
	 * A virológus inventoryja
	 */
	private Inventory myInventory = new Inventory();
	
	/**
	 * A virológuson éppen ható effektek listája
	 */
	private ArrayList<Effectable> applied_effects = new ArrayList<Effectable>();
	
	/**
	 * A virológus által ismert genomok listája
	 */
	private ArrayList<Genome> known_genomes = new ArrayList<Genome>();
	
	/**
	 * A virológus jelenlegi helye.
	 */
	private Default_Field place = new Default_Field();
	
	private boolean end;

	public void endTurn() {
		end = true;
	}
	
	/**
	 * Paraméter nélküli konstruktor
	 */
	Virologist() {
	}
	
	/**
	 * Paraméteres konstruktor
	 * @param n a virológus sorszáma
	 * @param f a virolgus jelenlegi helye
	 * @param i a virológus inventory-ja 
	 * @param g a virológus által ismert genomok listája. 
	 */
	public Virologist(int n, Default_Field f, Inventory i, ArrayList<Genome> g){
		num = n;
		place = f;
		myInventory = i; 
		known_genomes = g;
		
		/*
		if(n==0)
			known_genomes.add(new Genome("Dancing"));
		known_genomes.add(new Genome("Amnesia"));
		known_genomes.add(new Genome("Protective"));
		known_genomes.add(new Genome("Paralysis"));
		//known_genomes.add(new Genome("Vaccine"));
		myInventory.addEq(new Cape());
		myInventory.addEq(new Gloves());
		myInventory.addAgent(new Protective(3,3));
		myInventory.addAgent(new Paralysis(3,3));
		myInventory.addAgent(new Dancing(3,3));
		*/
	}
	boolean hasBear() {
		for(int i=0;i<applied_effects.size();i++)
			if(applied_effects.get(i).getDescription()=="Bear Agent" || applied_effects.get(i).getDescription()=="Dancing")
				return true;
		return false;
	}
	/**
	 * A virológus egy az övével szomszédos mezőre lép.
	 * @param n a szomzédos mező, ahova mozgunk.
	 */
	public void move(Default_Field n)	
	{
		if(!isParalyzed() && movepoints > 0) {
			if(n.findNeighbor(place)) {
				n.addVir(this);
				place.removeVirologist(this);
				place = n;
				movepoints = 0;
			}			
		}
	}
	public int getMove() {
		return movepoints;
	}
	public void eraseEffects() {
		ArrayList<Effectable> newapplied = new ArrayList<Effectable>();
		for(int i=0;i<this.applied_effects.size();i++) {
			if(applied_effects.get(i).getDescription()=="Axe" || applied_effects.get(i).getDescription()=="Cape" ||
					applied_effects.get(i).getDescription()=="Gloves" || applied_effects.get(i).getDescription()=="Bag"
					|| applied_effects.get(i).getDescription()=="Protection" || applied_effects.get(i).getDescription()=="Bear Agent") {
						
				newapplied.add(applied_effects.get(i));
			}
		}
		this.applied_effects=newapplied;
	}
	/**
	 * megnézi, hogy a virológust védi-e valami ágensek ellen
	 * @return
	 */
	public boolean isProtected() {
		for (Effectable element : applied_effects ){
			if (element.getDescription() == "Protection" ) {
				return true;
			}
			/*if (element.getDescription() == "Gloves") {
				element.effect(this);
				return true;
			}*/
		}
		return false;
	}
	/**
	 * megnézi, hogy a virológus le van-e bénítva
	 * @return
	 */
	public boolean isParalyzed() {
		
		for (Effectable element : applied_effects ){
			if (element.getDescription() == "Paralysis" ) {
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A virológus a megadott ágenst rákeni a megadott virológusra (akár magára)
	 * @param t a célpont
	 * @param a a használandó ágens
	 */
	public void useAgent(Virologist t, Agent a) 
	{
		t.applyEffect(a);
		for( int i=0;i<myInventory.getAgentList().size();i++)
		{
			if(myInventory.getAgentList().get(i)==a) {
				myInventory.getAgentList().remove(i);
			}
		}
		
	}
	
	/**
	 * Elveszi a megcélzott virológus összes elvehető elemét az Inventory-jában.
	 * @param t a megcélzott virológus
	 */
	public void plunder(Virologist t)
	{
		
		if (t.isParalyzed()) {
			t.getInventory().transferInventory(myInventory);
			t.getInventory().getEquipmentList().clear();
		}		
	}
	
	/**
	 *  Megnézi, hogy a virológusnak megvan-e minden Genetikai kódja, vagyis nyert-e.
	 * @return Igaz ha a virológusnak megvan az összes lehetséges genome fajtája. Hamis ha nem. 
	 */
	public boolean checkWin()
	{
		//int hasIt = 0;		
		if(known_genomes.size() >= 5) {return true;}		
		return false;
	}
	
	/**
	 * Visszaadja a virológushoz tartozó Inventory-t.
	 * @return a virológus Invetory-ja
	 */
	public Inventory getInventory()
	{
		return myInventory;
	}
	
	/**
	 * Beteszi az attribútumként kapott Effectable-t az applied_effects listába.
	 * @param e az effekt amit érvényesíteni kell. 
	 */
	public void applyEffect(Effectable e)
	{
		boolean vane=false;
		for(int i=0;i<applied_effects.size();i++)
			if(applied_effects.get(i).getDescription()==e.getDescription())
				vane=true;
		if (!isProtected() && !vane) {
			applied_effects.add(e);
		}		
	}
	
	/**
	 * Visszaadja azt a Default_Field mezőt amin a virológus aktuálisan tartózkodik.
	 * @return a virológus által elfoglalt mező. 
	 */
	public Default_Field getPlace()
	{
		return place;		
	}

	/**
	 * Beteszi az attribútumként kapott Genome-t a known_genomes listába.
	 * @param g a megtanult genom.
	 */
	public void learnGenome(Genome g)
	{
		boolean volte=false;
		for (Genome e : known_genomes) {
			if(g.getDescription()==e.getDescription())
				volte=true;
		}
		if(!volte)
			known_genomes.add(g);
	}
	
	/**
	 * Törli a known_genomes lista elemeit.
	 */
	public void forgetAll() 
	{
		known_genomes.clear();		
	}
	
	/**
	 * A virológus leveszi magáról a megadott felszerélst
	 * @param e a levevendő felszerelés
	 */
	public void unequip(Equipment e)
	{
		removeEffect(e);
	}
	
	/**
	 * A virológus eldobja a felszerelést és azt el is dobja (megsemmisül)
	 * @param e a levevendő felszerelés
	 */
	public void drop(Equipment e) 
	{
		unequip(e);
		myInventory.remove(e);		
	}
	
	/**
	 * Egy effektus megszűnik így az kikerül a jelenleg ható effektusok listájából
	 * @param e A megszűnő effektus.
	 */
	public void removeEffect(Effectable e)
	{
		applied_effects.remove(e);
	}

	/**
	 * Visszaadja a virológus sorszámát
	 * @return a virológus sorszáma
	 */
	public int getNum() {
		return num;
	}
	
	/**
	 * Visszaadja a virológusra ható efektusok listáját
	 * @return virológusra ható efektusok listája
	 */
	public ArrayList<Effectable> getApplied_effects(){		
		return applied_effects;
	}

	/**
	 * Megnézi, hogy milyen effektusok hatása járt le, milyen ágensek jártak 
	 */
	public void StartTurn() {		
		movepoints = 1;
		
		for (int i=0; i<applied_effects.size(); i++) {
			
			String s=applied_effects.get(i).getDescription();
			switch (s)
			{
			case "Axe":
				break;
			case "Gloves":
				applied_effects.get(i).effect(this);
				break;
			case "Cape":
				applied_effects.get(i).effect(this);
				break;
			case "Bag":
				applied_effects.get(i).effect(this);
				break;
				
			default:
				Agent a = (Agent) applied_effects.get(i);
				if (a.getLength() < Game_Controller.getGameController().getTimer().getTurn() ) {
					applied_effects.remove(i);
					i--;
				} else {
					applied_effects.get(i).effect(this);
				}
				
				break;
			}
		}
		
		for (int i = 0; i < myInventory.getAgentList().size(); i++){
			 if ( myInventory.getAgentList().get(i).getExpiration() < Game_Controller.getGameController().getTimer().getTurn() ) {
				myInventory.getAgentList().remove(i);
				i--;
			 }
		}
		while(Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).isParalyzed())
			Game_Controller.getGameController().endTurn();
		/*for(int i=0;i<applied_effects.size();i++) {
			if(applied_effects.get(i).getDescription()=="Bear Agent")
				Game_Controller.getGameController().endTurn();
		}*/
	}

	/**
	 * Felszerel egy felszerelést
	 * @param i a felszerelés sorszáma az effektek listájában
	 */
	public void equip(int i) {
		Equipment eq = myInventory.getEquipment(i);
		boolean volte=false;
		for (Effectable e : applied_effects) {
			if(e.getDescription()==eq.getDescription()) {
				volte=true;
			}
		}
		if(!volte) {
			applied_effects.add(eq);
		}
		
	}

	/**
	 * A paraméterben kapott virológuson használja a baltát
	 * @param v
	 */
	public void useAxe(Virologist v) {
		for (Effectable e : applied_effects) {
			if(e.getDescription()=="Axe") {
				Axe a = (Axe)e;
				e.effect(v);
			}
		}
	}
	/**
	 * visszaadja a virológus által ismert genomokat
	 * @return
	 */
	public ArrayList<Genome> getKnownGenomes() {
		return known_genomes;
	}
}