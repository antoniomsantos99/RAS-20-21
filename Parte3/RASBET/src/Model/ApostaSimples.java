package Model;

public class ApostaSimples extends Aposta{
    private Jogo jogo;
    private int opcao;


    public ApostaSimples(){
        super();
    }
    public ApostaSimples(int id,String e, Float vA,int op, Jogo j){
        super(id,e,vA);
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
