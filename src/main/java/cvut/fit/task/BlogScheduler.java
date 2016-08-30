package cvut.fit.task;

import cvut.fit.config.SchedulerStatusConfig;
import cvut.fit.service.blog.DownloaderBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Component
public class BlogScheduler {
    private static final Logger log = LoggerFactory.getLogger(BlogScheduler.class);

    private final DownloaderBlogService downloaderBlogService;
    private final SchedulerStatusConfig schedulerStatusConfig;

    @Autowired
    public BlogScheduler(DownloaderBlogService downloaderBlogService, SchedulerStatusConfig schedulerStatusConfig) {
        this.downloaderBlogService = downloaderBlogService;
        this.schedulerStatusConfig = schedulerStatusConfig;
    }


    @Scheduled(fixedRate = 10000)
    public void updateDataFromBlog() {
        if (schedulerStatusConfig.isBlogSchedulerStatus()) {
            log.info("scheduled update");
        }
    }
}
