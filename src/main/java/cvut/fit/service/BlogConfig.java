package cvut.fit.service;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
public class BlogConfig {

    /**
     * Basic config
     */

    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";

    public final static String HEADER_ACCEPT_LANG = "en-US";


    /**
     * Update part config
     */
    public final static int BLOG_UPDATE_MAX_PAGES = 8;

    public final static String BLOG_UPDATE_URL = "http://www.dota2.com/news/updates/?p=";


    /**
     * Parsing const
     */
    public final static String ENTRY_PREFIX_ID = "post-";
    public final static String TITLE_SELECTOR = "h2[class=entry-title]";
    public final static String POST_DATE_SELECTOR = "div[class=entry-meta]";
    public final static String CONTENT_SELECTOR = "div[class=entry-content]";

    public static final String DATE_FORMAT = "MMMM d, yyyy";
}
