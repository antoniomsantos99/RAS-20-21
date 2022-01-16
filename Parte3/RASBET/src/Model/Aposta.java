package Model;

public abstract class Aposta {
    private final int id;
    private String estado;
    private Float valorApostado;
    private Float ganhosPossiveis;
    private String participante;
    private Boolean resultado;
    private Jogo jogo;

    public Aposta(){
        this.id=0;
        this.estado= "";
        this.valorApostado=(float)0;
        this.ganhosPossiveis=(float)0;
        this.participante="";
        this.resultado=false;
    }

    public Aposta(int id,String e,Float va, String p){
        this.id = id;
        this.estado = e;
        this.valorApostado = va;
       // this.ganhosPossiveis= calculaganhosPossiveis();
        this.participante = p;
    }
    public Aposta(Aposta a){
        this.id = a.getId();
        setEstado(a.getEstado());
        setValorApostado(a.getValorApostado());
        setGanhosPossiveis(a.getGanhosPossiveis());
        setParticipante(a.getParticipante());
        setResultado(a.getResultado());
    }

    public int getId() { return id; }

    public String getEstado() {
        return estado;
    }

    public Float getGanhosPossiveis() {
        return ganhosPossiveis;
    }

    public Float getValorApostado() {
        return valorApostado;
    }

    public String getParticipante() { return participante;}

    public Boolean getResultado() { return resultado; }

    public Jogo getJogo(){
        return jogo.clone();
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setGanhosPossiveis(Float ganhosPossiveis) {
        this.ganhosPossiveis = ganhosPossiveis;
    }

    public void setValorApostado(Float valorApostado) {
        this.valorApostado = valorApostado;
    }

    public void setParticipante(String p) {
        participante = p;
    }

    public void setResultado(Boolean r) {
        resultado = r;
    }

    public abstract Aposta clone();
}
