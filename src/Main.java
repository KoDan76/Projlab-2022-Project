import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * //TODO
 */
public class Main implements Serializable {
	/**
	 * elindítja a programot
	 * 
	 * @param args argumentumok
	 */
	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * UserInterface UI = UserInterface.getUI(); Game_Controller gc =
		 * Game_Controller.getGameController(); UserInterface.getUI().defaultUI(gc);
		 */
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/**
					 * létrehozza a startwindowt- ezzel elindítva a játékot.
					 */
					StartWindow m = new StartWindow();
					m.getStartWindow().setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
