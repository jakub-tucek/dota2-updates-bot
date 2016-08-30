package cvut.fit.controllers;

import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.domain.entity.RedditEntry;
import cvut.fit.service.blog.BlogParsingException;
import cvut.fit.service.blog.DownloaderBlogService;
import cvut.fit.service.reddit.DownloaderRedditService;
import cvut.fit.service.reddit.RedditParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final DownloaderBlogService downloaderBlogService;

    private final DownloaderRedditService downloaderRedditService;

    @Autowired
    public IndexController(DownloaderBlogService downloaderBlogService, DownloaderRedditService downloaderRedditService) {
        this.downloaderBlogService = downloaderBlogService;
        this.downloaderRedditService = downloaderRedditService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<BlogUpdateEntry> blogUpdateEntries = downloaderBlogService.getAllBlogUpdates();
        model.addAttribute("blogUpdateEntries", blogUpdateEntries);

        Iterable<BlogEntry> blogEntries = downloaderBlogService.getAllBlog();
        model.addAttribute("blogEntries", blogEntries);

        Iterable<RedditEntry> redditSirBelvedereEntries = downloaderRedditService.getAllSirBelvedere();
        model.addAttribute("redditSirBelvedereEntries", redditSirBelvedereEntries);

        return "index";
    }

    @RequestMapping("/reload")
    public String reload(Model model) {

        List<BlogUpdateEntry> blogUpdateEntries = null;
        try {
            blogUpdateEntries = downloaderBlogService.downloadBlogUpdates();
        } catch (IOException | BlogParsingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        model.addAttribute("blogUpdateEntries", blogUpdateEntries);


        List<BlogEntry> blogEntries = null;
        try {
            blogEntries = downloaderBlogService.downloadBlog();
        } catch (IOException | BlogParsingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        model.addAttribute("blogEntries", blogEntries);


        List<RedditEntry> redditSirBelvedereEntries = null;
        try {
            redditSirBelvedereEntries = downloaderRedditService.downloadSirBelvedere();
        } catch (IOException | RedditParsingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        model.addAttribute("redditSirBelvedereEntries", redditSirBelvedereEntries);
        return "index";
    }

    @RequestMapping("/updates")
    public String updates(Model model) {

        Iterable<BlogUpdateEntry> blogUpdateEntries = downloaderBlogService.getAllBlogUpdates();
        model.addAttribute("blogUpdateEntries", blogUpdateEntries);

        return "index";
    }

    @RequestMapping("/blog")
    public String blog(Model model) {
        Iterable<BlogEntry> blogEntries = downloaderBlogService.getAllBlog();
        model.addAttribute("blogEntries", blogEntries);

        return "index";
    }

    @RequestMapping("/redditSirBelvedere")
    public String reddit(Model model) {
        Iterable<RedditEntry> redditSirBelvedereEntries = downloaderRedditService.getAllSirBelvedere();
        model.addAttribute("redditSirBelvedereEntries", redditSirBelvedereEntries);

        return "index";
    }
}
