package db;

import entity.Player;

import java.util.List;

public interface DBRepository {
    void createPlayer(Player player);

    void updatePlayer(Player player);

    Player findByName(String name);

    List<Player> findAll();

    void delete(String name);

    boolean userIsExist(String name, String password);
}
