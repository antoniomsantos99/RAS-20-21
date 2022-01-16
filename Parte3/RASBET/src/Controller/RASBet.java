package Controller;


import Exceptions.PasswordIncorreta;
import Data.*;
import Exceptions.UtilizadorExistente;
import Model.*;
import Model.Aposta;
import Model.Carteira;
import Model.UtilizadorAutenticado;
import View.Menu;
import View.RASBetView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class RASBet {
    private Map<String, UtilizadorAutenticado> utilizadores;
    private Map<Integer, Sessao> sessoes;
    private final Menu view;

    public RASBet(){
        view = new Menu();
        this.utilizadores= UtilizadorDAO.getInstance();
    }

    public void run() {
        Sessao atual = new Sessao();
        Menu menu = new Menu(new String[]{
                "Menu de Login.",
                "Menu de Registo."
        });


        menu.setHandler(1, this::menuLogin);
        menu.setHandler(2, this::menuRegisto);


        menu.executa(1);
    }

    public Boolean loginUtilizador(Sessao s, String email,String password) throws PasswordIncorreta {
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

    public void registarUtilizadorSistema(Sessao s, String username,String email,String password){
        Carteira c = new Carteira();
        ArrayList<Aposta> hist = new ArrayList<>();
        UtilizadorAutenticado u = new UtilizadorAutenticado(false,username,email,password,c,hist);
        this.utilizadores.put(email,u);
        s.setUtilizador(u);
    }

    private void menuPrincipal(){
        Menu menu = new Menu(new String[]{
                "Menu de Login.",
                "Menu de Registo."
        });


        menu.setHandler(1, this::menuLogin);
        menu.setHandler(2, this::menuRegisto);


        menu.executa(1);
    }

    private void menuLogin(){
        Menu menu = new Menu(new String[]{
                "Utilizador Autenticado",
                "Utilizador Nao Autenticado."
        });


        menu.setHandler(1, this::tratarLogin);
        menu.setHandler(2, this::tratarRegisto);

        menu.executa(2);
    }

    private void menuRegisto(){
        Menu menu = new Menu(new String[]{
                "Utilizador Autenticado",
                "Utilizador Nao Autenticado."
        });

        menu.setHandler(1, this::tratarLogin);
        menu.setHandler(2, this::tratarRegisto);

        menu.executa(3);
    }


    private void tratarLogin(){
        try{
            int i =1;
            String password;
            String email = new String();
            System.out.println("Email:   ");
            email = scin.nextLine();

            System.out.println("Email:   ");
            while(i==1) {
                email = scin.nextLine();
                if(email.contains("@")) i =0;
            }

            System.out.println("Password:   ");
            password = scin.nextLine();


            this.model.loginUtilizador(email,password);
            System.out.println("'Login com sucesso '");

        } catch(NullPointerException | PasswordIncorreta e){
            if (e instanceof PasswordIncorreta) {
                System.out.println(("Password incorreta."));
            }
            else{
                System.out.println(e.getMessage());
            }
        }
    }

    private void tratarRegisto(){
        try{
            int i =1;
            String username, password;
            String email = "";
            String dataN;
            System.out.println("Username:");
            username = scin.nextLine();

            while(i==1) {
                System.out.println("Email:");
                email = scin.nextLine();
                if(email.contains("@")) i =0;
            }

            if (this.model.existeUtilizador(email)){
                throw new UtilizadorExistente();
            }

            System.out.println("Password:");
            password = scin.nextLine();

            System.out.println("Data Nascimento (yyyy/mm/dd):");
            dataN = scin.nextLine();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = format.parse(dataN);



            this.model.registarUtilizadorSistema(username,email,password);
            System.out.println("Utilizador com email '" + email + "' registado no sistema.");

        } catch(NullPointerException | UtilizadorExistente | ParseException e){
            if (e instanceof UtilizadorExistente) {
                System.out.println(("Utilizador já existe no sistema. Insira outro email."));
            }
            else{
                System.out.println(e.getMessage());
            }
        }
    }


}