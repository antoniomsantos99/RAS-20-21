package Model;

public class ApostaSimples extends Aposta{
    private Jogo jogo;
    private int opcao;


    public ApostaSimples(){
        super();
    }
    public ApostaSimples(String e, Float vA,int op, Jogo j){
        super(e,vA);
        this.opcao= op;
        this.jogo=j;
    }
    public ApostaSimples(ApostaSimples a){
        super(a);
    }


    public ApostaSimples clone(){
        return new ApostaSimples(this);
    }
}
