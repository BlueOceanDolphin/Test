package test.org.models.board;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import test.org.controllers.board.BoardDataForm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class BoardDataDao {
    private final JdbcTemplate jdbcTemplate;

    public boolean save(BoardDataForm data) {
        long id = data.getId();
        int affectedRows = 0;
        if (id > 0) { // 수정
             String sql = "UPDATE TEST_BOARD " +
                    " SET " +
                    " POSTER = ?, " +
                    " SUBJECT = ?, " +
                    " CONTENT = ?, " +
                    " MODDT = SYSDATE " +
                    " WHERE ID = ?";
             affectedRows = jdbcTemplate.update(sql, data.getPoster(), data.getSubject(), data.getContent(), data.getId());
        } else { // 추가
            String sql = "INSERT INTO TEST_BOARD (ID, POSTER, SUBJECT, CONTENT) " +
                    " VALUES (TEST_SEQ.nextval, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            affectedRows = jdbcTemplate.update(con -> {
                PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
                pstmt.setString(1, data.getPoster());
                pstmt.setString(2, data.getSubject());
                pstmt.setString(3, data.getContent());

                return pstmt;
            }, keyHolder);

            id = keyHolder.getKey().longValue();
        }

        data.setId(id);

        return affectedRows > 0;
    }

    public BoardData get(long id) {
        try {
            String sql = "SELECT * FROM TEST_BOARD WHERE ID = ?";
            BoardData data = jdbcTemplate.queryForObject(sql, this::mapper, id);

            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public BoardData mapper(ResultSet rs, int i) throws SQLException {
        Timestamp modDt = rs.getTimestamp("MODDT");
        return BoardData.builder()
                .id(rs.getLong("ID"))
                .poster(rs.getString("POSTER"))
                .subject(rs.getString("SUBJECT"))
                .content(rs.getString("CONTENT"))
                .regDt(rs.getTimestamp("REGDT").toLocalDateTime())
                .modDt(modDt == null ? null : modDt.toLocalDateTime())
                .build();
    }
}
