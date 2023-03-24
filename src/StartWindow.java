import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class StartWindow {

	private JFrame startWindow;
	private JLabel txtNumberOfPlayers;
	private JLabel txtMapSize;
	private JButton playButton;
	private JButton loadButton;
	private ImageIcon icon = new ImageIcon("assets/virus.jpg");
	/**
	 * Create the application.
	 */
	public StartWindow() {
		initialize();
	}
	/**
	 * Visszaadja a startWindow JFramet
	 */
	public JFrame getStartWindow() {
		return startWindow;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		startWindow = new JFrame();
		startWindow.setTitle("Start Game");
		startWindow.setResizable(false);
		startWindow.setBounds(600, 300, 280, 222);
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.getContentPane().setLayout(null);
		startWindow.setIconImage(icon.getImage());
		txtMapSize = new JLabel();
		txtMapSize.setText("Map Size:");
		txtMapSize.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMapSize.setBounds(10, 42, 134, 20);
		startWindow.getContentPane().add(txtMapSize);
		/**
		 * itt lehet kiválasztani a mapot, amin játszani szeretnénk.
		 */
		JComboBox mapSelector = new JComboBox();
		mapSelector.setMaximumRowCount(3);
		mapSelector.setBounds(154, 41, 100, 22);
		mapSelector.addItem("Small");
		mapSelector.addItem("Big");
		//mapSelector.addItem("Big");
		startWindow.getContentPane().add(mapSelector);
		/**
		 * Ezzel a sliderrel lehet kiválasztani hogy hány darab játékos lesz.
		 */
		JSlider playerNumber = new JSlider();
		playerNumber.setValue(2);
		playerNumber.setPaintLabels(true);
		playerNumber.setPaintTicks(true);
		playerNumber.setSnapToTicks(true);
		playerNumber.setMinimum(1);
		playerNumber.setMaximum(30);
		playerNumber.setBounds(154, 11, 100, 26);
		playerNumber.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				txtNumberOfPlayers.setText("Number of players: " + playerNumber.getValue());
			}
			
		});
		startWindow.getContentPane().add(playerNumber);

		txtNumberOfPlayers = new JLabel();
		txtNumberOfPlayers.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNumberOfPlayers.setText("Number of players: " + playerNumber.getValue());
		txtNumberOfPlayers.setBounds(10, 11, 134, 20);
		startWindow.getContentPane().add(txtNumberOfPlayers);
		/**
		 * A játék kezdetét jelezzük ezzel, ekkor készül a pálya
		 */
		playButton = new JButton("Play");
		playButton.setBounds(10, 112, 244, 60);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s=(String)mapSelector.getSelectedItem();
				if(s=="Big")
					s="Medium";
				MainWindow m = new MainWindow(s,playerNumber.getValue()); // ITT CSINÁL ÚJ MAPOT
				
				Game_Controller.getGameController().getVir(0).StartTurn();
				UserInterface.getUI().setGameView(m);
				m.getMainWindow().setVisible(true);
				startWindow.setVisible(false);
			}
		});
		startWindow.getContentPane().add(playButton);
		
		/*loadButton = new JButton("Load Game");
		loadButton.setBounds(10, 78, 244, 23);
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(startWindow);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					loadButton.setText("Selected File: " + selectedFile.getName());
				}
			}
		});
		startWindow.getContentPane().add(loadButton);*/
	}
}
