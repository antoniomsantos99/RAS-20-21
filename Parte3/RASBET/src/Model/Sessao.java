package Model;

import java.util.List;

public class Sessao {
    private List<Aposta> apostas_atuais;
    private Utilizador utilizador;
    private Idioma idioma;
    private Moeda moeda;

    public Sessao() {}

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
