package cvut.fit.service.blog;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
public class DownloaderBlogConfig {

    /**
     * Update part config
     */
    public final static int BLOG_UPDATE_MAX_PAGES = 8; //28.8 - 8

    public final static String BLOG_UPDATE_URL = "http://www.dota2.com/news/updates/?p=";

    /**
     * Blog part config
     */
    public final static int BLOG_MAX_PAGES = 3; //28.8 - 52

        public final static String BLOG_URL = "http://blog.dota2.com/page/";
//    public final static String BLOG_URL = "http://tomas.hostareal.cz/dota/index.php";

    /**
     * Parsing const
     */
    public final static String ENTRY_PREFIX_ID = "post-";
    public final static String TITLE_SELECTOR = "h2[class=entry-title]";
    public final static String POST_DATE_AUTHOR_SELECTOR = "div[class=entry-meta]";
    public final static String CONTENT_SELECTOR = "div[class=entry-content]";
    public static final String DATE_FORMAT = "MMMM d, yyyy";
    public static final int AUTHOR_OFFSET = 1;
}
