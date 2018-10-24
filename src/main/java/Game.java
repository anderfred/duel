import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {
    private static final Logger log = LoggerFactory.getLogger(Game.class);
    private Player player1, player2;
    private int p1Health, p2Health;
    private boolean player1Dead, player2Dead;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.p1Health = player1.getHealth();
        this.p2Health = player2.getHealth();
        player1Dead = false;
        player2Dead = false;
    }

    public void hit(Player player) {
        if (!player1Dead && !player2Dead) {
            if (player1.getHealth() >= 0 && player2.getHealth() >= 0) {
                if (player.getName().equals(player1.getName())) {
                    log.info("{} hit {} on {}", player1.getName(), player2.getName(), player1.getDamage());
                    player2.setHealth(player2.getHealth() - player1.getDamage());
                    DataBase.updatePlayer(player2);
                } else {
                    log.info("{} hit {} on {}", player2.getName(), player1.getName(), player2.getDamage());
                    player1.setHealth(player1.getHealth() - player2.getDamage());
                    DataBase.updatePlayer(player1);
                }
            }
            if (player1.getHealth() < 0) {
                player1Dead = true;
                Init.duels.remove(player1.getName());
                Init.duels.remove(player2.getName());
                player1.setHealth(p1Health + 1);
                player1.setDamage(player1.getDamage() + 1);
                player1.setRating(player1.getRating() - 1);
                player2.setRating(player2.getRating() + 1);
                player2.setHealth(p2Health + 1);
                player2.setDamage(player2.getDamage() + 1);
                DataBase.updatePlayer(player1);
                DataBase.updatePlayer(player2);
                log.info("Player 1 is dead {}", player1.getName());
            }
            if (player2.getHealth() < 0) {
                Init.duels.remove(player2.getName());
                Init.duels.remove(player1.getName());
                player2Dead = true;
                player2.setHealth(p2Health + 1);
                player2.setDamage(player2.getDamage() + 1);
                player2.setRating(player2.getRating() - 1);
                player1.setRating(player1.getRating() + 1);
                player1.setHealth(p1Health + 1);
                player1.setDamage(player1.getDamage() + 1);
                DataBase.updatePlayer(player1);
                DataBase.updatePlayer(player2);
                log.info("Player 2 {} is dead", player2.getName());
            }
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getP1Health() {
        return p1Health;
    }

    public int getP2Health() {
        return p2Health;
    }

    public boolean isPlayer1Dead() {
        return player1Dead;
    }

    public boolean isPlayer2Dead() {
        return player2Dead;
    }
}
