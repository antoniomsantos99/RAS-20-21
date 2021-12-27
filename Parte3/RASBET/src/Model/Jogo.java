package Model;

import java.util.ArrayList;
import java.util.Date;

public class Jogo {
    private Integer id;
    private Date data;
    private String estado;
    private ArrayList<String> participantes;
    private ArrayList<Float> odds;
    private String resultado;


    public Jogo(){
        this.id=0;
        this.data= new Date();
        this.estado = new String();
        this.resultado = new String();
        this.participantes= new ArrayList<>();
        this.odds= new ArrayList<>();
    }

    public Jogo(Integer id, Date dt, String e, ArrayList<String> p, ArrayList<Float> o,String r){
        this.id=id;
        this.data= dt;
        this.estado = e;
        this.resultado = r;
        setParticipantes(p);
        setOdds(o);
    }
    public Jogo(Jogo j){
        this.id=j.getId();
        this.data= j.getData();
        this.estado = j.getEstado();
        this.resultado = j.getResultado();
        this.odds=j.getOdds();
        this.participantes=j.getParticipantes();
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOdds(ArrayList<Float> odds) {
        this.odds = odds;
    }

    public void setParticipantes(ArrayList<String> participantes) {
        this.participantes = participantes;
    }

    public String getEstado() {
        return estado;
    }

    public Date getData() {
        return data;
    }

    public ArrayList<Float> getOdds() {
        return odds;
    }

    public ArrayList<String> getParticipantes() {
        return participantes;
    }

    public Integer getId() {
        return id;
    }

    public String getResultado() {
        return resultado;
    }


    protected Jogo clone()  {
        return new Jogo(this);
    }
}
