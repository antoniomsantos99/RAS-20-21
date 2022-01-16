package Model;

import java.util.ArrayList;
import java.util.List;

public class ApostaMultipla extends Aposta{
    //private List<Integer> opcoes;
    //private ArrayList<Jogo> jogos;
    private ArrayList<ApostaSimples> apostas;
    private Float totalOdds;

    public ApostaMultipla(){
        super();
        //this.opcoes= new ArrayList<>();
        this.apostas= new ArrayList<>();
    }
    public ApostaMultipla(int id,String e, Float vA,String p,ArrayList<Jogo> jgs, ArrayList<Integer> op){
        super(id,e,vA,p);
       // setJogos(jgs);
        //setOp(op);
    }
    public ApostaMultipla(ApostaMultipla a){
        super(a);
        //setJogos(a.getJogos());
        //setOp(a.getOp());
    }

    public ArrayList<Aposta> getApostas() {
        ArrayList<Aposta> aps = new ArrayList<>();
        for(Aposta a : apostas)
            aps.add(a.clone());
        return aps;
    }

    public ArrayList<Jogo> getJogos() {
        ArrayList<Jogo> jogos = new ArrayList<>();
        for(Aposta a : apostas)
            jogos.add(a.getJogo());
        /*for(Jogo a : this.jogos)
            ap.add(a.clone());*/
        return jogos;
    }

    public Float getTotalOdds() {
        return totalOdds;
    }

    public void addAposta(ApostaSimples a) {
        apostas.add(a.clone());
        totalOdds *= a.getOdd();
    }

    public Boolean checkResultados() {
        boolean r = true;
        for(Aposta a : apostas)
            if(!a.getResultado()) {
                r = false;
                break;
            }
        return r;
    }

/*
    public ArrayList<Integer> getOp() {
        return new ArrayList<>(this.opcoes);
    }


    public void setOp(ArrayList<Integer> aps) {
        this.opcoes=new ArrayList<>();
        this.opcoes.addAll(aps);
    }


    public void setJogos(ArrayList<Jogo> aps) {
        this.jogos=new ArrayList<>();
        this.jogos.addAll(aps);
    }
*/

    public ApostaMultipla clone() {
        return new ApostaMultipla(this);
    }
}
