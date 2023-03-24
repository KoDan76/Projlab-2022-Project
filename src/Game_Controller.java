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
	 * a soron l�v� j�t�kos sorsz�ma
	 */
	private int currentplayer;
	/**
	 * A p�ly�n l�v� virol�gusok list�ja
	 */
	private ArrayList<Virologist> allVirologists = new ArrayList<Virologist>();
	/**
	 * A j�t�k Timere
	 */
	private Timer gameTimer = new Timer();
	/**
	 * A mez�ket t�rol� lista
	 */
	private ArrayList<Default_Field> map = new ArrayList<Default_Field>();
	/**
	 * A konroller konstruktora, amiben inicializ�ljuk a k�l�nb�z� tagv�ltoz�kat
	 */
	Game_Controller(int turns){														
		
	}
	/**
	 * �tadja az ir�ny�t�st a k�vetkez� j�t�kosnak, illetve ha nyert a jelenlegi j�t�kos, akkor ezt ki�rja
	 */
	public void endTurn() 													
	{
		if(allVirologists.get(currentplayer).checkWin()) {					/**Itt megn�zz�k, hogy nyert-e a currentplayer*/
			this.endGame();													/**Ha nyert, akkor a j�t�knak v�ge: h�vja az endGame() f�ggv�nyt.*/
		}
		else if(allVirologists.size()-1==currentplayer) {					/**Ha a k�r utols� j�t�kosa fejezi be a j�t�kot, akkor n�velj�k a k�r�k sz�m�t, lenull�zzuk a currentplayert.*/
			currentplayer=0;
			gameTimer.increaseTurns();
		}else {									/**Ha se nem nyert, se nincs k�r v�ge, akkor a currentplayert n�velj�k eggyel.*/
			currentplayer++;
		}
	}
	
	/**
	 * L�trehozza a v�laszt�snak a param�terben specifik�lt mentett t�rk�pek k�z�l az egyikhez sz�ks�ges mez�ket.
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
	 * A beolvasott tulajdons�gokkal rendelkez� mapot lementi f�jlba
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
	 * Befejezi a j�t�kot, �s elind�tja az ehhez sz�ks�ges folyamatokat.
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
	 * Elind�tja a j�t�kot ami az els� j�t�kos els� k�r�vel kezd�dik, elk�sz�ti a mapot a v�lasztott m�retben
	 * @param c
	 */	
	public void startGame() 										
	{
		UserInterface ui= UserInterface.getUI();
		ui.placeVirologists(this, 0);
	}
	
	/**
	 * Hozz�ad egy �j mez�t a t�rolt mez�k list�j�hoz
	 * @param d
	 */	
	public void addField(Default_Field d)								
	{
		map.add(d);
	}
	
	/**
	 * Hozz�ad egy virol�gust a virol�gusok list�j�hoz, ezt param�terk�nt kapja
	 * @param v
	 */
	public void addVir(Virologist v) { 									
		allVirologists.add(v);
	}
	
	/**
	 * visszaadja a j�t�k timer objektum�t
	 * @return
	 */
	public Timer getTimer()													
	{	
		return gameTimer;
		
	}	
	/**
	 * Visszaadja a Game_Controller oszt�lyt amit lehet haszn�lni.
	 * @return
	 */
	static Game_Controller getGameController() {
		return instance;
	}
	/**
	 * Visszaadja az i. mez�t
	 * @param i
	 * @return
	 */
	public Default_Field getField(int i) {
		return map.get(i);
	}
	
	
	/**
	 * visszaadja az i. virol�gust
	 * @param i
	 * @return
	 */
	public Virologist getVir(int i) {
		return allVirologists.get(i) ;
		
	}
	/**
	 * visszaadja a jelenlegi j�t�kost
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
