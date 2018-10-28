package logic;

import entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;


public class DuelMaker {
    private static final Logger log = LoggerFactory.getLogger(DuelMaker.class);

    private BlockingQueue<Player> blockingQueue;

    public DuelMaker(BlockingQueue<Player> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    //Добавления новго игрока в очередь на дуэль
    public static void add(Player player) {
        player.setReady(false);
        ContextListener.db.get().updatePlayer(player);
        ContextListener.players.add(player);
        log.info("logic.DuelMaker player {} added to stack", player);
    }

    private static void make(Player p1, Player p2) {
        Game game = new Game(p1, p2);
        ContextListener.duels.get().put(p1.getName(), game);
        ContextListener.duels.get().put(p2.getName(), game);
        ContextListener.db.get().updatePlayer(p1);
        ContextListener.db.get().updatePlayer(p2);
        log.info("logic.Game Created {} vs {}", p1.getName(), p2.getName());
    }

    //Метод создания дуэли
    public static void makeDuel() {
        if (ContextListener.players.size() >= 2) {
            try {
                make(ContextListener.players.take(), ContextListener.players.take());
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
        }
    }
}
