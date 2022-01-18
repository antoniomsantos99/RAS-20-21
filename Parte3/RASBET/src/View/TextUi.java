package View;

import Controller.RASBet;
import Exceptions.PasswordIncorreta;
import Exceptions.UtilizadorExistente;
import Exceptions.UtilizadorNExistente;
import Languages.gestorIdiomas;
import Model.Competicao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TextUi {
    private final Scanner scin;
    private final RASBet rasbet;
    private final gestorIdiomas gestIdiomas;
    private final String id_user_atual;

    public TextUi() throws IOException {
        this.gestIdiomas = new gestorIdiomas();
        this.scin = new Scanner(System.in);
        rasbet = new RASBet();
        id_user_atual = null;
    }

    public void run() {
        System.out.println(gestIdiomas.getTexto("welcome"));
        this.menuPrincipal();
        System.out.println(gestIdiomas.getTexto("goodbye"));
    }

    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                "sports",
                "loginmenu",
                "signmenu",
                "tongues"
        }, gestIdiomas);

        menu.setHandler(1, this::menuDesportos);
        menu.setHandler(2, this::tratarLogin);
        menu.setHandler(3, this::tratarRegisto);
        menu.setHandler(4, this::menuLingua);

        menu.run();
    }

    private void menuDesportos(){
        Menu menu = new Menu(new String[]{
                "soccer",
                "tennis"
        },gestIdiomas);

        menu.setHandler(1, this::menuFutebol);
        menu.setHandler(2, this::menuTenis);

        menu.run();
    }


    private void menuFutebol(){
        int i =0;
        int index=100;
        List<Competicao> ras = rasbet.getCompeticoes('f');
        int size= ras.size();
        while(index>size || index <= 0) {
            for (i = 0; i < size; i++)
                System.out.printf("%d - %s%n", i + 1, ras.get(i).getNome());
            System.out.printf("%d - %s%n", i+1, gestIdiomas.getTexto("games"));
            System.out.println("Opcao:");
            index = scin.nextInt();
        }
        if(index==size)menuJogos();
        else  menuJogosFromComp(ras.get(index-1).getId());


    }

    private void menuJogosFromComp(String i){
        System.out.println(gestIdiomas.getTexto("gameslist"));
        System.out.println(rasbet.getJogosWithOddsFromComp(i).toString());

        Menu menu = new Menu(new String[]{
                "bet"
        },gestIdiomas);

        menu.setHandler(1, this::tratarAposta);

        menu.run();
    }



    private void menuJogos(){
        System.out.println(gestIdiomas.getTexto("gameslist"));
        System.out.println(rasbet.getJogosWithOdds().toString());

        Menu menu = new Menu(new String[]{
                "bet"
        },gestIdiomas);

        menu.setHandler(1, this::tratarAposta);

        menu.run();
    }


    private void menuTenis(){
        int i =0;
        int index=100;
        List<Competicao> ras = rasbet.getCompeticoes('t');
        int size= ras.size();
        while(index>size || index <= 0) {
            for (i = 0; i < size; i++)
                System.out.printf("%d - %s%n", i + 1, ras.get(i).getNome());
            System.out.printf("%d - %s%n", i+1, gestIdiomas.getTexto("games"));
            System.out.println(gestIdiomas.getTexto("option"));
            index = scin.nextInt();
        }
        if(index==size)menuJogos();
        else  menuJogosFromComp(ras.get(index-1).getId());
    }



    private void menuLingua(){
        System.out.println(Arrays.toString(gestIdiomas.getIdiomasDisponiveis()));
        System.out.println(gestIdiomas.getTexto("newtongue"));
        gestIdiomas.setIdioma(scin.nextLine());
    }


    private void tratarAposta(){

    }

    private void tratarLogin(){
        int i =1;
        String password;
        String email = "";
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

        } catch(NullPointerException | PasswordIncorreta | UtilizadorNExistente e){
            if (e instanceof PasswordIncorreta)
                System.out.println(gestIdiomas.getTexto("badPass"));

            else System.out.println(e.getMessage());
        }
        System.out.println(gestIdiomas.getTexto("goodlogin"));

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
            System.out.printf((gestIdiomas.getTexto("signSuccess")) + "%n",email);

        } catch(NullPointerException | UtilizadorExistente | ParseException e){
            if (e instanceof UtilizadorExistente)
                System.out.println(gestIdiomas.getTexto("userExists"));

            else System.out.println(e.getMessage());
        }
    }
}
