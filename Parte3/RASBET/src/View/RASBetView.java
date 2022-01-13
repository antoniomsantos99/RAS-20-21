package View;

import Model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import Exceptions.*;

public class RASBetView {

    /**
     * Exemplo de interface em modo texto.
     *
     */
        private RASBet model;

        private Scanner scin;

        public RASBetView() {

            this.model = new RASBet();
            scin = new Scanner(System.in);
        }

        /**
         * Executa o menu principal e invoca o método correspondente à opção seleccionada.
         */
        public void run() {

            this.menuPrincipal();
            System.out.println("Até breve!...");
        }


        private void menuPrincipal(){
            Menu menu = new Menu(new String[]{
                    "Menu de Login.",
                    "Menu de Registo."
            });


            menu.setHandler(1, ()->menuLogin());
            menu.setHandler(2, ()->menuRegisto());


            menu.executa(1);
        }

        private void menuLogin(){
            Menu menu = new Menu(new String[]{
                    "Utilizador Autenticado",
                    "Utilizador Nao Autenticado."
            });


            menu.setHandler(1, ()->tratarLogin());
            menu.setHandler(2, ()->tratarRegisto());

            menu.executa(2);
        }

        private void menuRegisto(){
            Menu menu = new Menu(new String[]{
                    "Utilizador Autenticado",
                    "Utilizador Nao Autenticado."
            });

            menu.setHandler(1, ()->tratarLogin());
            menu.setHandler(2, ()->tratarRegisto());

            menu.executa(3);
        }


    private void tratarLogin(){
        try{
            int i =1;
            String password;
            String email = new String();
            System.out.println("Email:   ");
            email = scin.nextLine();

            while(i==1) {
                System.out.println("Email:   ");
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
                String email = new String();
                String dataN;
                System.out.println("Username:   ");
                username = scin.nextLine();

                while(i==1) {
                    System.out.println("Email:   ");
                    email = scin.nextLine();
                    if(email.contains("@")) i =0;
                }

                if (this.model.existeUtilizador(email)){
                    throw new UtilizadorExistente();
                }

                System.out.println("Password:   ");
                password = scin.nextLine();

                System.out.println("Data Nascimento:   ");
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
