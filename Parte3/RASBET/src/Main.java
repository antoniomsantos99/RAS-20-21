import Controller.RASBet;

public class Main {
    public static void main(String[] args) {
        try {
            new RASBet().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }
    }

}