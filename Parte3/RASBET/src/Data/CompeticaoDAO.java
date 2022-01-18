package Data;

import java.util.ArrayList;

import Model.Competicao;
import Model.Jogo;
import java.sql.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.Collectors;

public class CompeticaoDAO {
    private static CompeticaoDAO singleton = null;


    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static CompeticaoDAO getInstance() {
        if (CompeticaoDAO.singleton == null) {
            CompeticaoDAO.singleton = new CompeticaoDAO();
        }
        return CompeticaoDAO.singleton;
    }

    /**
     * @return número de competicoes na base de dados
     */

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Competicao")) {
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
     * Método que verifica se existem competicoes
     *
     * @return true se existirem 0 competicoes
     */

    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que verifica se um id de uma competicao existe na base de dados
     *
     * @param idC Competicao
     * @return true se o Competicao existe
     * @throws NullPointerException
     */

    public boolean containsKey(Object idC) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM Competicao WHERE id='" + idC.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se uma Competicao existe na base de dados
     *
     * @param value
     * @return true caso a Competicao exista, false caso contrario
     * @throws NullPointerException
     */

    public boolean containsValue(Object value) {
        Competicao a = (Competicao) value;
        Competicao g = this.get(a.getId());
        if (g == null){
            return false;
        } else {
            return a.equals(g);
        }
    }

    /**
     * Obter um Jogo, dado o seu id
     *
     * @param id id do Jogo
     * @return o Jogo caso exista (null noutro caso)
     * @throws NullPointerException
     */

    public Competicao get(Object id) {
        Competicao a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Competicao WHERE id='" + id + "'");
            if (rs.next()) {  // A chave existe na tabela
                a = new Competicao(rs.getString("id"),rs.getString("nome"));
            }
        } catch(SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }

    public ArrayList<Competicao> getAllComp() {
        ArrayList<Competicao> a = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Competicao");
            while (rs.next()) {  // A chave existe na tabela
                a.add(new Competicao(rs.getString("id"),rs.getString("nome")));
            }
        } catch(SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }

    public ArrayList<Competicao> getCompFrom(char id) {
        ArrayList<Competicao> a = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Competicao where id like '"+id+"%'");
            while (rs.next()) {  // A chave existe na tabela
                a.add(new Competicao(rs.getString("id"),rs.getString("nome")));
            }
        } catch(SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }





    /**
     *
     * @return todos os codigos de Competicoes na BD
     */

    public Set<String> keySet() {
        Set<String> comps = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Jogo")){
            while (rs.next()) {
                comps.add(rs.getString("id"));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return comps;
    }

    /**
     * @return Todas os Jogos da base de dados
     */

    public Collection<Competicao> values() {
        Collection<Competicao> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Competicao")) {
            while (rs.next()) {   // Utilizamos o get para construir os jogos
                col.add(this.get(rs.getString("id")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Set<Entry<String, Competicao>> entrySet() {
        Set<Entry<String, Competicao>> setReturn = new HashSet<Map.Entry<String, Competicao>>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Competicao")) {
            while(rs.next()){
                Map.Entry<String, Competicao> entry = new HashMap.SimpleEntry<String, Competicao>(rs.getString("id"), this.get(rs.getString("id")));
                setReturn.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return setReturn;
    }


}
