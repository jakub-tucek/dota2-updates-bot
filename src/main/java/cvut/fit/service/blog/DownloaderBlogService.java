package cvut.fit.service.blog;

import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.domain.repository.BlogEntryRepository;
import cvut.fit.domain.repository.BlogUpdateEntryRepository;
import cvut.fit.service.DownloaderConfig;
import cvut.fit.service.blog.parser.BlogParser;
import cvut.fit.service.blog.parser.BlogParsingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Service
public class DownloaderBlogService {
    private static final Logger log = LoggerFactory.getLogger(DownloaderBlogService.class);

    private final BlogParser blogParser;

    private final BlogUpdateEntryRepository blogUpdateEntryRepository;

    private final BlogEntryRepository blogEntryRepository;


    @Autowired
    public DownloaderBlogService(BlogParser blogParser, BlogUpdateEntryRepository blogUpdateEntryRepository, BlogEntryRepository blogEntryRepository) {
        this.blogParser = blogParser;
        this.blogUpdateEntryRepository = blogUpdateEntryRepository;
        this.blogEntryRepository = blogEntryRepository;
    }

    public List<BlogUpdateEntry> downloadAllBlogUpdates() throws IOException, BlogParsingException {
        List<BlogUpdateEntry> blogEntryList = new ArrayList<>();

        for (int i = 1; i <= DownloaderBlogConfig.BLOG_UPDATE_MAX_PAGES; i++) {

//          setProxy();

            Document html = Jsoup.connect(DownloaderBlogConfig.BLOG_UPDATE_URL + i).userAgent(DownloaderConfig.USER_AGENT).header("Accept-Language", DownloaderConfig.HEADER_ACCEPT_LANG).get();

            List<BlogUpdateEntry> basicBlogEntryListPage = blogParser.parseUpdatePage(html);

            if (basicBlogEntryListPage.size() == 0) break;

            blogEntryList.addAll(basicBlogEntryListPage);
        }
        for (int i = blogEntryList.size() - 1; i >= 0; i--) {
            blogUpdateEntryRepository.save(blogEntryList.get(i));
        }
        return blogEntryList;
    }

    public List<BlogEntry> downloadAllBlog() throws IOException, BlogParsingException {
        List<BlogEntry> blogEntryList = new ArrayList<>();
//        setProxy();

        for (int i = 1; i <= DownloaderBlogConfig.BLOG_MAX_PAGES; i++) {
            Document html = Jsoup.connect(DownloaderBlogConfig.BLOG_URL + i).userAgent(DownloaderConfig.USER_AGENT).header("Accept-Language", DownloaderConfig.HEADER_ACCEPT_LANG).get();

            List<BlogEntry> blogEntryListPage = blogParser.parseBlogPage(html);

            if (blogEntryListPage.size() == 0) break;
            blogEntryList.addAll(blogEntryListPage);
        }

        for (int i = blogEntryList.size() - 1; i >= 0; i--) {
            blogEntryRepository.save(blogEntryList.get(i));
        }

        return blogEntryList;
    }

    private void setProxy() {
        System.setProperty("http.proxyHost", DownloaderConfig.PROXY);
        System.setProperty("http.proxyPort", DownloaderConfig.PORT);
        System.setProperty("https.proxyHost", DownloaderConfig.PROXY);
        System.setProperty("https.proxyPort", DownloaderConfig.PORT);
    }


    /**
     * PERSISTENCE
     */

    public Iterable<BlogUpdateEntry> getAllBlogUpdates() {
        return blogUpdateEntryRepository.findAll();
    }


    public Iterable<BlogEntry> getAllBlog() {
        return blogEntryRepository.findAll();
    }
}
