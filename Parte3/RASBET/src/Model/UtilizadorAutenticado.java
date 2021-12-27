package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UtilizadorAutenticado extends Utilizador {
    private String nome;
    private String email;
    private String password;
    private Date data_nascimento;
    private Float saldo;
    private ArrayList<Aposta> historico;

    public UtilizadorAutenticado(){
        super();
        this.nome = new String();
        this.email = new String();
        this.password= new String();
        this.data_nascimento = new Date();
        this.saldo = (float)0;
        this.historico = new ArrayList<Aposta>();

    }

    public UtilizadorAutenticado(Boolean lg,String n, String e, String p, Date dt, Float s){
        super(lg);
        this.nome = n;
        this.email=e;
        this.password=p;
        this.data_nascimento=dt;
        this.saldo=s;
        this.historico= new ArrayList<Aposta>();
    }

    public UtilizadorAutenticado(UtilizadorAutenticado u){
        super(u);
        setNome(u.getNome());
        setEmail(u.getEmail());
        setPassword(u.getPassword());
        setData_nascimento(u.getData_nascimento());
        setSaldo(u.getSaldo());
        setHistorico(u.getHistorico());
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public Float getSaldo() {
        return saldo;
    }


    public ArrayList<Aposta> getHistorico() {
        ArrayList<Aposta> ap= new ArrayList<>();
        for(Aposta a : this.historico)
            ap.add(a.clone());
        return historico;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }


    public void setHistorico(ArrayList<Aposta> hist) {
        this.historico=new ArrayList<>();
        for(Aposta a : hist)
            this.historico.add(a);
    }

    @Override
    public String toString() {
        return "UtilizadorAutenticado{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", data_nascimento=" + data_nascimento +
                ", saldo=" + saldo +
                ", historico=" + historico +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UtilizadorAutenticado that = (UtilizadorAutenticado) o;
        return Objects.equals(nome, that.nome)
                && Objects.equals(email, that.email)
                && Objects.equals(password, that.password)
                && Objects.equals(data_nascimento, that.data_nascimento)
                && Objects.equals(saldo, that.saldo)
                && Objects.equals(historico, that.historico);
    }


    protected Object clone() {
        return new UtilizadorAutenticado(this);
    }
}
