package Model;

public class ApostaSimples extends Aposta{
    private Jogo jogo;
    private int opcao;
    private Float odd;

    public ApostaSimples(){
        super();
    }

    public ApostaSimples(int id,String e, Float vA,Jogo j, int op){
        super(id,e,vA);
        this.opcao= op;
        this.jogo=j;
    }
    public ApostaSimples(int id,String e, Float vA,Jogo j, int op, Float odd){
        super(id,e,vA);
        this.opcao= op;
        this.jogo=j;
        this.odd=odd;
    }
    public ApostaSimples(ApostaSimples a){
        super(a);
    }

    public Float getOdd() {
        return odd;
    }

    public ApostaSimples clone(){
        return new ApostaSimples(this);
    }

    public Jogo getJogo() {
        return jogo;
    }

    public int getOpcao() {
        return opcao;
    }
}
