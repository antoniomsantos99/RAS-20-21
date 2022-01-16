package Model;

import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private List<Aposta> apostas_atuais;
    private Utilizador utilizador;
    private Idioma idioma;
    private Moeda moeda;

    public Sessao() {
        this.utilizador = new UtilizadorNAutenticado();
        this.apostas_atuais = new ArrayList<>();
        this.idioma = new Idioma();
        this.moeda = new Moeda();
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador u) {
        utilizador = u;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma i) {
        idioma = i;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda m) {
        moeda = m;
    }

    public void addAposta(Aposta a) {
        apostas_atuais.add(a.clone());
    }
}
