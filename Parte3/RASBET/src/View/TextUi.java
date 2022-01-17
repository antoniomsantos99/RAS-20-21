package View;

import Controller.RASBet;
import Exceptions.PasswordIncorreta;
import Exceptions.UtilizadorExistente;
import Languages.gestorIdiomas;
import View.Menu;

import java.io.IOException;
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
    private final gestorIdiomas gestIdiomas;

    public TextUi() throws IOException {
        this.gestIdiomas = new gestorIdiomas();
        this.scin = new Scanner(System.in);
        rasbet = new RASBet();
    }

    public void run() {
        //rasbet.comecarSessao();
        System.out.println(gestIdiomas.getTexto("welcome"));
        this.menuPrincipal();
        System.out.println(gestIdiomas.getTexto("goodbye"));
    }

    private void menuPrincipal() {
        Menu menu = new Menu(new String[] {
                gestIdiomas.getTexto("loginmenu"),
                gestIdiomas.getTexto("signmenu")
        },gestIdiomas);

        menu.setHandler(1, this::menuLogin);
        menu.setHandler(2, this::menuRegisto);


        menu.run();
    }

    private void menuLogin(){
        Menu menu = new Menu(new String[]{
                gestIdiomas.getTexto("userauth"),
                gestIdiomas.getTexto("usernoauth")
        },gestIdiomas);


        menu.setHandler(1, this::tratarLogin);
        menu.setHandler(2, this::tratarRegisto);

        menu.run();
    }

    private void menuRegisto(){
        Menu menu = new Menu(new String[]{
                gestIdiomas.getTexto("userauth"),
                gestIdiomas.getTexto("usernoauth")
        },gestIdiomas);

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
        /*
        try {
        //here it sends to controller
            //rasbet.login(email,password);

        } catch(NullPointerException | PasswordIncorreta e){
            if (e instanceof PasswordIncorreta)
                System.out.println(gestIdiomas.getTexto("badPass"));

            else System.out.println(e.getMessage());
        }
        System.out.println(gestIdiomas.getTexto("goodlogin"));
    */
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

            //rasbet.registarUtilizador(username,email,password);
            System.out.println(String.format(gestIdiomas.getTexto("signSuccess"),email));

        } catch(NullPointerException | UtilizadorExistente | ParseException e){
            if (e instanceof UtilizadorExistente)
                System.out.println(gestIdiomas.getTexto("userExists"));

            else System.out.println(e.getMessage());
        }
    }
}
