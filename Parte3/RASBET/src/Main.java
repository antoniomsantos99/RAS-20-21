import View.RASBetView;

public class Main {
    public static void main(String[] args) {
        try {
            new RASBetView().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }
    }

}