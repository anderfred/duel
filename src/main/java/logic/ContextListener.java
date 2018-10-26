package logic;

import db.DB;
import entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

@WebListener
public class ContextListener implements ServletContextListener {
    public static final String DB_CONNECTION_URI = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static BlockingQueue<Player> players = new ArrayBlockingQueue<>(20);
    public static AtomicReference<Map<String, Game>> duels = new AtomicReference<>(new HashMap<>());
    public static AtomicReference<DB> db = new AtomicReference<>(new DB());

    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Starting Up!");
        System.out.println("Starting up!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Shutting down!");
        System.out.println("Shutting down!");
    }
}
