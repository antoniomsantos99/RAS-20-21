package Data;

import java.util.ArrayList;

import Model.Jogo;
import java.sql.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.Collectors;

public class JogoDAO {
    private static JogoDAO singleton = null;


    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static JogoDAO getInstance() {
        if (JogoDAO.singleton == null) {
            JogoDAO.singleton = new JogoDAO();
        }
        return JogoDAO.singleton;
    }

    /**
     * @return número de desportos na base de dados
     */

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Jogo")) {
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
     * Método que verifica se existem Jogos
     *
     * @return true se existirem 0 Jogos
     */

    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que verifica se um id de um Jogo existe na base de dados
     *
     * @param idJ Jogo
     * @return true se o Jogo existe
     * @throws NullPointerException
     */

    public boolean containsKey(Object idJ) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM Jogo WHERE id='" + idJ.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se um Jogo existe na base de dados
     *
     * @param value
     * @return true caso o Jogo exista, false caso contrario
     * @throws NullPointerException
     */

    public boolean containsValue(Object value) {
        Jogo a = (Jogo) value;
        Jogo g = this.get(a.getId());
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

    public Jogo get(Object id) {
        Jogo a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Jogo WHERE id='" + id + "'");
             if (rs.next()) {  // A chave existe na tabela
                a = new Jogo(rs.getString("id"),rs.getString("Competicao"),rs.getString("participante1"),rs.getString("participante2"), rs.getFloat("Odd1") ,rs.getFloat("Odd2")  ,rs.getFloat("Odd3")  ,rs.getString("resultado"),rs.getTimestamp("data") ,rs.getString("localizacao"),rs.getString("estado"));
            }
         } catch(SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }

    public ArrayList<Jogo> getJogosWithOdds() {
        ArrayList<Jogo> a = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Jogo WHERE odd1 != 0");
            while (rs.next()) {  // A chave existe na tabela
                a.add(new Jogo(rs.getString("id"),rs.getString("Competicao"),rs.getString("participante1"),rs.getString("participante2"), rs.getFloat("Odd1") ,rs.getFloat("Odd2")  ,rs.getFloat("Odd3")  ,rs.getString("resultado"),rs.getTimestamp("data") ,rs.getString("localizacao"),rs.getString("estado")));
            }
        } catch(SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }

    public ArrayList<Jogo> getAllJogos() {
        ArrayList<Jogo> a = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Jogo");
            if (rs.next()) {  // A chave existe na tabela
                a.add(new Jogo(rs.getString("id"),rs.getString("Competicao"),rs.getString("participante1"),rs.getString("participante2"), rs.getFloat("Odd1") ,rs.getFloat("Odd2")  ,rs.getFloat("Odd3")  ,rs.getString("resultado"),rs.getTimestamp("data") ,rs.getString("localizacao"),rs.getString("estado")));
            }
        } catch(SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    public ArrayList<Jogo> getJogos(ArrayList<String> ids){
        return ids.stream().map(e->get(e)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Jogo> getJogos(){
        Set<String> set = keySet();
        ArrayList<String> lista_ids = new ArrayList<>(set);
        return getJogos(lista_ids);
    }


    /**
     *
     * @return todos os codigos dos Jogo na BD
     */

    public Set<String> keySet() {
        Set<String> jogos = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Jogo")){
            while (rs.next()) {
                jogos.add(rs.getString("id"));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return jogos;
    }

    /**
     * @return Todas os Jogos da base de dados
     */

    public Collection<Jogo> values() {
        Collection<Jogo> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Jogo")) {
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

    public Set<Entry<String, Jogo>> entrySet() {
        Set<Entry<String, Jogo>> setReturn = new HashSet<Map.Entry<String, Jogo>>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Jogo")) {
            while(rs.next()){
                Map.Entry<String, Jogo> entry = new HashMap.SimpleEntry<String, Jogo>(rs.getString("id"), this.get(rs.getString("id")));
                setReturn.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return setReturn;
    }


}
