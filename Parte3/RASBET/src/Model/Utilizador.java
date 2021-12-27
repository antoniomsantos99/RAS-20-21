package Model;

import java.util.Objects;

public abstract class Utilizador {
    private boolean loggedIn;
    //private Sessao sessao;

    public Utilizador(){
        this.loggedIn= false;
    }
    public Utilizador(boolean logged){
        this.loggedIn= logged;
    }
    public Utilizador(Utilizador u){
        this.loggedIn= u.getLoggedIn();
    }


    public boolean getLoggedIn(){
        return this.loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void realizarApostas(){

    }

    public boolean isLoggedIn(){
        return loggedIn;
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(" Logged: ").append(this.loggedIn);
               // .append(" sessao: ").append(this.sessao);
        return s.toString();
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return loggedIn == that.loggedIn;
        // && this.sessao == that.getSessao;
    }

}
