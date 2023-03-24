import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.ScrollPane;


public class MainWindow {
	/**
	 * tagv�ltoz�kban vettem fel azokat az elemeket, amelyeket p�ld�ul actionlistenerekben haszn�lni kell, �gy el�rhet�ek.
	 */
	private JFrame mainWindow;
	private ImageIcon map;
	private ImageIcon art;
	private JLabel playerField;
	private JLabel aminoField;
	private JLabel nucletidField;
	private JTable inventoryTable;
	private Game_Controller gc;
	private ArrayList<MapElement> mapElements = new ArrayList<MapElement>(); 
	private ArrayList<Element_Field> fields = new ArrayList<Element_Field>();
	private ArrayList<Element_Vir> virologists = new ArrayList<Element_Vir>();
	private GenomeData genomeData;
	private JLabel fieldArtLabel ;
	private ScrollPane inventoryPane = new ScrollPane();
	private ImageIcon icon = new ImageIcon("assets/virus.jpg");
	
	JLabel mapLabel = new JLabel("Map");
	

	
	/**
	 * Create the application.
	 * @param mapChoice A v�lasztott t�rk�p neve
	 */
	public MainWindow(String mapChoice, int playerNumber) {
		setMap(mapChoice);											/**be�ll�tja a megfelel� mapot*/
		gc = Game_Controller.getGameController();
		String init = "execute";									/**A m�r haszn�lt initialize f�jlok seg�ts�g�vel gener�lja a p�ly�t*/
		init += " Initialize" + mapChoice + ".txt" ;
		UserInterface.getUI().defaultUI(gc, init);
		
		for (int i=0;i<playerNumber-1;i++) {						/**Gener�lja a megfelel� sz�m� virol�gust*/
			int int_random = new Random().nextInt(Game_Controller.getGameController().getMap().size());
			Virologist v = new Virologist(i+1, Game_Controller.getGameController().getMap().get(int_random), new Inventory(), new ArrayList<Genome>());
			/*v.getInventory().getMaterial(0).add(i+1);
			v.getInventory().getMaterial(1).add(i+1);*/
			Game_Controller.getGameController().addVir(v);					/**megfelel� helyen elhelyezi a virol�gusokat(random)*/
			Game_Controller.getGameController().getMap().get(int_random).addVir(v);
		}
		place(mapChoice);								
		genomeData= new GenomeData();										/**itt inicializ�lom a genomokat tartalmaz� t�*/
		initialize();
	}
	
