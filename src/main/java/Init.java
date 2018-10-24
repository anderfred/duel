import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.*;


public class Init {
    static final String DB_CONNECTION_URI = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8";
    static final String DB_USERNAME = "root";
    static final String DB_PASSWORD = "root";
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    static BlockingQueue<Player> players = new ArrayBlockingQueue<>(20);
    static Map<String, Game> duels = new HashMap<>();

    static final Logger log = LoggerFactory.getLogger(Init.class);

    public static void main(String[] args) throws InterruptedException {
        log.debug("Print hello!");

        DuelMaker duelMaker = new DuelMaker(players);
        duelMaker.add(DataBase.findByName("fredx"));
        duelMaker.add(DataBase.findByName("arra"));
        //duelMaker.make(players.pop(), players.pop());
        new Thread(duelMaker).start();
        log.warn("*************");
        Thread.sleep(3000);
        log.warn("*************");
        while (true) {
            if (duels.containsKey("fredx")) {
                if (!duels.get("fredx").isPlayer1Dead() && !duels.get("fredx").isPlayer2Dead()) {
                    duels.get("fredx").hit(DataBase.findByName("fredx"));
                } else break;
            } else break;
        }

    }
}
