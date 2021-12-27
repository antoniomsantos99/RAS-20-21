package Model;

import java.util.ArrayList;

public class Desporto {
    private String nome;
    private int  id;
    private ArrayList<Competicao> competicoes;

    public Desporto(){
        this.nome=new String();
        this.id = 0;
        this.competicoes= new ArrayList<>();
    }
    public Desporto(String n, int id){
        this.nome=n;
        this.id=id;
        this.competicoes= new ArrayList<>();
    }
    public Desporto(Desporto d){
        this.nome=d.getNome();
        this.id=d.getId();
        setCompeticoes(d.getCompeticoes());
    }

    public void setCompeticoes(ArrayList<Competicao> comp) {
        this.competicoes=new ArrayList<>();
        for(Competicao c : comp)
            this.competicoes.add(c);
    }

    public ArrayList<Competicao> getCompeticoes() {
        ArrayList<Competicao> competicoes= new ArrayList<>();
        for(Competicao c: this.competicoes)
            competicoes.add(c.clone());
        return competicoes;
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


}
