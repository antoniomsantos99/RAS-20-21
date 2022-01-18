package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Jogo {
    private String id;
    private String competicao;
    private String participante1;
    private String participante2;
    private float odds[];
    private String resultado;
    private Date data;
    private String localizacao;
    private String estado;


    public Jogo(){
        this.id="";
        this.competicao = "";
        this.participante1 = "";
        this.participante2 = "";
        this.odds = new float[]{0f, 0f, 0f};
        this.resultado = "";
        this.data= new Date();
        this.localizacao = "";
        this.estado = "";
    }

    public Jogo(String id,String c, String p1, String p2,float o1,float o2,float o3, String r, Date dt, String l,String e){
        this.id=id;
        this.competicao=c;
        this.participante1 = p1;
        this.participante2 = p2;
        this.odds = new float[]{o1, o2, o3};
        this.resultado = r;
        this.data= dt;
        this.localizacao = l;
        this.estado = e;
    }
    public Jogo(Jogo j){
        this.id=j.getId();
        this.participante1=j.getParticipante1();
        this.participante2=j.getParticipante2();
        this.odds=j.getOdds();
        this.resultado = j.getResultado();
        this.data= j.getData();
        this.localizacao = j.getLocalizacao();
    }


    // SETS ---------------------------------------

    public void setId(String id) {
        this.id = id;
    }



    public float[] getOdds() {
        return odds;
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

    public String getId() {
        return id;
    }


    public void setOdd1(float[] odd1) {
        this.odds = odd1;
    }


    public String getResultado() {
        return resultado;
    }

    public int checkResultado() {
        int[] results = Arrays.stream(resultado.split("-")).mapToInt(n -> Integer.parseInt(n)).toArray();
        if(results[0]==results[1]) return 3;
        else return (results[0]>results[1] ? 1 : 2);

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

    @Override
    public String toString() {
        return "Jogo{" +
                "id='" + id + '\'' +
                ", competicao='" + competicao + '\'' +
                ", participante1='" + participante1 + '\'' +
                ", participante2='" + participante2 + '\'' +
                ", odds=" + Arrays.toString(odds) +
                ", resultado='" + resultado + '\'' +
                ", data=" + data +
                ", localizacao='" + localizacao + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
