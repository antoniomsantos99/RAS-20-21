package Data;

import Model.Competicao;

import java.sql.*;
import java.util.Map;

public class CompeticaoDAO implements Map<String, Competicao> {
        private static CompeticaoDAO singleton = null;


        private CompeticaoDAO() {
            try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                 Statement stm = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS jogo (" +
                        "codProduto varchar(7) NOT NULL PRIMARY KEY," +
                        "nomeProduto varchar(45) NOT NULL)";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS competicao (" +
                        "codQR VARCHAR(7) NOT NULL PRIMARY KEY," +
                        "codProduto VARCHAR(7) NOT NULL,"+
                        "tamanho DECIMAL(5,2) NOT NULL," +
                        "localizacao VARCHAR(5) NOT NULL)";
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
    public static CompeticaoDAO getInstance() {
        if (CompeticaoDAO.singleton == null) {
            CompeticaoDAO.singleton = new CompeticaoDAO();
        }
        return CompeticaoDAO.singleton;
    }

    /**
     * @return número de paletes na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM competicao")) {
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
     * Método que verifica se existem paletes
     *
     * @return true se existirem 0 paletes
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }




}
