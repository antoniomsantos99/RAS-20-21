package Model;

public class Moeda {
    private String nome;
    private double exchangeComEuro;

    public Moeda() {
        this.nome = "Euro";
        this.exchangeComEuro = 0.0;
    }

    public Moeda(String nome, double exchange) {
        this.nome = nome;
        this.exchangeComEuro = exchange;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String n) {
        nome = n;
    }

    public double getExchange() {
        return exchangeComEuro;
    }

    public void setExchange(double e) {
        exchangeComEuro = e;
    }
}
