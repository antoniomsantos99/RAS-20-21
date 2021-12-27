package Model;

public abstract class Aposta {
    private String estado;
    private Boolean resultado;
    private Float valorApostado;
    private Float ganhosPossiveis;
    private String participante;


    public Aposta(){
        this.estado=new String();
        this.resultado=false;
        this.valorApostado=(float)0;
        this.ganhosPossiveis=(float)0;
        this.participante=new String();
    }

    public Aposta(String e, Boolean rs, Float vA, Float gP, String participante){
        this.estado = e;
        this.resultado=rs;
        this.valorApostado=vA;
        this.ganhosPossiveis=gP;
        this.participante=participante;
    }
    public Aposta(Aposta a){
        setEstado(a.getEstado());
        setResultado(a.getResultado());
        setValorApostado(a.getValorApostado());
        setGanhosPossiveis(a.getGanhosPossiveis());
        setParticipante(a.getParticipante());
    }

    public String getEstado() {
        return estado;
    }

    public Boolean getResultado() {
        return resultado;
    }

    public Float getGanhosPossiveis() {
        return ganhosPossiveis;
    }

    public Float getValorApostado() {
        return valorApostado;
    }

    public String getParticipante() {
        return participante;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setGanhosPossiveis(Float ganhosPossiveis) {
        this.ganhosPossiveis = ganhosPossiveis;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public void setValorApostado(Float valorApostado) {
        this.valorApostado = valorApostado;
    }

    public abstract Aposta clone();
}
