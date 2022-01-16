package Model;

public class ApostaSimples extends Aposta{
    //private Jogo jogo;
    private int opcao;
    private Float odd;

    public ApostaSimples(){
        super();
    }
    public ApostaSimples(int id,String e, Float vA,String p, int op){
        super(id,e,vA,p);
        this.opcao= op;
        //this.jogo=j;
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
}
