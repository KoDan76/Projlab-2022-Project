import java.io.Serializable;
import java.util.Random;

// Projlab 1.0 

/**
 * A játékos tudását reprezentálja az adott ágens genetikai kódjáról. A Genome
 * felel továbbá a szintetizálás és a vakcina készítés lebonyolításáért. Továbbá
 * tárolva van, hogy ezekhez mennyi nukleotid illetve aminosav szükséges.
 * 
 * @author Koppa Dani
 */
public class Genome implements Serializable
{
	/**
	 * megadja a szintetizáláshoz szükséges nukleotiduk számát.
	 */
	private int nucleotid;
	/**
	 * megadja a szintetizáláshoz szükséges aminosavak számát.
	 */
	private int aminoacid;
	/**
	 * az ágens genetikai kódja, és elnevezése.
	 */
	private String code_and_name;
	/**
	 * az ágens leírása
	 */
	private String description;

	/**
	 * adott argumentum alapjan generalja a genomot
	 */
	public Genome(String args) {
		
		switch(args) {
		case "Paralysis":
			code_and_name="Paralysis";
			nucleotid=5;
			aminoacid=5;
			description="B�n�t�s genome";
			break;
		case "Dancing":
			code_and_name="Dancing";
			nucleotid=6;
			aminoacid=4;
			description="Legyen t�nc,t�ncest!";
			break;
		case "Protective":
			code_and_name="Protective";
			nucleotid=4;
			aminoacid=6;
			description="V�delem genome";
			break;
		case "Amnesia":
			code_and_name="Amnesia";
			nucleotid=6;
			aminoacid=6;
			description="J� buli volt!";
			break;
		case "Vaccine":
			code_and_name="Vaccine";
			nucleotid=10;
			aminoacid=10;
			description="Vakcinainfo";
			break;		
		}
	}

	/**
	 * @return Az aminosavak száma ami az ágens szintetizálásához szükséges
	 */
	public int getAminoacid() {
		return aminoacid;
	}

	/**
	 * @return A nukleotidok száma ami az ágens szintetizálásához szükséges
	 */
	public int getNucleotid() {
		return nucleotid;
	}

	/**
	 * A megadott virológus szintetizál (az Inventory-jába) helyez egy adott
	 * genetikai kódnak megfelelő agenst.
	 * 
	 * @param v A virológus aki szintetizál.
	 */
	public void synthetize(Virologist v) {
		int NumOfNuc = v.getInventory().getMaterial(0).getQuantity();
		int NumOfAmi = v.getInventory().getMaterial(1).getQuantity();

		if (NumOfNuc >= this.nucleotid && NumOfAmi >= this.aminoacid) {
			v.getInventory().getMaterial(0).dec(nucleotid);
			v.getInventory().getMaterial(1).dec(aminoacid);

			if (code_and_name == "Dancing") {
				Dancing dancing = new Dancing(2,3);
				v.getInventory().addAgent(dancing);
			} else if (code_and_name == "Paralysis") {
				Paralysis paralysis = new Paralysis(2,3);
				v.getInventory().addAgent(paralysis);
			} else if (code_and_name == "Amnesia") {
				Amnesia amnesia = new Amnesia(2,3);
				v.getInventory().addAgent(amnesia);

			} else if (code_and_name == "Protective") {
				Protective protective = new Protective(2,3);
				v.getInventory().addAgent(protective);

			} else if (code_and_name == "Vaccine") {
				Vaccine vaccine = new Vaccine();
				v.getInventory().addAgent(vaccine);
			}
		}else System.out.println("Not enough matereial");
	}

	/**
	 * Visszaadja az ágens genetikai kódját és elnevezését.
	 * 
	 * @return az ágens genetikai kódja, és elnevezése.
	 */
	public String getCode_and_name() {
		return code_and_name;
	}

	/**
	 * Visszaadja az ágens leírását.
	 * 
	 * @return az ágens leírása
	 */
	public String getDescription() {
		return description;
	}
}