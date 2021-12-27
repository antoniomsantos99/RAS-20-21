package Model;

import java.util.ArrayList;

public class ApostaMultipla extends Aposta{
    private Float totalOdds;
    private ArrayList<ApostaSimples> apostas;

    public ApostaMultipla(){
        super();
        this.totalOdds=(float)0;
        this.apostas= new ArrayList<ApostaSimples>();
    }
    public ApostaMultipla(String e, Boolean rs, Float vA, Float gP, String participante,Float to){
        super(e,rs,vA,gP,participante);
        setTotalOdds(to);
        this.apostas= new ArrayList<ApostaSimples>();
    }
    public ApostaMultipla(ApostaMultipla a){
        super(a);
        setTotalOdds(a.getTotalOdds());
        setApostas(a.getApostas());
    }

    public void setTotalOdds(Float totalOdds) {
        this.totalOdds = totalOdds;
    }

    public Float getTotalOdds() {
        return totalOdds;
    }


    public ArrayList<ApostaSimples> getApostas() {
        ArrayList<ApostaSimples> ap= new ArrayList<>();
        for(ApostaSimples a : this.apostas)
            ap.add(a.clone());
        return ap;
    }


    public void setApostas(ArrayList<ApostaSimples> aps) {
        this.apostas=new ArrayList<>();
        for(ApostaSimples a : aps)
            this.apostas.add(a);
    }


    public ApostaMultipla clone() {
        return new ApostaMultipla(this);
    }
}
