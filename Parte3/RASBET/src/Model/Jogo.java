package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Jogo {
    private int id;
    private String competicao;
    private String participante1;
    private String participante2;
    private float odd1;
    private float odd2;
    private float odd3;
    private String resultado;
    private Date data;
    private String localizacao;
    private String estado;


    public Jogo(){
        this.id=0;
        this.competicao = "";
        this.participante1 = "";
        this.participante2 = "";
        this.odd1= 0f;
        this.odd2= 0f;
        this.odd3= 0f;
        this.resultado = "";
        this.data= new Date();
        this.localizacao = "";
        this.estado = "";
    }

    public Jogo(int id,String c, String p1, String p2,float o1,float o2,float o3, String r, Date dt, String l,String e){
        this.id=id;
        this.competicao=c;
        this.participante1 = p1;
        this.participante2 = p2;
        this.odd1=o1;
        this.odd2=o2;
        this.odd3=o3;
        this.resultado = r;
        this.data= dt;
        this.localizacao = l;
        this.estado = e;
    }
    public Jogo(Jogo j){
        this.id=j.getId();
        this.participante1=j.getParticipante1();
        this.participante2=j.getParticipante2();
        this.odd1=j.getOdd1();
        this.odd2=j.getOdd2();
        this.odd3=j.getOdd3();
        this.resultado = j.getResultado();
        this.data= j.getData();
        this.localizacao = j.getLocalizacao();
    }


    // SETS ---------------------------------------

    public void setId(int id) {
        this.id = id;
    }



    public float getOdd1() {
        return odd1;
    }

    public float getOdd2() {
        return odd2;
    }

    public float getOdd3() {
        return odd3;
    }

    public String getParticipante1() {
        return participante1;
    }

    public String getParticipante2() {
        return participante2;
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


    public void setOdd1(float odd1) {
        this.odd1 = odd1;
    }

    public void setOdd2(float odd2) {
        this.odd2 = odd2;
    }

    public void setOdd3(float odd3) {
        this.odd3 = odd3;
    }

    public String getResultado() {
        return resultado;
    }

    public Date getData() {
        return data;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    protected Jogo clone()  {
        return new Jogo(this);
    }
}
