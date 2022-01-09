import Model.*;
import Model.UtilizadorAutenticado;
import Data.*;
import java.util.Map;

public class RASBet {

    private Map<String, UtilizadorAutenticado> utilizadores;
    private Map<Integer, Jogo> jogos;
    private Map<Integer,Desporto> desportos;
    private Map<Integer,Competicao> competicoes;
    private Map<Integer,Aposta> apostas;

    public RASBet(){
    //    this.utilizadores = new UtilizadorDAO.getInstance();
        this.jogos = new JogoDAO.getInstance();
        this.desportos = new DesportoDAO.getInstance();
        this.competicoes = new CompeticaoDAO.getInstance();
        //this.apostas =
    }
    



    public UtilizadorAutenticado registarCliente()  { //throws EmailInvalidoException
        System.out.println("Nome de Utilizador:");
        String nome = scannerC.nextLine();
        System.out.println("Email:");
        String email = scannerC.nextLine();
        if(email.contains("@")){
            System.out.println("Password:");
            String pass = scannerC.nextLine();
            System.out.println("Data Nascimento (dd/MM/yyyy):");
            // verificar se o user dá a data no formato correto
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String dataRecebida = scannerC.nextLine();
            Date dataN = formato.parse(dataRecebida); 
            Carteira carteira = new Carteira();
            List<Aposta> historico= new ArrayList<>(); 
            
            UtilizadorAutenticado u = new UtilizadorAutenticado( false, nome, email, pass,dataN, carteira, historico);
            utilizadores.put(email,u);
        }
       // else throw new EmailInvalidoException();
    }

    public List<String> getUtilizadores() {
		Collection<UtilizadorAutenticado> u = this.utilizadores.values();
		return u;
    }



public void run() {
        
        this.menuPrincipal();
        System.out.println("Até breve!...");
    }

private void menuPrincipal(){
   registarCliente();
   getUtilizadores();
}



}