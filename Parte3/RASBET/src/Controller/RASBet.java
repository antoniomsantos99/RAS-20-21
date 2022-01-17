package Controller;


import Exceptions.PasswordIncorreta;
import Data.*;
import Model.Aposta;
import Model.Carteira;
import Model.UtilizadorAutenticado;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class RASBet {
    private Map<String, UtilizadorAutenticado> utilizadores;
    private Map<Integer, Sessao> sessoes;

    public RASBet(){
        this.utilizadores= UtilizadorDAO.getInstance();
    }

    public void run() {
        Sessao atual = new Sessao();

    }

    public Boolean login(Sessao s, String email,String password) throws PasswordIncorreta {
        boolean sucesso = false;
        UtilizadorAutenticado u = this.utilizadores.get(email); //fazer pedido do utilizador
        if(Objects.equals(u.getPassword(), password)){
            sucesso= true;
            u.setLoggedIn(true);
        }
        else throw new PasswordIncorreta();
        s.setUtilizador(u);
        return sucesso;
    }

    public Boolean existeUtilizador(String email){
        return this.utilizadores.containsKey(email);
    }

    public void registarUtilizador(Sessao s, String username,String email,String password){
        Carteira c = new Carteira();
        ArrayList<Aposta> hist = new ArrayList<>();
        UtilizadorAutenticado u = new UtilizadorAutenticado(false,username,email,password,c,hist);
        this.utilizadores.put(email,u);
        s.setUtilizador(u);
    }

}