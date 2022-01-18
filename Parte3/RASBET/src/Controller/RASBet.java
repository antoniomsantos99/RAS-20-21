package Controller;


import Exceptions.PasswordIncorreta;
import Data.*;
import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RASBet {
    private Map<String, UtilizadorAutenticado> utilizadores;

    public RASBet(){
        this.utilizadores= UtilizadorDAO.getInstance();
    }

    public void run() {

    }

    public Boolean login(String email,String password) throws PasswordIncorreta {
        boolean sucesso = false;
        UtilizadorAutenticado u = this.utilizadores.get(email); //fazer pedido do utilizador
        System.out.println(u.getCarteira().toString());
        if(Objects.equals(u.getPassword(), password)){
            sucesso= true;
            u.setLoggedIn(true);
        }
        else throw new PasswordIncorreta();
        return sucesso;
    }

    public Boolean existeUtilizador(String email){
        return this.utilizadores.containsKey(email);
    }

    public void registarUtilizador(String username,String email,String password){
        Carteira c = new Carteira();
        ArrayList<Aposta> hist = new ArrayList<>();
        UtilizadorAutenticado u = new UtilizadorAutenticado(false,username,email,password,c,hist);
        this.utilizadores.put(email,u);
    }

    public List<Aposta> visualizarHistorico(String email){
        UtilizadorAutenticado u = UtilizadorDAO.getInstance().get(email);
        return u.getHistorico();
    }

    //FAZER METODOS DE EDITAR PERFIL, LEVANTAR/DEPOSITAR E ALTERACAO SALDO



    public List<Jogo> getJogos(){
        return JogoDAO.getInstance().getJogos();
    }

}