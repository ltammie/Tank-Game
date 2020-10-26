package tanks.server;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

class StatMapper implements RowMapper<Stat> {

    @Override
    public Stat mapRow(ResultSet resultSet, int i) throws SQLException {
        int shots = resultSet.getInt("shots");
        int hits = resultSet.getInt("hits");
        int misses = resultSet.getInt("misses");
        return new Stat(shots, hits, misses);
    }
}

@Component
public class StatRep {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_SAVE = "INSERT INTO users (login, password) VALUES (?, ?)";

    public StatRep(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}