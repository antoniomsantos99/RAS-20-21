package Data;

import Model.Competicao;

import Model.Desporto;
import java.sql.*;
import java.util.Map;

public class CompeticaoDAO implements Map<String, Competicao> {
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
     * @return número de desportos na base de dados
     */
    @Override
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
     * Método que verifica se existem competições
     *
     * @return true se existirem 0 competições
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que verifica se um id de uma competição existe na base de dados
     *
     * @param idC competicao
     * @return true se o competicao existe
     * @throws NullPointerException
     */
    @Override
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
     * Verifica se um Competicao existe na base de dados
     *
     * @param value
     * @return true caso o Competicao exista, false caso contrario
     * @throws NullPointerException
     */
    @Override
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
     * Obter um Competicao, dado o seu id
     *
     * @param id id do Competicao
     * @return o Competicao caso exista (null noutro caso)
     * @throws NullPointerException
     */
    @Override
    public Competicao get(Object id) {
        Competicao a = null;
        Desporto d = new Desporto();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Competicao WHERE id='" + id + "'")) {
            if (rs.next()) {  // A chave existe na tabela
                d.(rs.getInt("desporto"));
                a = new Competicao(rs.getString("nome"), rs.getInt("id"), d));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }



    @Override
    public Competicao put(String idD, Competicao d) {
        Competicao res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a Desporto
            stm.executeUpdate(
                    "INSERT INTO Competicao VALUES ('"+d.getId()+"', '"+d.getNome()+"', '"+d.getDesporto()+"' ) ");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    /**
     * Remover um Competicao, dado o seu id
     *
     * @param code id do Desporto a remover
     * @return Competicao removida
     * @throws NullPointerException
     */
    @Override
    public Competicao remove(Object code) {
        Competicao t = this.get(code);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Competicao WHERE id='"+code+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Adicionar um conjunto de Competicoes à base de dados
     *
     * @param competicoes a adicionar
     * @throws NullPointerException
     */
    @Override
    public void putAll(Map<? extends String,? extends Competicao> competicoes) {
        for(Competicao p : competicoes.values()) {
            this.put(Integer.toString(p.getId()), p);
        }
    }

    /**
     * Apagar todas os Competicoes
     *
     * @throws NullPointerException
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Competicao");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     *
     * @return todos os codigos das Competicoes na BD
     */
    @Override
    public Set<String> keySet() {
        Set<String> competicoes = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Competicao")){
            while (rs.next()) {
                competicoes.add(rs.getString("id"));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return competicoes;
    }

    /**
     * @return Todas as Competicoes da base de dados
     */
    @Override
    public Collection<Competicao> values() {
        Collection<Competicao> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Competicao")) {
            while (rs.next()) {   // Utilizamos o get para construir as competicoes
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
