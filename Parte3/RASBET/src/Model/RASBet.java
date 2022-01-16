package Model;

import Data.*;
import Data.UtilizadorDAO;
import Exceptions.PasswordIncorreta;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class RASBet {
    private Map<String,UtilizadorAutenticado> utilizadores;


    public RASBet(){
        this.utilizadores= UtilizadorDAO.getInstance();

    }



    public Boolean loginUtilizador(String email,String password) throws PasswordIncorreta {
        Boolean sucesso = false;
        UtilizadorAutenticado u = this.utilizadores.get(email);
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

    public void registarUtilizadorSistema(String username,String email,String password){
        Carteira c = new Carteira();
        ArrayList<Aposta> hist = new ArrayList<>();
        UtilizadorAutenticado u = new UtilizadorAutenticado(false,username,email,password,c,hist);
        this.utilizadores.put(email,u);
    }


}