import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public  class UserInterface {	
	
	private MainWindow gameView; 
	
	/**
	 * 
	 */
	private static UserInterface instance = new UserInterface();
	
	/**
	 * 
	 */
	private static boolean deterministic = false; 
	
	/**
	 * 
	 * @return
	 */
	public static boolean IsDeterministic() {
		return deterministic;
	}
	
	/**
	 * 
	 * @return
	 */
	public static UserInterface getUI() {
		return instance;		 
	}
	
	/**
	 * 
	 */
	public static void invalidCommandEx() {
		System.out.println("Invalid arguments or command");
	}
	
	/**
	 * 
	 */
	private UserInterface() {
		scan = new Scanner(System.in);
	}
	/**
	 * Nagyon menő beolvasós scanner
	 */
	public Scanner scan;
	
	/**
	 * Nem annyira menő string tömb a commandok tárolására.
	 */
	public String[] commands; 
		
	FileInputStream executeFile = null;
	
	PrintStream outFile = null;

	private MainWindow mainWindow;
	
	/**
	 * Parancsfeldolgozó UI
	 * @param gc
	 */
	public void defaultUI(Game_Controller gc, String string) {
		scan = new Scanner(string);
		boolean run = true;
		while (run) {
			scanLine();
			switch (commands[0]) {
			case "reset_stream":
				scan = new Scanner(System.in);
				break;
			case "execute":
				try {
					executeFile = new FileInputStream(commands[1]);
					System.out.println("execute success");
					scan = new Scanner(executeFile);
				} catch (FileNotFoundException e) {
					System.out.println("execute failure");
					e.printStackTrace();
				}
				break;
			case "output":
				try {
					outFile = new PrintStream(commands[1]);
					System.setOut(outFile);
				} catch (FileNotFoundException e) {
					System.out.println("No such file");
					e.printStackTrace();
				}
				break;
			case "save":
				gc.saveMap(Integer.parseInt(commands[1]));
				System.out.println("save successful");
				break;
			case "init":
				if (commands.length >= 3) {
					if(commands[2].equals("det")) {deterministic = true; System.out.println("load success");}
					else deterministic = false; 
				}
				placeVirologists(gc, Integer.parseInt(commands[1]));
				break;
			case "interact":
				virUI(gc.getVir(gc.getCurrentPlayer()));
				break;
			case "end_turn":
				gc.endTurn();
				break;
			case "new_turn":
				gc.getVir(Integer.parseInt(commands[1])).StartTurn();
				break;
			case "exit":
				run = false;
				break;
			case "":
				break;
			default:
				System.out.println("wrong command");
			}
		}
	}

	/**
	 * Visszatér a szintetizálandó �genssel.
	 * 
	 * @param v
	 * @returnW
	 */
	public void synthetizeUI(Virologist v) {
		System.out.println("Available agents:");
		int NumOfNuc = v.getInventory().getMaterial(0).getQuantity();
		int NumOfAmi = v.getInventory().getMaterial(1).getQuantity();
		System.out.println("Ami: "+ NumOfAmi + "\nNuc: "+ NumOfNuc);
		int j = 0;
		if(v.getKnownGenomes() != null) {
			for (Genome g : v.getKnownGenomes()) {
				j++;
				System.out.println(j + ". " + g.getCode_and_name() + " Amino Acid: " + g.getAminoacid() + " Nucleotid: " + g.getNucleotid());
			}
			scanLine();
			Genome synth = v.getKnownGenomes().get(Integer.parseInt(commands[0])-1);
			if(synth.getAminoacid() <= v.getInventory().getMaterial(0).getQuantity()  && synth.getNucleotid() <= v.getInventory().getMaterial(1).getQuantity()) {
				synth.synthetize(v);
				System.out.println(synth.getCode_and_name() + " synthetized");
			} else {
				System.out.println("Not enough material");
			}
		}
	}
 
	/**
	 * Ki kéne írnia a commands első eleme alapján, hogy milyen equipmentek, vagy
	 * ágensek vannak. Aztán a játékos kiválasztja amelyiket szeretné, és azt adja
	 * vissza. 
	 */
	public Agent agentsUI(Inventory i) {
		int j = 0;
		System.out.println("Available agents:");
		for (Agent ag : i.getAgentList()) {
			j++;
			System.out.println("" + j + " " +ag.getDescription());
		}
		System.out.print("Your choice: ");
		scanLine();
		for (Agent ag : i.getAgentList()) {
			if (ag.getDescription().equals(commands[0])) {
				return ag;
			}
		}
		System.out.println("No such Agent");
		return null;
	}
	/**
	 * Az Equipmentekhez tartozó UI.
	 * @param i
	 * @return
	 */
	public int EquipmentsUI(Inventory i) {
		System.out.println("Available equipments:");
		int j = 0;
		for (Equipment eq : i.getEquipmentList()) {
			j++;
			System.out.println("" + j + " "+ eq.getDescription());
		}
		System.out.print("Your choice: ");
		scanLine();
		return Integer.parseInt(commands[0]) - 1;
	}
	/**
	 * A virológus alap UI-ja
	 * @param v
	 */
	public void virUI(Virologist v) {
		System.out.println("Virologist " + v.getNum() + " currently on fieldID: " +
							Game_Controller.getGameController().getMap().indexOf(v.getPlace()) +
							" type: " + v.getPlace().getDescription());
		System.out.println("Field: " + v.getPlace().getDescription() + " Neighbors: " + v.getPlace().writeNeighbors() );
		scanLine();
		switch (commands[0]) {

		case "move":
			Default_Field move_to = Game_Controller.getGameController()
			.getField(Integer.parseInt(commands[1]));
			v.move(move_to);
			System.out.println(v.getNum() + " moved"); 
			break;
		case "interact":
			InteractUI(v);
			break;
		case "end_turn":
			v.endTurn();
			System.out.println("ended turn");
			break;
		case "unequip":
			v.unequip(v.getInventory().getEquipment(EquipmentsUI(v.getInventory())));
			System.out.println("unequipped");
			break;
		case "drop":
			v.drop(v.getInventory().getEquipment(EquipmentsUI(v.getInventory())));
			System.out.println(v.getNum()+ " dropped Equipment");
			break;
		case "synthetize":
			synthetizeUI(v);
			break;
		case "equip":
			Game_Controller.getGameController().getVir(Integer.parseInt(commands[1]))
					.equip(EquipmentsUI(Game_Controller.getGameController().getVir(Integer.parseInt(commands[1])).getInventory()));
			System.out.println(v.getNum()+ " equipped Equipment");
			break;

		default: 
			System.out.println("Wrong command");
		}
	}
	/**
	 * Az interakciónál használható UI.
	 * @param v
	 */
	public void InteractUI(Virologist v) {
		System.out.println("Field: " + v.getPlace().getDescription() + " Neighbors: " + v.getPlace().writeNeighbors() );
		System.out.println("Players: " + v.getPlace());
		scanLine();
		switch (commands[0]) {
		case "plunder":
			Virologist plundered = Game_Controller.getGameController()
					.getVir(Integer.parseInt(commands[3]));
			v.plunder(plundered);
			System.out.println(v.getNum() + "done plundering");
			break;
		case "use_agent":
			try {
				v.useAgent(Game_Controller.getGameController().getVir(Integer.parseInt(commands[1])),
						agentsUI(v.getInventory()));	
			} catch(Exception e) {
				invalidCommandEx();
			}			
			break;
		case "take_loot":
			switch(v.getPlace().getDescription()) {
			case "Warehouse": 
				((Warehouse)v.getPlace()).transferMaterial(v);
				System.out.println(v.getNum() + "Gained nucleotid and aminoacid");
				break;
			case "Laboratory":
				((Laboratory)v.getPlace()).transferGenome(v);
				System.out.println(v.getNum() + "Gained Genome: "+v.getKnownGenomes().get(v.getKnownGenomes().size()-1).getCode_and_name());
				break;
			case "Shelter":
				((Shelter)v.getPlace()).transferEq(v);
				System.out.println(v.getNum() + " Gained Equipment: "+v.getInventory().getEquipmentList().get(v.getInventory().getEquipmentList().size()-1).getDescription());
				break;
			default: System.out.println("There is no loot here!");
			break;
			};
			break;
		case "use_axe":
			Virologist axed = Game_Controller.getGameController()
					.getVir(Integer.parseInt(commands[1]));
			v.useAxe(axed);
			break;
		default:
			System.out.println("Invalid command Interaction over");
			break;
		}
	}

	/**
	 * EZT HASZN�LD BEOLVAS�SRA, ha commandot olvasol, amúgy ne! (köszi)
	 */
	public void scanLine() {
		String cmd = scan.nextLine();
		commands = cmd.split(" ");
	}
	/**
	 * elkészíti a mapot beolvasott számok alapján
	 * 
	 * @return
	 */
	public int[] mapCreation() {
		int[] answers = { 0, 0, 0, 0 };
		if(!deterministic)
		System.out.println("Specify number of all Fields");
		scanLine();
		answers[3] = Integer.parseInt( commands[0]);
		if(!deterministic)
		System.out.println("Specify number of Warehouses");
		scanLine();
		answers[0] = Integer.parseInt( commands[0]);
		if(!deterministic)
		System.out.println("Specify number of Shelters");
		scanLine();
		answers[2] = Integer.parseInt( commands[0]);
		if(!deterministic)
		System.out.println("Specify number of Labs (minimum 5)");
		scanLine();
		answers[1] = Integer.parseInt( commands[0]);
		if (answers[1] < 5)
			answers[1] = 5;
		return answers;
	}
	/**
	 * beállítja az összes szomszédos mezőt.
	 * @param map
	 */
	public void setNeighbors(ArrayList<Default_Field> map) {
		if(!deterministic)
		System.out.println("Adding neighbours escape code: -1");
		for (int i = 0; i < map.size(); i++) {
			while (true) {
				if(!deterministic)
				System.out.println(
						"Type the next neighbor's ID of FieldID: " + i + " Type: " + map.get(i).getDescription());
				scanLine();
				int a = Integer.parseInt( commands[0]);
				if (a < 0)
					break;
				else
					map.get(i).addNeighbor(map.get(a));
			}
		}
	}

	/**
	 * Kiírja hogy ki nyert 
	 * @param v
	 */
	public void endGame(Virologist v) {
		System.out.println("Nyertel! " + v.getNum());
	}

	/**
	 * A user által beírt map paraméterek után megkérdezi, hogy ez nagy, közepes
	 * vagy kicsi mapnak számít.
	 * 
	 * @return
	 */
	public int mapChoice() {
		if(!deterministic)
			System.out.println("What type of map is this? \n1 - big\n2 - medium\n3 - small");
		int i = scan.nextInt();
		return i;
	}
	/**
	 * Random vagy adott helyekre lerakja a virológusokat miután megcsinálta a mapot.
	 * @param gc
	 * @param c
	 */
	public void placeVirologists(Game_Controller gc, int c) {
		gc.createMap(c);

		if(!deterministic) {
			System.out.println("How many players are there?");
			int num = scan.nextInt();
			scanLine();
			for (int i = 0; i < num; i++) {
				int int_random = new Random().nextInt(gc.getMap().size());
				Virologist v = new Virologist(i, gc.getMap().get(int_random), new Inventory(), new ArrayList<Genome>());
				gc.addVir(v);
				gc.getMap().get(int_random).addVir(v);
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				Virologist v = new Virologist(i, gc.getMap().get(4), new Inventory(), new ArrayList<Genome>());
				gc.addVir(v);
				gc.getMap().get(4).addVir(v);
			}
		}
	}

	public MainWindow getGameView() {
		return gameView;
	}

	public void setGameView(MainWindow gameView) {
		this.gameView = gameView;
	}
}

