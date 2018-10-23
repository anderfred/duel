import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;



public class Init {
    static final String DB_CONNECTION_URI = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8";
    static final String DB_USERNAME = "root";
    static final String DB_PASSWORD = "root";
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    static final Logger log = LoggerFactory.getLogger(Init.class);

    public static void main(String[] args) throws SQLException {
        log.debug("Print hello!");
        System.out.println("Hello!");
        String sql = "SELECT id, name, password FROM user";

        DataBase.findByName("fredx");
        DataBase.createPlayer(new Player("123", "dsa", 2, 4, 6, true));
        DataBase.createPlayer(new Player("ggfd", "f3wf", 2, 4, 6, false));

    }
}
