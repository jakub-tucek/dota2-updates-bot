package cvut.fit.service.reddit;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
public class DownloaderRedditConfig {
    public static final int REDDIT_MAX_PAGES = 1;

    public static final String REDDIT_SIR_BELVEDERE_URL = "https://www.reddit.com/user/SirBelvedere/submitted/#page=";

    public static final String URL_SELECTOR = "a[class^=title]";

    public static final String TITLE_SELECTOR = "a[class^=title]";

    public static final String TIMESTAMPS_SELECTOR = "time";

    public static final String SUBREDDIT_SELECTOR = "span[class=domain]";

    public static final String CONTENT_MAIN_DIV_SELECTOR = "div[class^=expando]";

    public static final String CONTENT_INNER_DIV_SELECTOR = "div[class=md]";

    public static final String AUTHOR_SELECTOR = "a[class^=author]";

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";

}
