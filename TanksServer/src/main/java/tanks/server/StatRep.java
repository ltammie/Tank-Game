package tanks.server;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

class StatMapper implements RowMapper<Stat> {

    @Override
    public Stat mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        int shots = resultSet.getInt("shots");
        int hits = resultSet.getInt("hits");
        int misses = resultSet.getInt("misses");
        return new Stat(id, shots, hits, misses);
    }
}

@Component
public class StatRep {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_SAVE = "INSERT INTO stats (id, shots, hits, misses) VALUES (?, ?, ?, ?)";

    public StatRep(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Boolean save(Stat entity) {
        int rowsSaved;
        try {
            rowsSaved = jdbcTemplate.update(SQL_SAVE, entity.getId(), entity.getShots(), entity.getHits(), entity.getMisses());
            return rowsSaved > 0;
        } catch (DataAccessException e) {
            System.err.println("Cant save " + entity.toString());
        }
        return false;
    }
}