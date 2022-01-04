package Model;

import java.util.ArrayList;
import java.util.Date;

public class Jogo {
    private int id;
    private Competicao competicao;
    private List<String> participantes;
    private List<Float> odds;
    private String resultado;
    private Date data;
    private String localizacao;


    public Jogo(){
        this.id=0;
        this.competicao = new Competicao();
        this.participantes= new ArrayList<>();
        this.odds= new ArrayList<>();
        this.resultado = new String();
        this.data= new Date();
        this.localizacao = new String();
    }

    public Jogo(int id, int competicao, ArrayList<String> p, ArrayList<Float> o, String r, Date dt, String l){
        this.id=id;
        this.competicao = competicao;
        setParticipantes(p);
        setOdds(o);
        this.resultado = r;
        this.data= dt;
        this.localizacao = l;
        
    }
    public Jogo(Jogo j){
        this.id=j.getId();
        this.competicao = j.getCompeticao;
        this.participantes=j.getParticipantes();
        this.odds=j.getOdds();
        this.resultado = j.getResultado();
        this.data= j.getData();
        this.localizacao = j.getLocalizacao();
    }


    // SETS ---------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setCompeticao(Competicao ompeticao) {
        this.competicao = competicao;
    }

    public void setParticipantes(List<String> part) {
        this.participantes = new ArrayList<>();
        for(String p : part)
            this.participantes.add(p);
    }

    public void setOdds(ArrayList<Float> odds) {
        this.odds = new ArrayList<>();
        for(Integer o : odds)
            this.odds.add(o);
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }


    // GETS ---------------------------------------

    public int getId() {
        return id;
    }

    public int getCompeticao() {
        return competicao;
    }

    public List<String> getParticipantes() {
        List<String> participantes = new ArrayList<>();
        for(String p : this.participantes)
            competicoes.add(p.clone());
        return participantes;
    }

    public List<String> getOdds() {
        List<Integer> odds = new ArrayList<>();
        for(Integer o : this.odds)
            odds.add(o.clone());
        return odds;
    }

    public String getResultado() {
        return resultado;
    }

    public Date     () {
        return data;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    
    


    protected Jogo clone()  {
        return new Jogo(this);
    }
}
