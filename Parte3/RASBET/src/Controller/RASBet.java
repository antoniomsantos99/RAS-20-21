package Controller;

import Data.*;
import Exceptions.*;
import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RASBet {
    private UtilizadorAutenticado user;
    public RASBet(){
        UtilizadorAutenticado user = null;
    }

    public void run() {

    }

    public Boolean login(String email,String password) throws PasswordIncorreta, UtilizadorNExistente{
        boolean existe=existeUtilizador(email);
        if(!existe) throw new UtilizadorNExistente();
        UtilizadorAutenticado u = UtilizadorDAO.getInstance().get(email); //fazer pedido do utilizador
        System.out.println(u.getCarteira().toString());
        if(Objects.equals(u.getPassword(), password)){
            u.setLoggedIn(true);
            user = u;
        }
        else throw new PasswordIncorreta();
        return existe;
    }

    public Boolean existeUtilizador(String email){
        return UtilizadorDAO.getInstance().containsKey(email);
    }

    public void registarUtilizador(String username,String email,String password){
        Carteira c = new Carteira();
        ArrayList<Aposta> hist = new ArrayList<>();
        UtilizadorAutenticado u = new UtilizadorAutenticado(false,username,email,password,c,hist);
        UtilizadorDAO.getInstance().put(email,u);
    }

    public List<Aposta> getHistorico(String email){
        UtilizadorAutenticado u = UtilizadorDAO.getInstance().get(email);
        return u.getHistorico();
    }

    //FAZER METODOS DE EDITAR PERFIL, LEVANTAR/DEPOSITAR E ALTERACAO SALDO

    public List<Competicao> getCompeticoes(char desporto){
        return CompeticaoDAO.getInstance().getCompFrom(desporto);
    }
    public int getCompeticoesSize(char desporto){
        return getCompeticoes(desporto).size();
    }

    public List<Jogo> getJogos(String competicao) {
        return JogoDAO.getInstance().getJogosComp(competicao);
    }

    public List<Jogo> getJogosWithOdds(){
        return JogoDAO.getInstance().getJogosWithOdds();
    }

    public List<Jogo> getJogosWithOddsFromComp(String i){
       return JogoDAO.getInstance().getJogosWithOddsFromComp(i);
    }

    public List getMoedas(){
        return MoedaDAO.getInstance().getMoedas().stream().map(e->e.getNome()).collect(Collectors.toList());
    }

    public Boolean putTransaccao(String utilizador,String m, double valor){
        if(user.getCarteira().getValorMoeda(m) < Math.abs(valor) && valor < 0)
            return false;
        MoedaDAO.getInstance().putTransaccao(utilizador,m,valor);
        user.getCarteira().addMoeda(m,valor);
        return true;

    }

    public double getExchangeRate(String m1,String m2){
        return user.getCarteira().getExchangeRate(m1,m2);
    }


    public float getOddJogo(String id, int escolha){
        return JogoDAO.getInstance().getOdd(id,escolha);
    }
/*
    public void fazerAposta(String id_utilizador, String e, Float va) {
        Aposta a = Aposta(e, va);
    }
*/
}