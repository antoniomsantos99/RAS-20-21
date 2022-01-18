package View;

import Controller.RASBet;
import Exceptions.PasswordIncorreta;
import Exceptions.UtilizadorExistente;
import Exceptions.UtilizadorNExistente;
import Languages.gestorIdiomas;
import Model.Competicao;
import com.sun.source.tree.WhileLoopTree;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TextUi {
    private final Scanner scin;
    private final RASBet rasbet;
    private final gestorIdiomas gestIdiomas;
    private String id_user_atual;

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

    private void menuPrincipalAuth() {
        Menu menu = new Menu(new String[]{
                "sports",
                "wallet",
                "loginmenu",
                "signmenu",
                "tongues"
        }, gestIdiomas);

        menu.setHandler(1, this::menuDesportos);
        menu.setHandler(2, this::menuCarteira);
        menu.setHandler(3, this::tratarLogin);
        menu.setHandler(4, this::tratarRegisto);
        menu.setHandler(5, this::menuLingua);

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

    private void menuCarteira(){
        Menu menu = new Menu(new String[]{
                "deposit",
                "retrieve",
                "exchange"
        },gestIdiomas);

        menu.setHandler(1, this::depositMoney);
        menu.setHandler(2, this::retrieveMoney);
        menu.setHandler(3, this::exchangeMoney);

        menu.run();
    }

    private void depositMoney(){
        int i =0;
        int index=100;
        double value;
        List<String> ras = rasbet.getMoedas();
        int size= ras.size();
        while(index>size || index <= 0) {
            for (i = 0; i < size; i++)
                System.out.printf("%d - %s%n", i + 1, ras.get(i));
            System.out.println("Opcao:");
            index = scin.nextInt();
        }
        System.out.println(gestIdiomas.getTexto("depositValue"));
        value = scin.nextDouble();
        rasbet.putTransaccao(id_user_atual,ras.get(index-1),Math.abs(value));


    }

    private void retrieveMoney(){
        int i =0;
        int index=100;
        double value;
        boolean success = false;

        List<String> ras = rasbet.getMoedas();
        int size= ras.size();
        while(index>size || index <= 0) {
            for (i = 0; i < size; i++)
                System.out.printf("%d - %s%n", i + 1, ras.get(i));
            System.out.println("Opcao:");
            index = scin.nextInt();
        }
        while(!success) {
            System.out.println(gestIdiomas.getTexto("retrieveValue"));
            value = scin.nextDouble();
            success=rasbet.putTransaccao(id_user_atual, ras.get(index - 1), -Math.abs(value));
            if(!success) System.out.println(gestIdiomas.getTexto("noMoney"));
        }


    }

    private void exchangeMoney(){
        int i =0;
        int index=100,index2=100;
        double value;
        boolean success = false;

        List<String> ras = rasbet.getMoedas(), ras2 = new ArrayList<>();
        ras2.addAll(ras);
        int size= ras.size();
        while(index>size || index <= 0) {
            for (i = 0; i < size; i++)
                System.out.printf("%d - %s%n", i + 1, ras.get(i));
            System.out.println("Opcao:");
            index = scin.nextInt();
        }
        ras2.remove(index-1);

        while(index2>size-1 || index2 <= 0) {
            for (i = 0; i < size-1; i++)
                System.out.printf("%d - %s%n", i + 1, ras2.get(i));
            System.out.println("Opcao:");
            index2 = scin.nextInt();
        }

        while(!success) {
            System.out.println(gestIdiomas.getTexto("retrieveValue"));
            value = scin.nextDouble();
            success=rasbet.putTransaccao(id_user_atual, ras.get(index - 1), -Math.abs(value));
            if(!success) System.out.println(gestIdiomas.getTexto("noMoney"));
            else rasbet.putTransaccao(id_user_atual, ras2.get(index2 - 1), 0.97*Math.abs(value)* rasbet.getExchangeRate(ras.get(index - 1),ras2.get(index2 - 1)));
        }


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

        System.out.println(gestIdiomas.getTexto("wannaBet"));

        String resp = scin.next();

        if(Objects.equals(resp, "Y")) tratarAposta(i);


    }



    private void menuJogos(){
        System.out.println(gestIdiomas.getTexto("gameslist"));
        System.out.println(rasbet.getJogosWithOdds().toString());

        Menu menu = new Menu(new String[]{
                "bet"
        },gestIdiomas);

        //menu.setHandler(1, this::tratarAposta());

       // menu.run();
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

    private void tratarAposta(String id_jogo){
        float odd=0;
        if(id_user_atual == null){
           Menu menu = new Menu(new String[]{
                           "loginmenu",
                           "signmenu"
                   },gestIdiomas);

        menu.setHandler(1, this::tratarLogin);
        menu.setHandler(2, this::tratarRegisto);

        menu.run();
        }

        else{
            Map<String,Float> ids = new HashMap<>();
            int done=0;
            while(done==0) {
                System.out.println(gestIdiomas.getTexto("choose"));
                String resp = scin.next();
                System.out.println(gestIdiomas.getTexto("choice"));
                String respo = scin.next();

                if (Objects.equals(resp, "-1")) done = 1;
                else {
                    if(respo.equals("Home")) odd = rasbet.getOddJogo(resp,1);
                    if(respo.equals("Tie")) odd= rasbet.getOddJogo(resp,2);
                    if(respo.equals("Away")) odd = rasbet.getOddJogo(resp,3);
                    ids.put(resp,odd);
                }
            }

            if(ids.size()==1){

                System.out.println(gestIdiomas.getTexto("howMuch"));
                float valor = scin.nextFloat();
                odd = ids.get(0);
                System.out.printf("odd- %f%n",odd);
                System.out.println(gestIdiomas.getTexto("currency"));

                //rasbet.fazerApostaSimples(ids.get(0),odd,valor);
            }
            else{
                System.out.println(gestIdiomas.getTexto("howMuch"));
                float valor = scin.nextFloat();
                System.out.println(gestIdiomas.getTexto("currency"));
                for(String jogo : ids.keySet()){
                    //
                }

            }






        }


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
            this.id_user_atual=email;

        } catch(NullPointerException | PasswordIncorreta | UtilizadorNExistente e){
            if (e instanceof PasswordIncorreta)
                System.out.println(gestIdiomas.getTexto("badPass"));

            else System.out.println(e.getMessage());
        }
        System.out.println(gestIdiomas.getTexto("goodlogin"));
        id_user_atual=email;
        menuPrincipalAuth();

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
