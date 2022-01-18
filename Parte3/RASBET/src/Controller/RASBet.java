package Controller;

import Data.*;
import Exceptions.*;
import Model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    //FAZER METODOS DE EDITAR PERFIL,

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

    public boolean existeJogo(String id){
        return JogoDAO.getInstance().containsKey(id);
    }
    public double getExchangeRate(String m1,String m2){
        return user.getCarteira().getExchangeRate(m1,m2);
    }


    public float getOddJogo(String id, int escolha){
        return JogoDAO.getInstance().getOdd(id,escolha);
    }

    public boolean fazerApostaSimples(String id_jogo, float odd, float valor, String moeda, String escolha){
        int id = (int) (Math.random()*1000000000);
        Jogo j = JogoDAO.getInstance().get(id_jogo);
        boolean success = false;
        int choice = switch (escolha) {
            case "Home" -> 1;
            case "Tie" -> 2;
            case "Away" -> 3;
            default -> 0;
        };
        ApostaSimples a = new ApostaSimples(id,"Ativa",odd*valor,j,choice,odd);
        /*ArrayList<String> lista_jogos = new ArrayList<>();
        ArrayList<Integer> choices_list = new ArrayList<>();
        lista_jogos.add(j.getId());
        choices_list.add(choice);*/
        success=putTransaccao(user.getEmail(),moeda,-Math.abs(valor));
        if(success) user.addAposta(a,valor,moeda);
        return success;

    }

    public boolean fazerApostaMultipla(Map<String, Float> idjogo_odd, List<String> choices, float valor, String moeda){
        int id = (int) (Math.random()*1000000000);
        int i = 0;
        float odd_final = 1;
        boolean success = false;
        ArrayList<Jogo> lista_jogos = new ArrayList<>();
        ArrayList<String> lista_ids = new ArrayList<>();
        ArrayList<Integer> choices_list = new ArrayList<>();
        for(String id_jogo : idjogo_odd.keySet()){
            Jogo j = JogoDAO.getInstance().get(id_jogo);
            lista_jogos.add(j);
            lista_ids.add(j.getId());
            Float odd = idjogo_odd.get(id_jogo);
            odd_final = odd_final * odd;
            String escolha = choices.get(0);
            int choice = switch (escolha) {
                case "Home" -> 1;
                case "Tie" -> 2;
                case "Away" -> 3;
                default -> 0;
            };
            System.out.println(choice);
            choices_list.add(choice);
            i++;
        }
        ApostaMultipla a = new ApostaMultipla(id,"Ativa",odd_final*valor,lista_jogos,choices_list,odd_final);
        success = putTransaccao(user.getEmail(), moeda, -Math.abs(valor));
        if(success) user.addAposta(a,valor,moeda);
        return success;
    }

    public void mudarPass(String email,String pass){
        UtilizadorAutenticado u = UtilizadorDAO.getInstance().get(email);
        u.setPassword(pass);
        UtilizadorDAO.getInstance().put(email,u);
    }

    public void mudarUsername(String email, String username){
        user.setUsername(username);
        UtilizadorDAO.getInstance().putUsername(email,user);
    }

    public String getCarteira(){
        return user.getCarteira().toString();
    }
/*
    public void fazerAposta(String id_utilizador, String e, Float va) {
        Aposta a = Aposta(e, va);
    }
*/
}