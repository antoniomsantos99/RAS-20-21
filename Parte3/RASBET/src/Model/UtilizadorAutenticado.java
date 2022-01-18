package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UtilizadorAutenticado extends Utilizador {
    private String username;
    private String email;
    private String password;
    private Date data_nascimento;
    private Carteira carteira;
    private ArrayList<Aposta> historico;

    public UtilizadorAutenticado(){
        super();
        this.username = "";
        this.email = "";
        this.password= "";
        this.data_nascimento = new Date();
        this.carteira = new Carteira();
        this.historico = new ArrayList<>();

    }

    public UtilizadorAutenticado(Boolean lg,String n, String e, String p, Carteira c, ArrayList<Aposta> hist){
        super(lg);
        this.username = n;
        this.email=e;
        this.password=p;
        this.carteira = c;
        this.historico= hist;
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

    public Carteira getCarteira() { return carteira; }

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

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }


    public void setHistorico(ArrayList<Aposta> hist) {
        this.historico=new ArrayList<>();
        this.historico.addAll(hist);
    }

    @Override
    public String toString() {
        return "UtilizadorAutenticado{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", data_nascimento=" + data_nascimento +
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
                && Objects.equals(historico, that.historico);
    }


    /*protected Object clone() {
        return new UtilizadorAutenticado(this);
    }*/
}
