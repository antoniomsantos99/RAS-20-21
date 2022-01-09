package Model;

public abstract class Aposta {
    private int id;
    private String estado;
    private Float valorApostado;
    private Float ganhosPossiveis;


    public Aposta(){
        this.id=0;
        this.estado=new String();
        this.valorApostado=(float)0;
        this.ganhosPossiveis=(float)0;
    }

    public Aposta(int id,String e,Float va){
        this.id = id;
        this.estado = e;
        this.valorApostado = va;
       // this.ganhosPossiveis= calculaganhosPossiveis();
    }
    public Aposta(Aposta a){
        this.id = a.getId();
        setEstado(a.getEstado());
        setValorApostado(a.getValorApostado());
        setGanhosPossiveis(a.getGanhosPossiveis());
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

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setGanhosPossiveis(Float ganhosPossiveis) {
        this.ganhosPossiveis = ganhosPossiveis;
    }


    public void setValorApostado(Float valorApostado) {
        this.valorApostado = valorApostado;
    }

    public abstract Aposta clone();
}
