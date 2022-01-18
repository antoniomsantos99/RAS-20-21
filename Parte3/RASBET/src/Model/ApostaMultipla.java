package Model;

import java.util.ArrayList;
import java.util.List;

public class ApostaMultipla extends Aposta{
    private List<Integer> opcoes;
    private ArrayList<Jogo> jogos;
    private Float totalOdds;

    public ApostaMultipla(){
        super();
        this.opcoes= new ArrayList<>();
        this.jogos = new ArrayList<>();
    }
    public ApostaMultipla(int id,String e, Float vA,ArrayList<Jogo> jgs, ArrayList<Integer> op){
        super(id,e,vA);
        this.opcoes= op;
        this.jogos = jgs;
    }
    public ApostaMultipla(int id,String e, Float vA,ArrayList<Jogo> jgs, ArrayList<Integer> op,Float odds){
        super(id,e,vA);
        this.opcoes= op;
        this.jogos = jgs;
        this.totalOdds = odds;
    }
    public ApostaMultipla(ApostaMultipla a){
        super(a);
    }
/*
    public ArrayList<Aposta> getApostas() {
        ArrayList<Aposta> aps = new ArrayList<>();
        for(Aposta a : apostas)
            aps.add(a.clone());
        return aps;
    }
    */


    public ArrayList<Jogo> getJogos() {
        ArrayList<Jogo> jogos = new ArrayList<>();
        for(Jogo j : this.jogos)
            jogos.add(j);
        /*for(Jogo a : this.jogos)
            ap.add(a.clone());*/
        return jogos;
    }

    public Float getTotalOdds() {
        return totalOdds;
    }

    public void addAposta(Jogo j, Integer o) {
        jogos.add(j.clone());
        opcoes.add(o);
        totalOdds *= j.getOdds()[o];
    }

    public Boolean checkResultados() {
        boolean r = true;
        for(int i = 0;i<opcoes.size();i++)
            if(opcoes.get(i)!=jogos.get(i).checkResultado()) {
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

    public List<Integer> getOpcoes() {
        return opcoes;
    }
}

