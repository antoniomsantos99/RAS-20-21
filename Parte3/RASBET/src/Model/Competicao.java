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

    public void setDesporto() {
        this.desporto = desporto;
    }


    protected Competicao clone() {
        return new Competicao(this);
    }
}
