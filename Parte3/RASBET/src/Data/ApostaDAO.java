package Data;

import Model.Aposta;
import Model.ApostaMultipla;
import Model.ApostaSimples;
import Model.UtilizadorAutenticado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApostaDAO {
    private static ApostaDAO singleton = null;


    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static ApostaDAO getInstance() {
        if (ApostaDAO.singleton == null) {
            ApostaDAO.singleton = new ApostaDAO();
        }
        return ApostaDAO.singleton;
    }

    /**
     * @return número de apostas na base de dados
     */

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Aposta")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    /**
     *
     * Método que verifica se existem apostas
     * @return true se existirem 0 apostas
     */

    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que verifica se um id de uma aposta existe na base de dados
     *
     * @param idA aposta
     * @return true se o aposta existe
     * @throws NullPointerException
     */

    public boolean containsKey(Object idA) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM Aposta WHERE id='" + idA.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se um Aposta existe na base de dados
     *
     * @param value
     * @return true caso o Aposta exista, false caso contrario
     * @throws NullPointerException
     */

    public boolean containsValue(Object value) {
        Aposta a = (Aposta) value;
        Aposta g = this.get(a.getId());
        if (g == null){
            return false;
        } else {
            return a.equals(g);
        }
    }

    /**
     * Obter um Desporto, dado o seu id
     *
     * @param id id do Desporto
     * @return o Desporto caso exista (null noutro caso)
     * @throws NullPointerException
     */

    public Aposta get(Object id) {
        Aposta a = null;
        ArrayList<String> i = new ArrayList<>();
        ArrayList<Integer> j = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
             Statement stm = conn.createStatement();
             ResultSet rsjogos = stm.executeQuery("SELECT * FROM ApostaJogo WHERE idAposta='" + id + "' ");
            while (rsjogos.next()) {
                i.add(rsjogos.getString("idJogo"));
                j.add(rsjogos.getInt("opcao"));
            }
             ResultSet rs = stm.executeQuery("SELECT * FROM Aposta WHERE id='" + id + "' ");
            if (rs.next()) {  // A chave existe na tabela
                if (i.size() == 1) a = new ApostaSimples(rs.getInt("id"),rs.getString("estado"),rs.getFloat("valorApostado"),JogoDAO.getInstance().get(i.get(0)),j.get(0));
            else a = new ApostaMultipla(rs.getInt("id"),rs.getString("estado"),rs.getFloat("valorApostado"),JogoDAO.getInstance().getJogos(i),j);
            } // este erro é por causa de se terem mudado coisas na aposta, CHECK THIS
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    public void put(String email, ApostaSimples a) {
        int id=0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            stm.executeUpdate(
                    "INSERT INTO Aposta (idUtilizador,valorApostado,estado) VALUES ('" + email + "','" + a.getValorApostado()  +  "','" +a.getEstado() +
                    "')");
            ResultSet rs =stm.executeQuery(
                    "SELECT id FROM Aposta ORDER BY ID DESC LIMIT 1"
            );
             if(rs.next()) id = rs.getInt("id");
            stm.executeUpdate(
                    "INSERT INTO ApostaJogo VALUES ('" + id + "','" + a.getJogo().getId()  +  "','" +a.getOpcao() +
                            "')");

        }catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public void put(String email, ApostaMultipla a) {
        int id=0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            stm.executeUpdate(
                    "INSERT INTO Aposta (idUtilizador,valorApostado,estado) VALUES ('" + email + "','" + a.getValorApostado()  +  "','" +a.getEstado() +
                            "')");
            ResultSet rs =stm.executeQuery(
                    "SELECT id FROM Aposta ORDER BY ID DESC LIMIT 1"
            );
            if(rs.next()) id = rs.getInt("id");
            for(int i = 0;i<a.getOpcoes().size();i++)
            stm.executeUpdate(
                    "INSERT INTO ApostaJogo VALUES ('" + id + "','" + a.getJogos().get(i).getId()  +  "','" +a.getOpcoes().get(i) +
                            "')");

        }catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

  /*  public void addAposta(int id, String email, double valor, String estado, List<String> jogos, List<Integer> opcoes){
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            stm.executeUpdate(String.format("INSERT INTO Aposta VALUES (%d,'%s',%d,'%s')",id,email,valor,estado));
            int i = 0;
            for(String j : jogos){
                stm.executeUpdate(String.format("INSERT INTO ApostaJogo VALUES (%d,'%s',%d)",id,j,opcoes.get(i)));
                i++;
            }
        }catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
*/
}
