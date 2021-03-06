package logic;

import db.DB;
import entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@WebListener
public class ContextListener implements ServletContextListener {

    //Параметры подключения в БД
    public static final String DB_CONNECTION_URI = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    //Очередь игроков готовых к матчу
    public static BlockingQueue<Player> players = new ArrayBlockingQueue<>(20);
    //Список матчей проходящий в данный момент
    public static AtomicReference<Map<String, Game>> duels = new AtomicReference<>(new HashMap<>());
    //Ссылка для работы с базой данных
    public static AtomicReference<DB> db = new AtomicReference<>(new DB());
    //Лог урона для каждого игрока
    public static Map<String, Deque<String>> hitLog = new HashMap<>();
    //Количество запросов к базе данных
    public static AtomicInteger sqlCount = new AtomicInteger(0);
    //Дата испольуется для определения скорости заргузки страницы
    public static AtomicReference<Date> date = new AtomicReference<>(new Date());
    //Список игроков онлайн
    public static AtomicReference<Map<String, Player>> playersOnline = new AtomicReference<>(new HashMap<>());

    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Starting Up!");
        db.get().createTable();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Shutting down!");
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.info("deregistering jdbc driver: %s", driver);
            } catch (SQLException e) {
                log.error("Error deregistering driver %s", e);
            }

        }
    }
}
