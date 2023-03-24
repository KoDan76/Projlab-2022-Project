import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopUps {
	/**
	 * Az error üzenethez tartozó függvény
	 * @param f
	 * @param arg
	 */
	public static void errorMsg(JFrame f, String arg) {
		JOptionPane error = new JOptionPane(arg,
		JOptionPane.ERROR_MESSAGE); 
		JDialog jd = error.createDialog("Error");
		jd.setVisible(true);
	}
	/**
	 * a választáshoz tartozó függvény
	 * @param f
	 * @param arg
	 * @return
	 */
	public static int decision(JFrame f, String arg) {
		JOptionPane opt = new JOptionPane("args",
		JOptionPane.QUESTION_MESSAGE, 
		JOptionPane.YES_NO_OPTION); 
		JDialog jd = opt.createDialog("Are you sure");
		jd.setVisible(true);
		return (int) opt.getValue();
	}
	/**
	 * Az info üzenethez tartozó függvény
	 * @param f
	 * @param args
	 */
	public static void info(JFrame f, String args) {
		JOptionPane.showMessageDialog(f,
			    args);
	}
}
