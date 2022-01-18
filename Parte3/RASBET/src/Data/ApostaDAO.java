package Data;

import Model.Aposta;
import Model.ApostaMultipla;
import Model.ApostaSimples;
import Model.UtilizadorAutenticado;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class ApostaDAO {
    private static ApostaDAO singleton = null;

    public static ApostaDAO getInstance() {
        if (ApostaDAO.singleton == null) {
            ApostaDAO.singleton = new ApostaDAO();
        }
        return ApostaDAO.singleton;
    }

    /**
     * @return número de desportos na base de dados
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
     * Método que verifica se existem desportos
     *
     * @return true se existirem 0 desportos
     */

    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que verifica se um id de um desporto existe na base de dados
     *
     * @param idD desporto
     * @return true se o desporto existe
     * @throws NullPointerException
     */

    public boolean containsKey(Object idD) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM Aposta WHERE id='" + idD.toString() + "'")) {
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
        ArrayList<Integer> i = new ArrayList<>();
        ArrayList<Integer> j = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
             Statement stm = conn.createStatement();
             ResultSet rsjogos = stm.executeQuery("SELECT * FROM ApostaJogos WHERE id='" + id + "' ");
            while (rsjogos.next()) {
                i.add(rsjogos.getInt("idJogo"));
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
}
