package test.org.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PostService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시에 실행
    public void countPostsByHour() {
        String query = "SELECT TO_CHAR(REGDT, 'HH24') AS hour, COUNT(*) AS count FROM TEST_BOARD GROUP BY hour";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);

        // 시간대 별 글 작성 횟수 출력
        for (int hour = 0; hour < 24; hour++) {
            String hourStr = (hour < 10) ? "0" + hour : String.valueOf(hour);
            int postCount = 0;

            for (Map<String, Object> row : result) {
                if (hourStr.equals(row.get("hour"))) {
                    postCount = ((Number) row.get("count")).intValue();
                    break;
                }
            }

            System.out.println("Hour: " + hourStr + " - Post Count: " + postCount);
        }
    }
}
