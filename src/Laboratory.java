import java.io.Serializable;

// Projlab 1.0 



/**
 * //Speciális mezõ melyben genetikai kódok tárolódnak. Azokat innen lehet klónozni. Néhány kozuluk fertozo.
 */
public class Laboratory extends Default_Field implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean toxic=false;
	private Genome g;
	/**
	 * Létrehozza, hogy itt milyen fajta Genetikai kód található
	 */
	public void generateGenome(String args ) 
	{
		g = new Genome(args);
	}
	/**
	 * Átadja a genetikai kódot
	 * @param v az a Virológus, akinek átadnánk a genetikai kódot
	 */
	public void transferGenome(Virologist v) 
	{
		if(g!= null)
			v.learnGenome(g);
	}
	/**
	 * Leírás beállítása
	 */
	public Laboratory(){
        setDescription("Laboratory");
    }
	/**
	 * megnézi, hogy a Laboratory medvevírussal fertõzött-e, ha igen, akkor megfertõzi a rálépõ virológust
	 */
	public void addVir(Virologist v) 
	{
		this.standing_here.add(v);
		if(this.toxic)
			v.applyEffect(new Bear());
	}
	/**
	 * medvevírussal fertõzötté teszi a Laboratoryt
	 */
	public void setToxic() {
		toxic=true;
	}
	/**
	 * visszaadja, hogy a Laboratory medvevirussal fertozott-e
	 * @return
	 */
	public boolean getToxic() {
		return toxic;
	}
	
}
