import Controller.RASBet;
import View.TextUi;

public class Main {
    public static void main(String[] args) {
        try {
            new TextUi().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }
    }

}