	private void place(String mapChoice) {									/**A megfelel� maphoz tartoz� koordin�t�k seg�ts�g�vel elhelyezi a fieldeket*/
		String f = mapChoice + "Coordinates.txt";
		Scanner scan = null;
		Game_Controller gc = Game_Controller.getGameController();
		
		try {
			scan = new Scanner(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			PopUps.errorMsg(new JFrame(), f + " file not found");
		}
		String[] coord;
		int i = 0;
		while(scan.hasNextLine()) {													/**beolvassa a koordin�t�kat f�jlb�l*/
			String line = scan.nextLine();			
			coord = line.split(",");
			fields.add(new Element_Field( gc.getField(i)));
			fields.get(i).set(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
			i++;
		}		
		for (Virologist vir : gc.getAllVirologists()) {				/**A virol�gusokat hozz�adja a fieldekhez, majd a maphoz*/
			Element_Vir e = new Element_Vir(vir);
			for (Element_Field f1 : fields) {
				if(f1.getField() == vir.getPlace()) {
					e.set(f1.getY(), f1.getX());
				}				
			}
			virologists.add(e);
			mapElements.add(e);
		}
		mapElements.addAll(virologists);
		mapElements.addAll(fields);		
	}
	
	public void draw(Graphics g) {									/**kirajzolja az �sszes elementet*/
		int i = 0;
		for (MapElement element : mapElements) {	
			element.draw(g,i);
			i++;
		}
	}
	
	public JFrame getMainWindow() {									/**visszaadja mainWindow JFramet*/
		return mainWindow;
	}
	
	/**
	 * Bet�lti a t�rk�p k�p�t
	 * FONTOS: 1000x600
	 * @param m A v�lasztott t�rk�p neve
	 */
	private void setMap(String m) {			
		switch(m) {
		case "Small":
				map = new ImageIcon("assets/map_small.png");
			break;
		case "Medium":
				map = new ImageIcon("assets/map_medium.png");
			break;
		case "Big":
				map = new ImageIcon("assets/map_large.png");
			break;
		}
		System.out.println(map.getDescription() + " " + map.getIconWidth() + " x " + map.getIconHeight());  
	}
	
	/**
	 * Bet�lti a Field Art-ot
	 * FONTOS: 221x151
	 * @param fieldType Aktu�lis mezofajta neve
	 */
	private void setArt(String fieldType) {
		switch(fieldType) {
		case "Field":
				art = new ImageIcon("assets/art_default.png");
			break;
		case "Laboratory":
				art = new ImageIcon("assets/art_lab.png");
			break;
		case "Warehouse":
				art = new ImageIcon("assets/art_warehouse.png");
			break;
		case "Shelter":
				art = new ImageIcon("assets/art_shelter.png");
			break;
		}
		System.out.println(art.getDescription() + " " + art.getIconWidth() + " x " + art.getIconHeight());  
	}
	/**
	 * Rajzol egy k�rt az adott koordin�t�kra
	 * @param g Grafikus kontextus
	 * @param x koordin�ta
	 * @param y koordin�ta
	 * @param c sz�n
	 */
	public void paintCircle(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.fillOval(x, y, 10, 10);
	}
	/**
	 * Rajzol egy h�romsz�get
	 * Adott koordin�ta a felso cs�csa
	 * @param g Grafikus kontextus
	 * @param x koordin�ta
	 * @param y koordin�ta
	 * @param c sz�n
	 */
	public void paintTriangle(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.fillPolygon(new int[] {x, x-10, x+10}, new int[] {y, y+15, y+15}, 3);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/**
		 * hozz�adja az elementeket
		 */
		ArrayList<Virologist> virs = Game_Controller.getGameController().getAllVirologists();
		for( Virologist v : virs) {
			virologists.add(new Element_Vir(v));
		}
		ArrayList<Default_Field> filds = Game_Controller.getGameController().getMap();
		for( Default_Field f : filds) {
			fields.add(new Element_Field(f));
		}
		/**
		 * inicializ�lja az ablakot
		 */
		
		mainWindow = new JFrame();
		mainWindow.setResizable(false);
		mainWindow.setTitle("World of the Blind Virologists");
		mainWindow.setBounds(100, 100, 1280, 720);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null);
		mainWindow.setIconImage(icon.getImage());
		/**
		 * Ezzel a gombbal lehet szintetiz�lni �genseket
		 * el�sz�r leellen�rzni, hogy j�l jel�lt�nk-e ki, illetve, hogy van-e el�g anyagunk
		 * ha igen, akkor megh�vja a szintetiz�l� f�ggv�nyt.
		 */
		JButton synthetizeButton = new JButton("Synthetize");
		synthetizeButton.setBounds(10, 630, 160, 40);
		mainWindow.getContentPane().add(synthetizeButton);
		synthetizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inventoryTable.getSelectionModel().isSelectionEmpty() && inventoryTable.getSelectedRow()!= 0) {
					Genome a=Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).
							getKnownGenomes().get(inventoryTable.getSelectedRow()-1);
					if(a.getAminoacid()<=Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(0).getQuantity() &&
						a.getNucleotid()<=Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(1).getQuantity())
					{
						a.synthetize(Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()));
						aminoField.setText("Aminoacid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(0).getQuantity());
						nucletidField.setText("Nucleotid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(1).getQuantity());
						JOptionPane.showMessageDialog(mainWindow,"Agens szintetizalva: "+a.getCode_and_name());
					} else
						JOptionPane.showMessageDialog(mainWindow,"Nincs el�g aminosav, nukleotid! ");
					
				} else
					JOptionPane.showMessageDialog(mainWindow,"V�laszd ki a szintetiz�land� genomot! ");
			}
		});
		/**
		 * Megnyitja az interakci�hoz kell� ablakot
		 */
		JButton interactButton = new JButton("Interact");
		interactButton.setBounds(552, 630, 160, 40);
		mainWindow.getContentPane().add(interactButton);
		interactButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InteractWindow i = new InteractWindow();
				i.getInteractWindow().setVisible(true);
				/**
				 * ha bez�rjuk az interakci�s ablakot, akkor friss�teni kell a t�bl�zatot
				 * mivel ha felvett�nk genomot, vagy anyagot, akkor azt itt l�tnunk kell.
				 */
				WindowListener listener = new WindowAdapter() {
			         public void windowClosing(WindowEvent evt) {
			        	 playerField.setText("Virologist "+(Game_Controller.getGameController().getAllVirologists().get(Game_Controller.getGameController().getCurrentPlayer()).getNum()+1)+
			     				"  -  Turn: " + Game_Controller.getGameController().getTimer().getTurn());
						aminoField.setText("Aminoacid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(0).getQuantity());
						nucletidField.setText("Nucleotid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(1).getQuantity());
						
						genomeData.fireTableDataChanged();
						inventoryTable.repaint();
			         }
			      };
			      i.getInteractWindow().addWindowListener(listener);
			}
		});
		/**
		 * ezzel a gombbal kell befejezn�nk a k�rt, ha �ppen nyert�nk, akkor v�ge a j�t�knak
		 */
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.setBounds(1094, 630, 160, 40);
		mainWindow.getContentPane().add(endTurnButton);
		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).checkWin()) {
					Game_Controller.getGameController().endTurn();
					JOptionPane.showMessageDialog(mainWindow,"Jatek vege!\n"+"A jatekos "+
							(Game_Controller.getGameController().getAllVirologists().get(Game_Controller.getGameController().getCurrentPlayer()).getNum()+1)+" nyert.");
					{System.exit(0);}
				}else {	
				Game_Controller.getGameController().endTurn();
				
				Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).StartTurn();
				if(Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).hasBear())
				{
					Element_Vir v = virologists.get(Game_Controller.getGameController().getCurrentPlayer());
					mapLabel.repaint(v.posX-30, v.posY-30, 60, 60);
					v.moveRandom();
					draw(mapLabel.getGraphics());
				}
					
				playerField.setText("Virologist "+(Game_Controller.getGameController().getAllVirologists().get(Game_Controller.getGameController().getCurrentPlayer()).getNum()+1)+
						"  -  Turn: " + Game_Controller.getGameController().getTimer().getTurn());
				playerField.setForeground(virologists.get(Game_Controller.getGameController().getAllVirologists().get(Game_Controller.getGameController().getCurrentPlayer()).getNum()).color);

				aminoField.setText("Aminoacid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(0).getQuantity());
				nucletidField.setText("Nucleotid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(1).getQuantity());
				setArt(Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getPlace().getDescription());
				fieldArtLabel.setIcon(art);
				genomeData.fireTableDataChanged();
				inventoryTable.repaint();
				}
			}
		});
		/**
		 * a mouseClickre mozog a virol�gus
		 */
		mapLabel.setIcon(map);
		mapLabel.setBounds(10, 11, 1000, 600); // MAP: 1000 X 600 - as 
		mapLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int b = e.getButton();
				int n = e.getClickCount();
				int x = e.getX();
				int y = e.getY();
				System.out.println("Mouse button " + b + " clicked " + n + " time(s) at " + x + " " + y);
				draw(mapLabel.getGraphics());
				if(b == 1 && n == 1){
					Element_Vir v = virologists.get(Game_Controller.getGameController().getCurrentPlayer());
					if(v.getVir().hasBear()) {
						JOptionPane.showMessageDialog(mainWindow,"Nem tudsz l�pni, v�letlenszer� a mozg�sod!");
					} else {
					mapLabel.repaint(v.posX-30, v.posY-30, 60, 60);
					v.move(x, y);
					playerField.setForeground(virologists.get(Game_Controller.getGameController().getAllVirologists().get(Game_Controller.getGameController().getCurrentPlayer()).getNum()).color);
					
					setArt(Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getPlace().getDescription());
					fieldArtLabel.setIcon(art);
					genomeData.fireTableDataChanged();
					inventoryTable.repaint();
					draw(mapLabel.getGraphics());
					}
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {/*draw(mapLabel.getGraphics());*/}
			@Override
			public void mouseExited(MouseEvent e) {}

		});
		mapLabel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {draw(mapLabel.getGraphics());}
			@Override
			public void mouseDragged(MouseEvent e) {}
		});
		mainWindow.getContentPane().add(mapLabel);
		/**
		 * be�ll�tjuk a sarokban a mez� k�p�t
		 */
		setArt(Game_Controller.getGameController().getVir(0).getPlace().getDescription());
		fieldArtLabel = new JLabel("Field art");
		fieldArtLabel.setIcon(art);
		fieldArtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldArtLabel.setBounds(1033, 11, 221, 151);
		mainWindow.getContentPane().add(fieldArtLabel);
		/**
		 * ez a jelenlegi virol�gust, illetve k�rt mutatja 
		 */
		playerField = new JLabel();
		playerField.setText("Virologist "+(Game_Controller.getGameController().getAllVirologists().get(Game_Controller.getGameController().getCurrentPlayer()).getNum()+1)+
				"  -  Turn: " + Game_Controller.getGameController().getTimer().getTurn());
		playerField.setForeground(virologists.get(Game_Controller.getGameController().getAllVirologists().get(Game_Controller.getGameController().getCurrentPlayer()).getNum()).color);
		playerField.setBounds(1033, 173, 300, 20);
		mainWindow.getContentPane().add(playerField);
		/**
		 * ez a jelenlegi j�tkos aminosav�nak sz�m�t mutatja meg.
		 */
		aminoField = new JLabel();
		aminoField.setText("Aminoacid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(0).getQuantity());
		aminoField.setBounds(1033, 204, 86, 20);
		mainWindow.getContentPane().add(aminoField);
		/**
		 * ez a jelenlegi j�t�kos nukleotidjainak sz�m�t mutatja meg
		 */
		nucletidField = new JLabel();
		nucletidField.setText("Nucleotid: "+Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getInventory().getMaterial(1).getQuantity());
		nucletidField.setBounds(1129, 204, 86, 20);
		mainWindow.getContentPane().add(nucletidField);
		

		inventoryPane.setBounds(1033, 230, 221, 381);
		mainWindow.getContentPane().add(inventoryPane);
		
		/**
		 * l�trehozza a genomokat tartalmaz� t�bl�zatot.
		 */
		inventoryTable = new JTable();
		inventoryTable.setModel(genomeData);
		inventoryTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		inventoryTable.setSelectionMode(0);
		inventoryTable.getColumnModel().getColumn(0).setResizable(false);
		inventoryTable.getColumnModel().getColumn(1).setResizable(false);
		inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(30);
		inventoryTable.getColumnModel().getColumn(2).setResizable(false);
		inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(30);
		inventoryTable.setBounds(1137, 255, -52, 81);
		inventoryPane.add(inventoryTable);
	}
	
	
	/**
	 * visszaadja a fieldeket.
	 * @return
	 */
	public ArrayList<Element_Field> getFields() {
		return fields;
	}
}
