import java.io.Serializable;

// Projlab 1.0 



/**
 * //Speci�lis mez� melyben genetikai k�dok t�rol�dnak. Azokat innen lehet kl�nozni. N�h�ny kozuluk fertozo.
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
	 * L�trehozza, hogy itt milyen fajta Genetikai k�d tal�lhat�
	 */
	public void generateGenome(String args ) 
	{
		g = new Genome(args);
	}
	/**
	 * �tadja a genetikai k�dot
	 * @param v az a Virol�gus, akinek �tadn�nk a genetikai k�dot
	 */
	public void transferGenome(Virologist v) 
	{
		if(g!= null)
			v.learnGenome(g);
	}
	/**
	 * Le�r�s be�ll�t�sa
	 */
	public Laboratory(){
        setDescription("Laboratory");
    }
	/**
	 * megn�zi, hogy a Laboratory medvev�russal fert�z�tt-e, ha igen, akkor megfert�zi a r�l�p� virol�gust
	 */
	public void addVir(Virologist v) 
	{
		this.standing_here.add(v);
		if(this.toxic)
			v.applyEffect(new Bear());
	}
	/**
	 * medvev�russal fert�z�tt� teszi a Laboratoryt
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
