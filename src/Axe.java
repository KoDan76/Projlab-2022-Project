import java.io.Serializable;

/**
 *Az �v�helyeken tal�lhat� balta nev� eszk�z is.
 * B�rki, akit ezzel megcsapnak, meghal, de nem fert�z tov�bb.
 * A balta egy haszn�lat ut�n kicsorbul �s nem m�k�dik tov�bb, de am�g nem dobj�k el, foglalja a helyet.
 */
public class Axe extends Equipment implements Serializable{
    private boolean broken = false;

    public Axe(){
        setDescription("Axe");
    }

    /**
     * megcsapnak vele egy virol�gust �s meg�li azt. Ezt �gy �ri el,
     * hogy a megadott Virol�gus effect list�j�nak LEGELEJ�RE behelyez egy �jonnan l�trehozott Paralyis objektumot
     * amelynek v�gtelen ideig tart� hat�sa van.
     * @param v
     */
    public void effect(Virologist v)
    {
        if(getBroken() == false){
            Paralysis pInf = new Paralysis(666,666);
            v.getApplied_effects().add(pInf);//todo nem tudom lista elej�re szurja e be ha nem kell uh fv
            setBroken();
        }

    }

    /**
     * Ha elt�rt a fejsze haszn�lhatatlann� teszi. �t�ll�tja a broken v�ltoz�t igazra.
     */
    public void setBroken(){
        this.broken = true;
    }

    /**
     * Visszaadja, hogy a balta el van-e csorbulva.
     * @return
     */
    public boolean getBroken(){
        return this.broken;
    }


}
