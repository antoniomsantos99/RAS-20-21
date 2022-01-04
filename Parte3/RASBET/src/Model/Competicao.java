package Model;

import java.util.ArrayList;

public class Competicao {
    private String nome;
    private int id;
    private Desporto desporto;

    public Competicao(){
        this.nome=new String();
        this.id = 0;
        this.desporto = new Desporto();
    }
    public Competicao(String n, int id, Desporto desporto){
        this.nome=n;
        this.id=id;
        this.desporto = desporto;
    }
    public Competicao(Competicao c){
        this.nome=c.getNome();
        this.id=c.getId();
        this.desporto = c.getDesporto();
    }
/*
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
*/

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public Desporto getDesporto() {
        return desporto;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int setDesporto() {
        this.desporto = desporto;
    }


    protected Competicao clone() {
        return new Competicao(this);
    }
}
