package Model;

import java.util.ArrayList;

public class Competicao {
    private String nome;
    private int id;
    private ArrayList<Jogo> jogos;

    public Competicao(){
        this.nome=new String();
        this.id = 0;
        this.jogos= new ArrayList<>();
    }
    public Competicao(String n, int id, ArrayList<Jogo> jgs){
        this.nome=n;
        this.id=id;
        setJogo(jgs);
    }
    public Competicao(Competicao c){
        this.nome=c.getNome();
        this.id=c.getId();
        setJogo(c.getJogos());
    }

    public void setJogo(ArrayList<Jogo> jogos) {
        this.jogos=new ArrayList<>();
        for(Jogo j : jogos)
            this.jogos.add(j);
    }

    public ArrayList<Jogo> getJogos() {
        ArrayList<Jogo> jogos= new ArrayList<>();
        for(Jogo j: this.jogos)
            jogos.add(j.clone());
        return jogos;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    protected Competicao clone() {
        return new Competicao(this);
    }
}
