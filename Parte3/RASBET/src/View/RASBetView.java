public class RASBetView {
    private MenuIndice menu;
    private final Stack<MenuIndice> prev;
    private final ArrayList<MenuIndice> options;
    private boolean run;

    public enum MenuIndice {  //Temos de ir acrescentando variaveis consoante o que queremos!!!!
        MenuInicial,
        Login,
        Registar
    }

    public Menu() {
        this.menu = MenuIndice.MenuInicial;
        this.prev = new Stack<>();
        this.options = new ArrayList<>();
        this.run = true;
        this.pickChildMenus();
    }

    public MenuIndice getMenu() {
        return this.menu;
    }

    public NewLogin newLogin() { 
        System.out.println("Utilizador:");
        String user = scanner.nextLine();
        System.out.println("Password:");
        String password = scanner.nextLine();
        return new NewLogin(user, password);
    }
    
    public User registarCliente() throws EmailInvalidoException {

        Scanner scannerC = new Scanner(System.in);
        System.out.println("Nome de Utilizador:");
        String nome = scannerC.nextLine();
        System.out.println("Email:");
        String email = scannerC.nextLine();
        if(email.contains("@")){
            System.out.println("Password:");
            String pass = scannerC.nextLine();
            
            
        return new User( "u", nome, email, pass,latitude, longitude);
        }
        else throw new EmailInvalidoException();
    }