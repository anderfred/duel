package logic;

import entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;

public class Game {
    private static final Logger log = LoggerFactory.getLogger(Game.class);
    private Player player1, player2;
    //Здоровье до начала дуели
    private int p1Health, p2Health;
    //Состояние смерти игрока
    private boolean player1Dead, player2Dead;
    //Имя подбедителя
    private String winner;

    public int getP1Health() {
        return p1Health;
    }

    public int getP2Health() {
        return p2Health;
    }

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
            if (player1.getHealth() > 0 && player2.getHealth() > 0) {
                if (player.getName().equals(player1.getName())) {
                    log.info("{} hit {} on {}", player1.getName(), player2.getName(), player1.getDamage());
                    logHits(player1, player2);
                    player2.setHealth(player2.getHealth() - player1.getDamage());
                    ContextListener.db.get().updatePlayer(player2);
                } else {
                    log.info("{} hit {} on {}", player2.getName(), player1.getName(), player2.getDamage());
                    logHits(player2, player1);
                    player1.setHealth(player1.getHealth() - player2.getDamage());
                    ContextListener.db.get().updatePlayer(player1);
                }
            }
            log.info("{} {}", player1, player2);
            if (player1.getHealth() <= 0) {
                player1Dead = true;
                winner = player2.getName();
                changeStatAfterFight(player1, -1);
                changeStatAfterFight(player2, 1);
                log.info("entity.Player 1 is dead {}", player1.getName());
            }
            if (player2.getHealth() <= 0) {
                player2Dead = true;
                winner = player1.getName();
                changeStatAfterFight(player1, 1);
                changeStatAfterFight(player2, -1);
                log.info("entity.Player 2 {} is dead", player2.getName());
            }
        }
    }

    public String getWinner() {
        return winner;
    }
    //Меняем параметры игроков после дуэли
    private void changeStatAfterFight(Player player, int mod) {
        if (player.getName().equals(player1.getName())) player.setHealth(p1Health + 1);
        else player.setHealth(p2Health + 1);
        player.setDamage(player.getDamage() + 1);
        player.setRating(player.getRating() + mod);
        ContextListener.db.get().updatePlayer(player);
    }


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }


    public boolean isPlayer1Dead() {
        return player1Dead;
    }

    public boolean isPlayer2Dead() {
        return player2Dead;
    }

    public void logHits(Player hero, Player enemy) {

        Deque<String> listHero = ContextListener.hitLog.get(hero.getName());
        Deque<String> listEnemy = ContextListener.hitLog.get(enemy.getName());
        listHero.addFirst("Вы ударили " + enemy.getName() + " на " + hero.getDamage() + " урона.");
        listEnemy.addFirst(hero.getName() + " ударил вас на " + hero.getDamage() + " урона");
        if (hero.getHealth() < 0) {
            listHero.addFirst("Вас убил " + enemy.getName());
            listEnemy.addFirst("Вы убили " + hero.getName());
        }
        if (listHero.size() > 3) {
            listHero.removeLast();
        }
        if (listEnemy.size() > 3) {
            listEnemy.removeLast();
        }
        log.info("logHits {} {}", listHero, listEnemy);
        ContextListener.hitLog.replace(hero.getName(), listHero);
        ContextListener.hitLog.replace(enemy.getName(), listEnemy);

    }

    public Player getPlayerByName(String name) {
        if (player1.getName().equals(name)) return player1;
        else return player2;
    }
}
