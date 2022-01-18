package Data;

import java.util.ArrayList;

import Model.Moeda;
import Model.Jogo;
import java.sql.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.Collectors;

    public class MoedaDAO {
        private static Data.MoedaDAO singleton = null;


        /**
         * Implementação do padrão Singleton
         *
         * @return devolve a instância única desta classe
         */
        public static Data.MoedaDAO getInstance() {
            if (Data.MoedaDAO.singleton == null) {
                Data.MoedaDAO.singleton = new Data.MoedaDAO();
            }
            return Data.MoedaDAO.singleton;
        }

        /**
         * @return número de desportos na base de dados
         */

        public int size() {
            int i = 0;
            try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                 Statement stm = conn.createStatement();
                 ResultSet rs = stm.executeQuery("SELECT count(*) FROM Moeda")) {
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
         * Método que verifica se um id de uma moeda existe na base de dados
         *
         * @param idJ Moeda
         * @return true se a moeda existe
         * @throws NullPointerException
         */

        public boolean containsKey(Object idJ) {
            boolean r;
            try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                 Statement stm = conn.createStatement();
                 ResultSet rs =
                         stm.executeQuery("SELECT id FROM Moeda WHERE id='" + idJ.toString() + "'")) {
                r = rs.next();
            } catch (SQLException e) {
                // Database error!
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            return r;
        }

        /**
         * Verifica se uma moeda existe na base de dados
         *
         * @param value
         * @return true caso a moeda exista, false caso contrario
         * @throws NullPointerException
         */

        public boolean containsValue(Object value) {
            Moeda a = (Moeda) value;
            Moeda g = this.get(a.getNome());
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

        public Moeda get(Object id) {
            Moeda a = null;
            try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Moeda WHERE nome='" + id + "'");
                if (rs.next()) {  // A chave existe na tabela
                    a = new Moeda(rs.getString("nome"),rs.getFloat("exchangeComEuro"));
                }
            } catch(SQLException e) {
                // Database error!
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            return a;
        }

        public ArrayList<Moeda> getMoedas() {
            ArrayList<Moeda> a = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Moeda");
                while (rs.next()) {  // A chave existe na tabela
                    a.add(new Moeda(rs.getString("nome"),rs.getFloat("exchangeComEuro")));
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
                 ResultSet rs = stm.executeQuery("SELECT id FROM Moeda")){
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


        public void putTransaccao(String email ,String m, double d) {
            try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)){
                 Statement stm = conn.createStatement();
                 stm.executeUpdate("INSERT INTO CarteiraMoeda VALUES ('" + email + "', '" + m + "'," + d +
                         ")");
        }catch (Exception e) {
                // Database error!
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
        }



    }



