import java.io.Serializable;

/**
 *Az óvóhelyeken található balta nevû eszköz is.
 * Bárki, akit ezzel megcsapnak, meghal, de nem fertõz tovább.
 * A balta egy használat után kicsorbul és nem mûködik tovább, de amíg nem dobják el, foglalja a helyet.
 */
public class Axe extends Equipment implements Serializable{
    private boolean broken = false;

    public Axe(){
        setDescription("Axe");
    }

    /**
     * megcsapnak vele egy virológust és megöli azt. Ezt úgy éri el,
     * hogy a megadott Virológus effect listájának LEGELEJÉRE behelyez egy újonnan létrehozott Paralyis objektumot
     * amelynek végtelen ideig tartó hatása van.
     * @param v
     */
    public void effect(Virologist v)
    {
        if(getBroken() == false){
            Paralysis pInf = new Paralysis(666,666);
            v.getApplied_effects().add(pInf);//todo nem tudom lista elejére szurja e be ha nem kell uh fv
            setBroken();
        }

    }

    /**
     * Ha eltört a fejsze használhatatlanná teszi. Átállítja a broken változót igazra.
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
