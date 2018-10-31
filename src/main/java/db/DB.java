package db;

import entity.Player;
import logic.ContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//Работа с базой данных
public class DB {
    private static final Logger log = LoggerFactory.getLogger(DB.class);

    public void createTable(){
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE players(\n" +
                    "  name VARCHAR(255) NOT NULL,\n" +
                    "  password VARCHAR(255) NOT NULL ,\n" +
                    "  health int NOT NULL ,\n" +
                    "  damage int NOT NULL ,\n" +
                    "  rating int NOT NULL,\n" +
                    "  ready boolean NOT NULL ,\n" +
                    "  PRIMARY KEY (name)\n" +
                    ")DEFAULT CHARSET=utf8;";
            statement.execute(sql);

            log.info(" Ok :");
            close(null, connection, statement);
        } catch (SQLException e) {
            log.error("createPlayer SQLException error {}", e);
        }
    }
    public static Connection getConnection() {

        Connection connection;

        try {
            Class.forName(ContextListener.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("DB getDbConnection ClassNotFoundException {}", e);
        }
        try {
            connection = DriverManager.getConnection(ContextListener.DB_CONNECTION_URI, ContextListener.DB_USERNAME, ContextListener.DB_PASSWORD);
            log.info("Connected {}", connection.toString());
            return connection;
        } catch (SQLException e) {
            log.error("getDbConnection SQLException {}", e);
        }
        return null;
    }


    public void createPlayer(Player player) {
        if (count(player.getName()) == 0) {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                ContextListener.sqlCount.incrementAndGet();
                log.info("trying to create new entity.Player {}", player);
                String sql = String.format("insert into players ( name, rating, damage, health, password , ready) values ('%s' , %d, %d, %d, '%s', %d);",
                        player.getName(), player.getRating(), player.getDamage(), player.getHealth(), player.getPassword(), player.isReady() ? 1 : 0);
                statement.executeUpdate(sql);

                log.info("createPlayer Ok statements:");
                close(null, connection, statement);
            } catch (SQLException e) {
                log.error("createPlayer SQLException error {}", player, e);
            }
        } else {
            log.info("player {} already in db", player);
        }
    }

    public void updatePlayer(Player player) {
        try (Connection connection = getConnection()
        ) {
            ContextListener.sqlCount.incrementAndGet();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE players SET damage = ?, health = ? , ready= ? , rating = ? WHERE name = ?");
            log.info("trying to update  entity.Player {}", player);
            statement.setInt(1, player.getDamage());
            statement.setInt(2, player.getHealth());
            statement.setBoolean(3, player.isReady());
            statement.setInt(4, player.getRating());
            statement.setString(5, player.getName());
            statement.executeUpdate();

            log.info("updatePlayer Ok");
            close(null, connection, statement);
        } catch (SQLException e) {
            log.error("updatePlayer SQLException error {}", player, e);
        }
    }

    public Player findByName(String name) {
        if (count(name) > 0) {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                ContextListener.sqlCount.incrementAndGet();
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
                log.info("FindByName {}", player);
                return player;
            } catch (SQLException e) {
                log.error("SQLException {}", e);
            }
        }
        log.info("FindByName return null *** byName:{}", name);
        return null;
    }

    public List<Player> findAll() {
        List<Player> allPlayers = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ContextListener.sqlCount.incrementAndGet();
            log.info("Trying find all players executeSql {}");
            String playerSql = "SELECT * FROM players;";
            ResultSet resultSet = statement.executeQuery(playerSql);
            while (resultSet.next()) {
                Player player = new Player(
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
    //Закрываем открытые соединения
    private void close(ResultSet resultSet, Connection connection, Statement statement) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            log.info("All connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int count(String column) {

        int count = 0;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ) {
            ContextListener.sqlCount.incrementAndGet();
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

    public void delete(String name) {
        String sql = "delete from players where name='" + name + "';";
        if (count(name) == 1) {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()
            ) {
                ContextListener.sqlCount.incrementAndGet();
                log.info("db.DB delete -> trying to delete:{}", name);
                statement.executeUpdate(sql);
                log.info("db.DB successfully deleted:{}", name);


            } catch (SQLException e) {
                log.error("db.DB delete SQLException {}", e);
            }
        } else {
            log.info("db.DB delete -> no player for name:{}", name);
        }
    }

    public boolean userIsExist(String name, String password) {
        if (name != null) {
            ContextListener.sqlCount.incrementAndGet();
            Player player = findByName(name);
            if (player != null) {
                if (player.getPassword().equals(password)) return true;
            }
        }
        return false;
    }

}

