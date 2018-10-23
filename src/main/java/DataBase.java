import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DataBase {
    private static final Logger log = LoggerFactory.getLogger(DataBase.class);

    private static Connection getConnection() {

        Connection connection ;

        try {
            Class.forName(Init.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("DB getDbConnection ClassNotFoundException {}", e);
        }
        try {
            connection = DriverManager.getConnection(Init.DB_CONNECTION_URI, Init.DB_USERNAME, Init.DB_PASSWORD);
            log.info("Connected {}", connection.toString());
            return connection;
        } catch (SQLException e) {
            log.error("getDbConnection SQLException {}", e);
        }
        return null;
    }


    static void createPlayer(Player player) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            log.info("trying to create new Player {}", player);
            String sql = String.format("insert into players (uuid, name, rating, damage, health, password , ready) values ('%s', '%s' , %d, %d, %d, '%s', %d);",
                    player.getUuid(), player.getName(), player.getRating(), player.getDamage(), player.getHealth(), player.getPassword(), player.isReady() ? 1 : 0);
            statement.executeUpdate(sql);
            log.info("Ok");
            close(null, connection, statement);
        } catch (SQLException e) {
            log.error("createPlayer SQLException error {}", player, e);
        }
    }

    static Player findByName(String name) {
        if (count(name) > 0) {

            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                log.info("Trying executeSql {}", name);
                String playerSql = "SELECT * FROM players where name='" + name + "'";
                ResultSet resultSet = statement.executeQuery(playerSql);
                resultSet.first();
                Player player = new Player(resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getInt("rating"),
                        resultSet.getInt("damage"),
                        resultSet.getInt("health"),
                        resultSet.getBoolean("ready"));
                close(resultSet, connection, statement);
                log.info("FindByName {}", player);
                return player;
            } catch (SQLException e) {
                log.error("SQLException {}", e);
            }
        }
        log.info("FindByName return null *** byName:{}", name);
        return null;
    }

    static List<Player> findAll() {
        List<Player> allPlayers = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();) {
            log.info("Trying find all players executeSql {}");
            String playerSql = "SELECT * FROM players;";
            ResultSet resultSet = statement.executeQuery(playerSql);
            while (resultSet.next()) {
                Player player = new Player(
                        resultSet.getString("uuid"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getInt("rating"),
                        resultSet.getInt("damage"),
                        resultSet.getInt("health"),
                        resultSet.getBoolean("ready"));
                allPlayers.add(player);
            }
            close(resultSet, connection, statement);
            log.info("FindByName Complete");
        } catch (SQLException e) {
            log.error("SQLException {}", e);
        }
        return allPlayers;
    }

    private static void close(ResultSet resultSet, Connection connection, Statement statement) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            log.info("All connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int count(String column) {

        int count = 0;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
        ) {
            log.info("Trying executeSql {}", column);
            String countSql = "SELECT count(*) as count FROM players where name='" + column + "'";
            ResultSet resultSet = statement.executeQuery(countSql);
            log.info("executeSql ok");
            resultSet.first();
            count = resultSet.getInt("count");
            log.info("count:{}", count);
            close(resultSet, connection, statement);
        } catch (SQLException e) {
            log.error("DB count SQLException {}", e);
        }
        return count;
    }

    private static void delete(String name) {
        String sql = "delete from players where name='" + name + "';";
        if (count(name) == 1) {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()
            ) {
                log.info("DataBase delete -> trying to delete:{}", name);
                statement.executeUpdate(sql);
                log.info("DataBase successfully deleted:{}", name);

            } catch (SQLException e) {
                log.error("DataBase delete SQLException {}", e);
            }
        } else {
            log.info("DataBase delete -> no player for name:{}", name);
        }
    }

}

