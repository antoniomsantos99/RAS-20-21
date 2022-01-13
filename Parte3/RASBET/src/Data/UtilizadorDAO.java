package Data;

import Model.Aposta;
import Model.Carteira;
import Model.Utilizador;
import Model.UtilizadorAutenticado;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;


public class UtilizadorDAO implements Map<String,UtilizadorAutenticado>{
    private static UtilizadorDAO singleton = null;

    
    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static UtilizadorDAO getInstance() {
        if (UtilizadorDAO.singleton == null) {
            UtilizadorDAO.singleton = new UtilizadorDAO();
        }
        return UtilizadorDAO.singleton;
    }

    /**
     * @return número de utilizadores na base de dados
     */

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Utilizador")) {
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
     * Método que verifica se existe utilizadores
     *
     * @return true se existirem 0 utiliazdores
     */

    public boolean isEmpty() {
        return this.size() == 0;
    }

    
    /**
     * Método que verifica se um id de um desporto existe na base de dados
     *
     * @param email utilizador
     * @return true se o utilizador existe
     * @throws NullPointerException
     */

    public boolean containsKey(Object email) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM Utilizador WHERE email='" + email.toString() + "'")) {
             r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se um Utilizador existe na base de dados
     *
     * @param value
     * @return true caso o Utilizador exista, false caso contrario
     * @throws NullPointerException
     **/

    public boolean containsValue(Object value) {
        UtilizadorAutenticado a = (UtilizadorAutenticado) value;
        UtilizadorAutenticado g = this.get(a.getEmail());
        if (g == null){
            return false;
        } else {
            return a.equals(g);
        }
    }

    /**
     * Obter um Utilizador, dado o seu id
     *
     * @param email id do Utilizador
     * @return o Utilizador caso exista (null noutro caso)
     * @throws NullPointerException
     **/

    public UtilizadorAutenticado get(Object email) {
        UtilizadorAutenticado a = null;
        Carteira c = null;
        ArrayList<Aposta> apostas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Utilizador WHERE email='" + email + "'");
             ResultSet rsA = stm.executeQuery("SELECT id FROM Aposta WHERE idUtilizador='" + email + "'");
             ResultSet rsC = stm.executeQuery("SELECT * FROM Carteira WHERE user='" + email + "'");
             while(rsA.next()){
                 Aposta ap = ApostaDAO.getInstance().get(rsA.getInt(1));
                 apostas.add(ap);
             }
             if(rsC.next()) c = new Carteira(rsC.getFloat("eur"),rsC.getFloat("usd"),rsC.getFloat("gbp"),rsC.getFloat("ada"));
             if (rs.next()) {  // A chave existe na tabela
                 a = new UtilizadorAutenticado(true, rs.getString("username"), rs.getString("email"),  rs.getString("password"),c,apostas);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    public UtilizadorAutenticado put(String email, UtilizadorAutenticado u) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "INSERT INTO Utilizador VALUES ('"+u.getUsername()+"', '"+u.getEmail()+ "','" +u.getPassword()+"','" +u.getCarteira()+"' ) " +
                            "ON DUPLICATE KEY UPDATE password=VALUES(password)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return u;
    }

    public UtilizadorAutenticado remove(Object code) {
        UtilizadorAutenticado t = this.get(code);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Utilizador WHERE email='"+code+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    public void putAll(Map<? extends String,? extends UtilizadorAutenticado> utilizadores) {
        for(UtilizadorAutenticado u : utilizadores.values()) {
            this.put(u.getEmail(),u);
        }
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Utilizador");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     *
     * @return todos os codigos dos Desportos na BD
     *
     **/

    public Set<String> keySet() {
        Set<String> utilizadores = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT email FROM Utilizador")){
            while (rs.next()) {
                utilizadores.add(rs.getString("email"));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return utilizadores;
    }

    /**
     * @return Todas os Desportos da base de dados
     *
     *
     **/

    public Collection<UtilizadorAutenticado> values() {
        Collection<UtilizadorAutenticado> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT email FROM Utilizador")) {
            while (rs.next()) {   // Utilizamos o get para construir os desportos
                col.add(this.get(rs.getString("email")));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }
    public Set<Entry<String, UtilizadorAutenticado>> entrySet() {
        Set<Entry<String, UtilizadorAutenticado>> setReturn = new HashSet<Map.Entry<String, UtilizadorAutenticado>>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codQR FROM paletes")) {
            while(rs.next()){
                Map.Entry<String, UtilizadorAutenticado> entry = new HashMap.SimpleEntry<String, UtilizadorAutenticado>(rs.getString("email"), this.get(rs.getString("email")));
                setReturn.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return setReturn;
    }
}


