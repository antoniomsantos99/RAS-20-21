package Data;

import java.util.ArrayList;
import java.util.Date;

public class JogoDAO {


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
    @Override
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
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que verifica se um id de um Jogo existe na base de dados
     *
     * @param idD Jogo
     * @return true se o Jogo existe
     * @throws NullPointerException
     */
    @Override
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
    @Override
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
    @Override
    public Jogo get(Object id) {
        Jogo a = null;
        Float odd1, odd2, odd3;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Jogo WHERE id='" + id + "'")) {
            if (rs.next()) {  // A chave existe na tabela
                a = new Jogo(rs.getInt("id"), rs.getInt("id"), rs.getInt("Participante1"),rs.getInt("Participante2"), rs.getFloat("Odd1") ,rs.getFloat("Odd2")  ,rs.getFloat("Odd3")  ,rs.getString("resultado"),rs.getTimestamp("data") ,rs.getString("localizacao"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }



    @Override
    public Jogo put(String idD, Jogo j) {
        Jogo res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a Jogo
            stm.executeUpdate(
                    "INSERT INTO Jogo VALUES ('" + j.getId() + "', '" + j.getCompeticao() + "," + j.getParticipantes() + "," + 
                                                j.getOdds() + "," + j.getResultado() + "," + j.getData() + "' ) ");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    /**
     * Remover um Jogo, dado o seu id
     *
     * @param code id do Jogo a remover
     * @return Jogo removido
     * @throws NullPointerException
     */
    @Override
    public Desporto remove(Object code) {
        Desporto t = this.get(code);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Jogo WHERE id='" + code + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Adicionar um conjunto de Jogo à base de dados
     *
     * @param jogos a adicionar
     * @throws NullPointerException
     */
    @Override
    public void putAll(Map<? extends String,? extends Jogo> jogos) {
        for(Jogo p : jogos.values()) {
            this.put(Integer.toString(p.getId()), p);
        }
    }

    /**
     * Apagar todas os Jogos
     *
     * @throws NullPointerException
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Jogo");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     *
     * @return todos os codigos dos Jogo na BD
     */
    @Override
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
    @Override
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

    @Override
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