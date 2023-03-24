import java.io.*;
import java.util.ArrayList;

// Projlab 1.0 





public class Game_Controller implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Game_Controller instance = new Game_Controller();
	
	/**
	 * a Game_controller konstruktora
	 */
	private Game_Controller() {
		currentplayer=0;
		allVirologists=new ArrayList<Virologist>();
		map= new ArrayList<Default_Field>();
		gameTimer=new Timer();
	}
	
	private ArrayList<Genome> allGenomes = new ArrayList<Genome>();	
	
	ArrayList<Genome> getAllGenomes(){
		return allGenomes;
	}
	
	/**
	 * a soron lévõ játékos sorszáma
	 */
	private int currentplayer;
	/**
	 * A pályán lévõ virológusok listája
	 */
	private ArrayList<Virologist> allVirologists = new ArrayList<Virologist>();
	/**
	 * A játék Timere
	 */
	private Timer gameTimer = new Timer();
	/**
	 * A mezõket tároló lista
	 */
	private ArrayList<Default_Field> map = new ArrayList<Default_Field>();
	/**
	 * A konroller konstruktora, amiben inicializáljuk a különbözõ tagváltozókat
	 */
	Game_Controller(int turns){														
		
	}
	/**
	 * Átadja az irányítást a következõ játékosnak, illetve ha nyert a jelenlegi játékos, akkor ezt kiírja
	 */
	public void endTurn() 													
	{
		if(allVirologists.get(currentplayer).checkWin()) {					/**Itt megnézzük, hogy nyert-e a currentplayer*/
			this.endGame();													/**Ha nyert, akkor a játéknak vége: hívja az endGame() függvényt.*/
		}
		else if(allVirologists.size()-1==currentplayer) {					/**Ha a kör utolsó játékosa fejezi be a játékot, akkor növeljük a körök számát, lenullázzuk a currentplayert.*/
			currentplayer=0;
			gameTimer.increaseTurns();
		}else {									/**Ha se nem nyert, se nincs kör vége, akkor a currentplayert növeljük eggyel.*/
			currentplayer++;
		}
	}
	
	/**
	 * Létrehozza a választásnak a paraméterben specifikált mentett térképek közül az egyikhez szükséges mezõket.
	 * @param choice
	 */	
	public void createMap(int choice)									
	{
		if (choice == 0) {			
			UserInterface ui = UserInterface.getUI();
			int[] fields = ui.mapCreation();		
			
			allGenomes.add(new Genome("Paralysis"));
			allGenomes.add(new Genome("Protective"));
			allGenomes.add(new Genome("Vaccine"));
			allGenomes.add(new Genome("Dancing"));
			allGenomes.add(new Genome("Amnesia"));	
			for (int i=0;i < fields[1];i++) {
				if(i==2) {
					Laboratory l=new Laboratory();	
					l.setToxic();
					map.add(l);
					fields[3]--;
				}
				Laboratory l=new Laboratory();				
				l.generateGenome(allGenomes.get(i%5).getCode_and_name());
				map.add(l);
				fields[3]--;
			}
			
			for (int i=0; i < fields[0]; i++) {
				map.add(new Warehouse());
				fields[3]--;
			}
			
			for (int i=0; i<fields[2]; i++) {
				Shelter s=new Shelter();
				s.generateEq();
				map.add(s);
				fields[3]--;
			}
			
			for (int i=0;i<fields[3];i++) {
				map.add(new Default_Field());
			}
			
			ui.setNeighbors( map );
			int c=ui.mapChoice();
			this.saveMap(c);
		} else {
			try {
				this.loadMap(choice);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadMap(int choice) throws IOException {
		switch(choice) {
		case 1:
			try {
				FileInputStream f = new FileInputStream("bigmap.txt");
				ObjectInputStream in = new ObjectInputStream(f);
				this.map =(ArrayList)in.readObject();
				f.close();
				in.close();
			}catch (Exception ex) {
				System.out.println("Error");
			}
			break;
		case 2:
			try {
				FileInputStream f = new FileInputStream("medmap.txt");
				ObjectInputStream in = new ObjectInputStream(f);
				this.map =(ArrayList)in.readObject();
				f.close();
				in.close();
				}catch (Exception ex) {
					System.out.println("Error");
				}
				break;
		case 3:
			
				FileInputStream f = new FileInputStream("proto_tests/smallmap.txt");
				ObjectInputStream in = new ObjectInputStream(f);
			try {
				this.map =(ArrayList)in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				f.close();
				in.close();			
				
				break;
		}
			
		
	}
	/**
	 * A beolvasott tulajdonságokkal rendelkezõ mapot lementi fájlba
	 */
	
	public void saveMap(int c) {
		try {
			switch (c) {
			case 1:
				FileOutputStream f1 = new FileOutputStream("bigmap.txt");
				ObjectOutputStream out1 =new ObjectOutputStream(f1);
				out1.writeObject(this.map);
				f1.close();
				out1.close();
				break;
			case 2:
				FileOutputStream f2 = new FileOutputStream("medmap.txt");
				ObjectOutputStream out2 =new ObjectOutputStream(f2);
				out2.writeObject(this.map);
				f2.close();
				out2.close();
				break;
			case 3:
				FileOutputStream f3 = new FileOutputStream("smallmap.txt");
				ObjectOutputStream out3 =new ObjectOutputStream(f3);
				out3.writeObject(this.map);
				f3.close();
				out3.close();
				break;
			}
		}
		catch(IOException ex) { 
			//System.out.println("Nem sikerult menteni");
		}
	}
	
	/**
	 * Befejezi a játékot, és elindítja az ehhez szükséges folyamatokat.
	 */	
	public void endGame() 													
	{
		try {
		      FileWriter myWriter = new FileWriter("eredmeny.txt");
		      for(int i=0;i<allVirologists.size();i++)
		    	  myWriter.write("Jatekos "+ (i+1)+" : megtanult genomok szama: "+allVirologists.get(i).getKnownGenomes().size()+'\n');
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	/**
	 * Elindítja a játékot ami az elsõ játékos elsõ körével kezdõdik, elkészíti a mapot a választott méretben
	 * @param c
	 */	
	public void startGame() 										
	{
		UserInterface ui= UserInterface.getUI();
		ui.placeVirologists(this, 0);
	}
	
	/**
	 * Hozzáad egy új mezõt a tárolt mezõk listájához
	 * @param d
	 */	
	public void addField(Default_Field d)								
	{
		map.add(d);
	}
	
	/**
	 * Hozzáad egy virológust a virológusok listájához, ezt paraméterként kapja
	 * @param v
	 */
	public void addVir(Virologist v) { 									
		allVirologists.add(v);
	}
	
	/**
	 * visszaadja a játék timer objektumát
	 * @return
	 */
	public Timer getTimer()													
	{	
		return gameTimer;
		
	}	
	/**
	 * Visszaadja a Game_Controller osztályt amit lehet használni.
	 * @return
	 */
	static Game_Controller getGameController() {
		return instance;
	}
	/**
	 * Visszaadja az i. mezõt
	 * @param i
	 * @return
	 */
	public Default_Field getField(int i) {
		return map.get(i);
	}
	
	
	/**
	 * visszaadja az i. virológust
	 * @param i
	 * @return
	 */
	public Virologist getVir(int i) {
		return allVirologists.get(i) ;
		
	}
	/**
	 * visszaadja a jelenlegi játékost
	 */
	public int getCurrentPlayer() {
		return currentplayer;
	}
	/**
	 * visszaadja a mapot
	 * @return
	 */
	public ArrayList<Default_Field> getMap() {
		return map;
	}
	
	public ArrayList<Virologist> getAllVirologists() {
		return allVirologists;
	}
}
