import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/heisei_riders");
        config.setUsername("heisei_user");
        config.setPassword("riderkick");
        
        config.setMaximumPoolSize(5); 
        config.setMinimumIdle(2);     
        
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    public static void close() {
        if (ds != null) {
            ds.close();
        }
    }
}

