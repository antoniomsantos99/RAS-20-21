package Data;

import Model.Competicao;
import Model.Desporto;

import java.sql.*;
import java.util.*;

public class DesportoDAO implements Map<String, Desporto> {
        private static DesportoDAO singleton = null;


    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static DesportoDAO getInstance() {
        if (DesportoDAO.singleton == null) {
            DesportoDAO.singleton = new DesportoDAO();
        }
        return DesportoDAO.singleton;
    }

    /**
     * @return número de desportos na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Desporto")) {
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
    @Override
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
    @Override
    public boolean containsKey(Object idD) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT id FROM Desporto WHERE id='" + idD.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se um Desporto existe na base de dados
     *
     * @param value
     * @return true caso o Desporto exista, false caso contrario
     * @throws NullPointerException
     */
    @Override
    public boolean containsValue(Object value) {
        Desporto a = (Desporto) value;
        Desporto g = this.get(a.getId());
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
    @Override
    public Desporto get(Object id) {
        Desporto a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Desporto WHERE id='" + id + "'")) {
            if (rs.next()) {  // A chave existe na tabela
                a = new Desporto(rs.getString("nome"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }



    @Override
    public Desporto put(String idD, Desporto d) {
        Desporto res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a Desporto
            stm.executeUpdate(
                    "INSERT INTO Desporto VALUES ('" + d.getId() + "', '" + d.getNome() + "' ) ");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    /**
     * Remover um Desporto, dado o seu id
     *
     * @param code id do Desporto a remover
     * @return Desporto removido
     * @throws NullPointerException
     */
    @Override
    public Desporto remove(Object code) {
        Desporto t = this.get(code);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Desporto WHERE id='" + code + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Adicionar um conjunto de Desportos à base de dados
     *
     * @param desportos a adicionar
     * @throws NullPointerException
     */
    @Override
    public void putAll(Map<? extends String,? extends Desporto> desportos) {
        for(Desporto p : desportos.values()) {
            this.put(Integer.toString(p.getId()), p);
        }
    }

    /**
     * Apagar todas os Desporto
     *
     * @throws NullPointerException
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Desporto");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     *
     * @return todos os codigos dos Desportos na BD
     */
    @Override
    public Set<String> keySet() {
        Set<String> desportos = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM desporto")){
            while (rs.next()) {
                desportos.add(rs.getString("id"));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return desportos;
    }

    /**
     * @return Todas os Desportos da base de dados
     */
    @Override
    public Collection<Desporto> values() {
        Collection<Desporto> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Desporto")) {
            while (rs.next()) {   // Utilizamos o get para construir os desportos
                col.add(this.get(rs.getString("id")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    public Set<Entry<String, Desporto>> entrySet() {
        Set<Entry<String, Desporto>> setReturn = new HashSet<Map.Entry<String, Desporto>>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Desporto")) {
            while(rs.next()){
                Map.Entry<String, Desporto> entry = new HashMap.SimpleEntry<String, Desporto>(rs.getString("id"), this.get(rs.getString("id")));
                setReturn.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return setReturn;
    }


}
