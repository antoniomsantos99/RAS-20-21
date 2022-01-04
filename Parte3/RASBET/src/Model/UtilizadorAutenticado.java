package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UtilizadorAutenticado extends Utilizador {
    private String username;
    private String email;
    private String password;
    private Date data_nascimento;
    private Carteira carteira;
    private List<Aposta> historico;

    public UtilizadorAutenticado(){
        super();
        this.username = new String();
        this.email = new String();
        this.password= new String();
        this.data_nascimento = new Date();
        this.saldo = (float)0;
        this.historico = new ArrayList<Aposta>();

    }

    public UtilizadorAutenticado(Boolean lg,String n, String e, String p, Date dt, Carteira c, List<Aposta> hist){
        super(lg);
        this.username = n;
        this.email=e;
        this.password=p;
        this.data_nascimento=dt;
        this.carteira = c;
        this.historico= hist;
    }

    public UtilizadorAutenticado(UtilizadorAutenticado u){
        super(u);
        setUsername(u.getUsername());
        setEmail(u.getEmail());
        setPassword(u.getPassword());
        setData_nascimento(u.getData_nascimento());
        setSaldo(u.getSaldo());
        setHistorico(u.getHistorico());
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCarteira(Carteira carteira
        this.carteira = carteira;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }


    public void setHistorico(ArrayList<Aposta> hist) {
        this.historico=new ArrayList<>();
        for(Aposta a : hist)
            this.historico.add(a);
    }


public 

    @Override
    public String toString() {
        return "UtilizadorAutenticado{" +
                "username='" + username + '\'' +
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
        return Objects.equals(username, that.username)
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