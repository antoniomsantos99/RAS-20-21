package Model;


import Data.MoedaDAO;

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
        for(Moeda m : MoedaDAO.getInstance().getMoedas()){
            moedas.put(m,0d);
        }
    }

    public double getValorMoeda(String moeda) {
        for (Moeda m : moedas.keySet())
            if(m.getNome().equals(moeda))
                return moedas.get(m);
        return 0;
    }

    public double getExchangeRate(String moeda,String moeda2) {
        double ex1 = 0,ex2 = 1;
        for (Moeda m : moedas.keySet())
            if(m.getNome().equals(moeda))
                ex1 = m.getExchange();
            else if(m.getNome().equals(moeda2))
                ex2 = m.getExchange();
        return ex1/ex2;
    }


        public void addMoeda(String moeda, double d) {
       for (Moeda m : moedas.keySet())
           if(m.getNome().equals(moeda))
               moedas.put(m,moedas.get(m)+d);

    }


    @Override
    public String toString() {
        return "Carteira{" +
                "moedas=" + moedas +
                '}';
    }
}
