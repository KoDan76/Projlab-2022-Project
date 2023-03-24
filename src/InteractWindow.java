import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import javax.swing.border.BevelBorder;

public class InteractWindow {
	
	private JFrame interactWindow;
	private JTable inventoryTable;
	private JTable playersTable;
	private VirologistData virData=new VirologistData();
	private InventoryData invData=new InventoryData();
	private ImageIcon icon = new ImageIcon("assets/virus.jpg");
	private JLabel fieldtype=new JLabel();
	/**
	 * Create the application.
	 */
	public InteractWindow() {
		initialize();
	}
	
	public JFrame getInteractWindow() {
		return interactWindow;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		interactWindow = new JFrame();
		interactWindow.setResizable(false);
		interactWindow.setTitle("Interact Menu");
		interactWindow.setBounds(400, 200, 640, 480);
		interactWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		interactWindow.getContentPane().setLayout(null);
		interactWindow.setIconImage(icon.getImage());
		
		/**
		 * A gombra kattintva felveszi a virológus a mezõn lévõ dolgokat.
		 * ez ha shelter - equipment
		 * ha labor - genom (ha nem üres)
		 * ha warehouse - material
		 * 
		 */
		fieldtype.setBounds(10, 300, 190, 23);
		fieldtype.setText("A Field_type: "+ Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer()).getPlace().getDescription());
		interactWindow.getContentPane().add(fieldtype);
		JButton lootButton = new JButton("Take loot");
		lootButton.setBounds(10, 373, 89, 23);
		interactWindow.getContentPane().add(lootButton);
		lootButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Virologist v=Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer());
				switch(v.getPlace().getDescription()) {
				case "Warehouse": 
					boolean success = ((Warehouse)v.getPlace()).transferMaterial(v);
					if(success)
						JOptionPane.showMessageDialog(interactWindow,"Nukleotid, aminosav felvéve ");
					else
						JOptionPane.showMessageDialog(interactWindow,"A raktár le van rombolva, innen nem vehetsz fel anyagot ");
					break;
				case "Laboratory":
					((Laboratory)v.getPlace()).transferGenome(v);
					JOptionPane.showMessageDialog(interactWindow,"Genom megtanulva: "+v.getKnownGenomes().get(v.getKnownGenomes().size()-1).getCode_and_name());
					break;
				case "Shelter":
					((Shelter)v.getPlace()).transferEq(v);
					JOptionPane.showMessageDialog(interactWindow,"Felszerelés felvéve: "+v.getInventory().getEquipmentList().get(v.getInventory().getEquipmentList().size()-1).getDescription());
					break;
				default: JOptionPane.showMessageDialog(interactWindow,"Nem lehet semmit felvenni! ");
				break;
				};
				invData.fireTableDataChanged();
				inventoryTable.repaint();	
				virData.fireTableDataChanged();
				playersTable.repaint();
			}
		});
		/**
		 * Ez a gomb az ágensek, illetve a balta használatára való
		 * ellenõrzi, hogy ki van-e minden jelölve, illetve, hogy ez a használat engedélyezett-e.
		 */
		JButton useButton = new JButton("Use");
		useButton.setBounds(109, 407, 89, 23);
		interactWindow.getContentPane().add(useButton);
		useButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inventoryTable.getSelectionModel().isSelectionEmpty() && inventoryTable.getSelectedRow()!= 0 &&
						!playersTable.getSelectionModel().isSelectionEmpty() && playersTable.getSelectedRow()!= 0)
				{
					int virnum=(Game_Controller.getGameController().getCurrentPlayer());
					int sel=((int) playersTable.getValueAt(playersTable.getSelectedRow(),0))-1;
					if(inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 0)=="Agent") {
						if(!Game_Controller.getGameController().getVir(sel).isProtected()){
							JOptionPane.showMessageDialog(interactWindow, Game_Controller.getGameController().getVir(virnum).
									getInventory().getAgentList().get(inventoryTable.getSelectedRow()-3).getDescription()+
									" ágens hasznalva: "+ (sel+1)+ "-s számú virológuson");
							Game_Controller.getGameController().getVir(virnum).
								useAgent(Game_Controller.getGameController().getVir(sel), Game_Controller.getGameController().getVir(virnum).
										getInventory().getAgentList().get(inventoryTable.getSelectedRow()-3));
						} else
							JOptionPane.showMessageDialog(interactWindow, "A választott virológus védve van!");
					} else if(inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 1)=="Axe") {
						Game_Controller.getGameController().getVir(virnum).useAxe(Game_Controller.getGameController().getVir(virnum));
						JOptionPane.showMessageDialog(interactWindow,"Axe hasznalva: "+ (sel+1)+ "-s számú virológuson");
					} else 
						JOptionPane.showMessageDialog(interactWindow, "Csak Agent és Axe típusú tárgyat lehet használni!");
					
				} else
					JOptionPane.showMessageDialog(interactWindow,"Nem választottál játékost, Agent/Axe típusú tárgyat!");
				invData.fireTableDataChanged();
				inventoryTable.repaint();	
				virData.fireTableDataChanged();
				playersTable.repaint();
			}
		});
		/**
		 * Ennek a gombnak a hatására a virológus a vele egy mezõ álló
		 * másik bénult virológus equipmentjét lopja el
		 */
		JButton plunderButton = new JButton("Plunder");
		plunderButton.setBounds(109, 373, 89, 23);
		interactWindow.getContentPane().add(plunderButton);
		plunderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!playersTable.getSelectionModel().isSelectionEmpty() && playersTable.getSelectedRow()!= 0) {
					int sel=(int) playersTable.getValueAt(playersTable.getSelectedRow(),0);
					if(sel-1 != (Game_Controller.getGameController().getCurrentPlayer())) {
						if(Game_Controller.getGameController().getVir(sel-1).isParalyzed()) {
								Game_Controller.getGameController().getVir(Game_Controller.getGameController().
										getCurrentPlayer()).plunder(Game_Controller.getGameController().getVir(sel-1));
								JOptionPane.showMessageDialog(interactWindow,"Plundering kész!");
						}
						else
							JOptionPane.showMessageDialog(interactWindow,"A választott virológus nincs bénulva!");
					} else
						JOptionPane.showMessageDialog(interactWindow,"Nem választhatod önmagad!");
				} else
					JOptionPane.showMessageDialog(interactWindow,"Nem választottál játékost!");
				invData.fireTableDataChanged();
				inventoryTable.repaint();	
				virData.fireTableDataChanged();
				playersTable.repaint();
			}
		});
		
		/**
		 * Ez a gomb a kijelölt Equipmentet veszi le a virológusról
		 */
		JButton unequipButton = new JButton("Unequip");
		unequipButton.setBounds(10, 339, 89, 23);
		interactWindow.getContentPane().add(unequipButton);
		unequipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inventoryTable.getSelectionModel().isSelectionEmpty() && inventoryTable.getSelectedRow()!= 0)
				{
					if(inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 0)=="Equipment") {
						
						Virologist vir= Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer());
						boolean vane=false;
						for(int i=0;i<vir.getApplied_effects().size();i++) {
							if(vir.getApplied_effects().get(i).getDescription()==inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 1));
								vane=true;
						}
						if(vane) {
							int idx= inventoryTable.getSelectedRow()-(vir.getInventory().getAgentList().size()+3);
							Equipment equipm=(Equipment) vir.getApplied_effects().get(idx);
							vir.unequip(equipm);
							JOptionPane.showMessageDialog(interactWindow,"Felszerelés a virológusról levéve: "+equipm.getDescription());
							virData.fireTableDataChanged();
							playersTable.repaint();
							invData.fireTableDataChanged();
							inventoryTable.repaint();
						} else
							JOptionPane.showMessageDialog(interactWindow,"Nem felvett Equipmentet nem lehet levenni!");
					} else
						JOptionPane.showMessageDialog(interactWindow,"Csak Equipment típusú tárgyat lehet levenni!");
				} else
					JOptionPane.showMessageDialog(interactWindow,"Nem választottál ki Equipmentet!");
				
			}
		});
		/**
		 * Ez a gomb a kijelölt Equipmentet veszi fel a Virológusra.
		 */
		JButton equipButton = new JButton("Equip");
		equipButton.setBounds(109, 339, 89, 23);
		interactWindow.getContentPane().add(equipButton);
		equipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inventoryTable.getSelectionModel().isSelectionEmpty() && inventoryTable.getSelectedRow()!= 0) {
					if(inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 0)=="Equipment") {
						Virologist vir= Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer());
						int idx= inventoryTable.getSelectedRow()-(vir.getInventory().getAgentList().size()+3);
						vir.equip(idx);
						JOptionPane.showMessageDialog(interactWindow,"Felszerelés a virológusra felvéve: "+ vir.getInventory().getEquipment(idx).getDescription());
					}else {
						JOptionPane.showMessageDialog(interactWindow,"Csak Equipment típusú tárgyat vehetsz fel! ");
					}
				}else
					JOptionPane.showMessageDialog(interactWindow,"Nem jelöltél ki Equipmentet! ");
				
				
				virData.fireTableDataChanged();
				playersTable.repaint();
			}
		});
		/**
		 * Ez a gomb a kijelölt equipmentet dobja le.
		 */
		JButton dropButton = new JButton("Drop");
		dropButton.setBounds(10, 407, 89, 23);
		interactWindow.getContentPane().add(dropButton);
		dropButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inventoryTable.getSelectionModel().isSelectionEmpty() && inventoryTable.getSelectedRow()!= 0) {
					if(inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 0)=="Equipment") {
						Virologist vir= Game_Controller.getGameController().getVir(Game_Controller.getGameController().getCurrentPlayer());
						int idx= inventoryTable.getSelectedRow()-(vir.getInventory().getAgentList().size()+3);
						Equipment equipm=(Equipment) vir.getInventory().getEquipment(idx);
						vir.drop(equipm);
						JOptionPane.showMessageDialog(interactWindow,"Felszerelés eldobva: "+equipm.getDescription());
					} else
						JOptionPane.showMessageDialog(interactWindow,"Csak Equipment típusú tárgyat dobhatsz el! ");
				} else
					JOptionPane.showMessageDialog(interactWindow,"Nem jelöltél ki semmit! ");
				virData.fireTableDataChanged();
				playersTable.repaint();
				invData.fireTableDataChanged();
				inventoryTable.repaint();
				
			}
			
		});
		/**
		 * Innentõl már csak a felület darabonkénti felépítése van 
		 * (plusz a táblázatok feltöltése a megfelelõ adatokkal)
		 */
		ScrollPane inventoryPane = new ScrollPane();
		inventoryPane.setBounds(10, 10, 604, 238);
		interactWindow.getContentPane().add(inventoryPane);
		
		ScrollPane playersPane = new ScrollPane();
		playersPane.setBounds(208, 254, 406, 176);
		interactWindow.getContentPane().add(playersPane);
		
		inventoryTable = new JTable(invData);
		
		inventoryTable.getColumnModel().getColumn(0).setResizable(false);
		inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		inventoryTable.getColumnModel().getColumn(1).setResizable(false);
		inventoryTable.getColumnModel().getColumn(2).setResizable(false);
		inventoryTable.getColumnModel().getColumn(3).setResizable(false);
		inventoryTable.getColumnModel().getColumn(4).setResizable(false);
		inventoryTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		inventoryTable.setBounds(38, 24, 1, 1);
		inventoryTable.setSelectionMode(0);
		inventoryPane.add(inventoryTable);

		playersTable = new JTable(virData);
		playersTable.getColumnModel().getColumn(0).setResizable(false);
		playersTable.getColumnModel().getColumn(1).setResizable(false);
		playersTable.getColumnModel().getColumn(2).setResizable(false);
		playersTable.getColumnModel().getColumn(3).setResizable(false);
		playersTable.getColumnModel().getColumn(4).setResizable(false);
		playersTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		playersTable.setBounds(343, 339, 1, 1);
		playersTable.setSelectionMode(0);
		playersPane.add(playersTable);
	}
	/**
	 * Ez visszaadja az inventoryTable-ben lévõ dolgokat.
	 * @return
	 */
	public Effectable[] return_Effectables() {
		int[] rows = inventoryTable.getSelectedRows();
		Effectable[] ret = null;
		for(int i = 0; i < rows.length; i++) {
			ret[i] = invData.getItemInRow(rows[i]);
		}
		return ret;
		
	}
}
