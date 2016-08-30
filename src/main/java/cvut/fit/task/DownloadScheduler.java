package cvut.fit.task;

import cvut.fit.config.SchedulerStatusConfig;
import cvut.fit.service.blog.BlogParsingException;
import cvut.fit.service.blog.DownloaderBlogService;
import cvut.fit.service.reddit.DownloaderRedditService;
import cvut.fit.service.reddit.RedditParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Component
public class DownloadScheduler {
    private static final Logger log = LoggerFactory.getLogger(DownloadScheduler.class);

    private final DownloaderRedditService downloaderRedditService;
    private final DownloaderBlogService downloaderBlogService;
    private final SchedulerStatusConfig schedulerStatusConfig;

    @Autowired
    public DownloadScheduler(DownloaderRedditService downloaderRedditService, DownloaderBlogService downloaderBlogService, SchedulerStatusConfig schedulerStatusConfig) {
        this.downloaderRedditService = downloaderRedditService;
        this.downloaderBlogService = downloaderBlogService;
        this.schedulerStatusConfig = schedulerStatusConfig;
    }

    @Scheduled(fixedRate = 10000)
    public void updateDataFromBlog() {
        if (schedulerStatusConfig.isBlogSchedulerStatus()) {
            try {
                log.info("Scheduler blog run");
                downloaderBlogService.downloadBlogUpdates();
                downloaderBlogService.downloadBlog();
            } catch (IOException | BlogParsingException ex) {
                ex.printStackTrace();
                log.error(ex.toString());
            }
        }
    }

    @Scheduled(fixedRate = 10000)
    public void updateDataFromReddit() {
        if (schedulerStatusConfig.isRedditSchedulerStatus()) {
            try {
                log.info("Scheduler reddit run");
                downloaderRedditService.downloadSirBelvedere();
            } catch (IOException | RedditParsingException ex) {
                ex.printStackTrace();
                log.error(ex.toString());
            }
        }
    }
}
