package tanks.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "edu.school21.sockets")
@PropertySource("classpath:db.properties")
public class Config {

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.user}")
    private String dbUser;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${db.driver.name}")
    private String dbDriverName;

//    @Bean
//    UsersRepositoryImpl usersRepositoryImpl(DataSource dataSource) {
//        return new UsersRepositoryImpl(dataSource);
//    }
//
//    @Bean
//    public UsersServiceImpl usersServiceImpl() {
//        return new UsersServiceImpl();
//    }

    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dbDriverName);
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(dbUser);
        hikariConfig.setPassword(dbPassword);

        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

}