package View;

import Languages.gestorIdiomas;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";

        public interface MenuHandler {
            void execute();
        }

        public interface MenuPreCondition {
            boolean validate();
        }

        // Varíavel de classe para suportar leitura
        private static Scanner is = new Scanner(System.in);

        // variáveis de instância
        private List<String> opcoes;
        private List<MenuPreCondition> disponivel;  // Lista de pré-condições
        private List<MenuHandler> handlers;         // Lista de handlers
        private gestorIdiomas gestIdiomas;

        private int op;

        /**
         * Constructor for objects of class Menu
         */
        public Menu(String[] opcoes,gestorIdiomas gest) {
            this.gestIdiomas = gest;
            this.opcoes = Arrays.asList(opcoes);
            this.disponivel = new ArrayList<>();
            this.handlers = new ArrayList<>();
            this.opcoes.forEach(s-> {
                this.disponivel.add(()->true);
                this.handlers.add(()->System.out.println("\nATENÇÃO: Opção não implementada!"));
            });

            this.op = 0;
        }

        public Menu(){
            this.opcoes = new ArrayList<>();
            this.disponivel = new ArrayList<>();
            this.handlers = new ArrayList<>();
            this.op = 0;
        }


        /**
         * Método para apresentar o menu e ler uma opção.
         */
        public void run() {
            do {
                showMenu();
                this.op = lerOpcao();
                if (op>0 && !this.disponivel.get(op-1).validate()) {
                    System.out.println(gestIdiomas.getTexto("optionNotFound"));
                } else if (op>0) {
                    // executar handler
                    this.handlers.get(op-1).execute();
                }
            } while (this.op != 0);
        }


        /** Apresentar o menu */
        private void showMenu() {
            System.out.println("\n########### Menu ###########");

            for (int i = 0; i<this.opcoes.size(); i++) {
                System.out.print(i+1);
                System.out.print(" - ");
                System.out.println(this.disponivel.get(i).validate() ? gestIdiomas.getTexto(this.opcoes.get(i)) : "----------");
            }
            System.out.println(gestIdiomas.getTexto("leave"));
        }

        /** Ler uma opção válida */
        private int lerOpcao() {
            int op;
            System.out.print(gestIdiomas.getTexto("option"));
            try {
                String linha = is.nextLine();
                op = Integer.parseInt(linha);

            } catch (NumberFormatException e) {
                op = -1;
            }

            if (op < 0 || op > this.opcoes.size()) {
                System.out.println(gestIdiomas.getTexto("invalidOption"));
                op = -1;
            }
            return op;
        }

        /**
         * Método para obter a última opção lida
         */
        public int getOpcao() {
            return this.op;
        }

        /**
         * Método que regista uma uma pré-condição numa opção do menu.
         *
         * @param i índice da opção (começa em 1)
         * @param b pré-condição a registar
         */
        public void setPreCondition(int i, MenuPreCondition b) {
            this.disponivel.set(i-1,b);
        }

        /**
         * Método para registar um handler numa opção do menu.
         *
         * @param i indice da opção  (começa em 1)
         * @param h handlers a registar
         */
        public void setHandler(int i, MenuHandler h) {
            this.handlers.set(i-1, h);
        }
    }
