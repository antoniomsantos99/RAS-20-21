package View;

import Controller.RASBet;
import Exceptions.PasswordIncorreta;
import Exceptions.UtilizadorExistente;
import View.Menu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TextUi {
    private final Scanner scin;
    private final RASBet rasbet;

    public TextUi() {

        this.scin = new Scanner(System.in);
        rasbet = new RASBet();
    }

    public void run() {
        rasbet.comecarSessao();
        System.out.println("Bem vindo ao Gestor de Armazém!");
        this.menuPrincipal();
        System.out.println("A sair...");
    }

    private void menuPrincipal() {
        Menu menu = new Menu(new String[] {
                "Menu de Login.",
                "Menu de Registo."
        });

        menu.setHandler(1, this::menuLogin);
        menu.setHandler(2, this::menuRegisto);


        menu.run();
    }

    private void menuLogin(){
        Menu menu = new Menu(new String[]{
                "Utilizador Autenticado",
                "Utilizador Nao Autenticado."
        });


        menu.setHandler(1, this::tratarLogin);
        menu.setHandler(2, this::tratarRegisto);

        menu.run();
    }

    private void menuRegisto(){
        Menu menu = new Menu(new String[]{
                "Utilizador Autenticado",
                "Utilizador Nao Autenticado."
        });

        menu.setHandler(1, this::tratarLogin);
        menu.setHandler(2, this::tratarRegisto);

        menu.run();
    }


    private void tratarLogin(){
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

        try {
        //here it sends to controller
            rasbet.login(email,password);

        } catch(NullPointerException | PasswordIncorreta e){
            if (e instanceof PasswordIncorreta)
                System.out.println(("Password incorreta."));

            else System.out.println(e.getMessage());
        }
        System.out.println("'Login com sucesso '");
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

            if (rasbet.existeUtilizador(email)) // ?
                throw new UtilizadorExistente();


            System.out.println("Password:");
            password = scin.nextLine();

            System.out.println("Data Nascimento (yyyy/mm/dd):");
            dataN = scin.nextLine();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = format.parse(dataN);

            rasbet.registarUtilizador(username,email,password);
            System.out.println("Utilizador com email '" + email + "' registado no sistema.");

        } catch(NullPointerException | UtilizadorExistente | ParseException e){
            if (e instanceof UtilizadorExistente)
                System.out.println(("Utilizador já existe no sistema. Insira outro email."));

            else System.out.println(e.getMessage());
        }
    }
}
