import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class Init {
    static final Logger log = LoggerFactory.getLogger(Init.class);

    public static void main(String[] args) {
        log.debug("Print hello!");
        System.out.println("Hello!");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8", "root", "root");
            log.debug("Connection established {}",conn.toString());
            conn.close();
            log.debug("Connection close");
        } catch (Exception e) {
            log.debug("Exception {}", e);
        }
    }
}
