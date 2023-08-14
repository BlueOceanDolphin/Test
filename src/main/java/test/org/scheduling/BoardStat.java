package test.org.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BoardStat {
    //@Scheduled(fixedRate = 3000)
    @Scheduled(cron="0 0 1 * * *")
    public void makeStat() {
        System.out.println("3초마다 실행!");
    }
}
