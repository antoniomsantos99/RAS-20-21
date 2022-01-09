package Model;

import java.util.ArrayList;
import java.util.List;

public class ApostaMultipla extends Aposta{
    private List<Integer> opcoes;
    private ArrayList<Jogo> jogos;

    public ApostaMultipla(){
        super();
        this.opcoes= new ArrayList<>();
        this.jogos= new ArrayList<>();
    }
    public ApostaMultipla(String e, Float vA,ArrayList<Jogo> jgs, ArrayList<Integer> op){
        super(e,vA);
        setJogos(jgs);
        setOp(op);
    }
    public ApostaMultipla(ApostaMultipla a){
        super(a);
        setJogos(a.getJogos());
        setOp(a.getOp());
    }

    public ArrayList<Jogo> getJogos() {
        ArrayList<Jogo> ap= new ArrayList<>();
        for(Jogo a : this.jogos)
            ap.add(a.clone());
        return ap;
    }


    public ArrayList<Integer> getOp() {
        ArrayList<Integer> ap= new ArrayList<>();
        for(Integer a : this.opcoes)
            ap.add(a);
        return ap;
    }


    public void setOp(ArrayList<Integer> aps) {
        this.opcoes=new ArrayList<>();
        for(Integer a : aps)
            this.opcoes.add(a);
    }


    public void setJogos(ArrayList<Jogo> aps) {
        this.jogos=new ArrayList<>();
        for(Jogo a : aps)
            this.jogos.add(a);
    }


    public ApostaMultipla clone() {
        return new ApostaMultipla(this);
    }
}
