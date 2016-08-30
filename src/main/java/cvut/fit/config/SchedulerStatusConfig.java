package cvut.fit.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by Jakub Tuček on 29.8.2016.
 */
@Configuration
public class SchedulerStatusConfig {

    private boolean blogSchedulerStatus = false;
    private boolean redditSchedulerStatus = false;

    public boolean isBlogSchedulerStatus() {
        return blogSchedulerStatus;
    }

    public void setBlogSchedulerStatus(boolean blogSchedulerStatus) {
        this.blogSchedulerStatus = blogSchedulerStatus;
    }

    public boolean isRedditSchedulerStatus() {
        return redditSchedulerStatus;
    }

    public void setRedditSchedulerStatus(boolean redditSchedulerStatus) {
        this.redditSchedulerStatus = redditSchedulerStatus;
    }
}
