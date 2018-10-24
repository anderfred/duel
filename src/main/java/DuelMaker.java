import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;


public class DuelMaker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DuelMaker.class);

    private BlockingQueue<Player> stack;

    public DuelMaker(BlockingQueue<Player> stack) {
        this.stack = stack;
    }

    public static void add(Player player) {
        player.setReady(false);
        DataBase.updatePlayer(player);
        Init.players.add(player);
        log.info("DuelMaker player {} added to stack", player);
    }

    public void make(Player p1, Player p2) {
        Game game = new Game(p1, p2);
        Init.duels.put(p1.getName(), game);
        Init.duels.put(p2.getName(), game);
        p1.setReady(false);
        p2.setReady(false);
        DataBase.updatePlayer(p1);
        DataBase.updatePlayer(p2);
        log.info("Game Created {} vs {}", p1.getName(), p2.getName());
    }

    @Override
    public void run() {
        try{
            while (true){
                Thread.sleep(10);
                if(stack.size()>=2)
                make(stack.take(), stack.take());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
