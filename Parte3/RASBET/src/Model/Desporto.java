package Model;

import java.util.ArrayList;

public class Desporto {
    private int  id;
    private String nome;
    

    public Desporto(){
        this.id = 0;
        this.nome=new String();
    }
    public Desporto(String n, int id){
        this.id=id;
        this.nome=n;
    }
    public Desporto(Desporto d){
        this.nome=d.getNome();
        this.id=d.getId();
    }

/*
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
*/
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
