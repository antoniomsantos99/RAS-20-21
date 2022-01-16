package Model;

public class Moeda {
    private String nome;
    private double exchange;

    public Moeda() {}

    public Moeda(String nome, double exchange) {
        this.nome = nome;
        this.exchange = exchange;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String n) {
        nome = n;
    }

    public double getExchange() {
        return exchange;
    }

    public void setExchange(double e) {
        exchange = e;
    }
}
