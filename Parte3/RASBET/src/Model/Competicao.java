package Model;

public class Competicao {
    private String nome;
    private String id;

    public Competicao(){
        this.nome=new String();
        this.id = "";
    }

    public Competicao(String id, String n){
        this.nome=n;
        this.id=id;
    }
    public Competicao(Competicao c){
        this.nome=c.getNome();
        this.id=c.getId();
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    protected Competicao clone() {
        return new Competicao(this);
    }

    @Override
    public String toString() {
        return "Competicao{" +
                "nome='" + nome + '\''+
                '}';
    }
}
