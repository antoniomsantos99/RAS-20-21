package Data;

import Model.Utilizador;
import Model.UtilizadorAutenticado;

import java.sql.*;
import java.util.Map;


public class UtilizadorDAO implements Map<String, Utilizador> {
    private static UtilizadorDAO singleton = null;

    private UtilizadorDAO(){

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS aposta (" +
                    "codAposta varchar(7) NOT NULL PRIMARY KEY," +
                    "estado varchar(20) NOT NULL," +
                    "resultado Boolean NOT NULL," +
                    "valorApostado DECIMAL(4,2) NOT NULL," +
                    "ganhosPossiveis DECIMAL(4,2) NOT N6ULL," +
                    "participante varchar(45) NOT NULL)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS sessao (" +
                    "id_Utilizador VARCHAR(7) NOT NULL PRIMARY KEY," +
                    "idioma VARCHAR(20) NOT NULL,"+
                    "moeda VARCHAR(10) NOT NULL";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS utilizador (" +
                    "email VARCHAR(40) NOT NULL PRIMARY KEY," +
                    "nome VARCHAR(20) NOT NULL," +
                    "password VARCHAR(20) NOT NULL," +
                    "saldo DECIMAL(4,2) DEFAULT NULL," +
                    "data_nascimento DATE NOT NULL," +
                    "loggedIn Boolean NOT NULL";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

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
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM utilizador")) {
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
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }



}


