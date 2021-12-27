package Model;

public class ApostaSimples extends Aposta{
    private Float odd;


    public ApostaSimples(){
        super();
        this.odd=(float)0;
    }
    public ApostaSimples(String e, Boolean rs, Float vA, Float gP, String participante,Float odd){
        super(e,rs,vA,gP,participante);
        setOdd(odd);
    }
    public ApostaSimples(ApostaSimples a){
        super(a);
        setOdd(a.getOdd());
    }

    public void setOdd(Float odd) {
        this.odd = odd;
    }

    public Float getOdd() {
        return this.odd;
    }

    public ApostaSimples clone(){
        return new ApostaSimples(this);
    }
}
