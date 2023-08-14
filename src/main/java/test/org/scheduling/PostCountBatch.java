/*
package test.org.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import test.org.scheduling.PostService;

@Component
public class PostCountBatch {

    private final PostService postService;

    public PostCountBatch(PostService postService) {
        this.postService = postService;
    }

    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void calculateAndPrintPostCounts() {
        for (int hour = 0; hour < 24; hour++) {
            int postCount = postService.getPostCountByHour(hour);
            System.out.println("Hour " + hour + ": " + postCount + " posts");
        }
    }
}




*/
