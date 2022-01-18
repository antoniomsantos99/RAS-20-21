package Model;


import java.util.HashMap;
import java.util.Map;


public class Carteira {
    Map<Moeda, Double> moedas;
/*
    private float eur;  // Euro
    private float usd;  // US Dollar
    private float gbp;  // Libra inglesa
    private float ada;  // Cardano
*/

    public Carteira(){
        moedas = new HashMap<>();
    }
    /*
        public Carteira(float euro,float usd, float gbp, float ada){
            this.eur = 0;
            this.usd = 0;
            this.gbp = 0;
            this.ada = 0;
        }
    */
    public void addMoeda(Moeda m, double d) {
        double before = moedas.getOrDefault(m, 0.0);
        moedas.put(m,before+d);
    }

    @Override
    public String toString() {
        return "Carteira{" +
                "moedas=" + moedas +
                '}';
    }
}
