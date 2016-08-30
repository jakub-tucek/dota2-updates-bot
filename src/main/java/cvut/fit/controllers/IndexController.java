package cvut.fit.controllers;

import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.service.blog.BlogParsingException;
import cvut.fit.service.blog.DownloaderBlogService;
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

    @Autowired
    public IndexController(DownloaderBlogService downloaderBlogService) {
        this.downloaderBlogService = downloaderBlogService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<BlogUpdateEntry> blogUpdateEntries = downloaderBlogService.getAllBlogUpdates();
        model.addAttribute("blogUpdateEntries", blogUpdateEntries);

        Iterable<BlogEntry> blogEntries = downloaderBlogService.getAllBlog();
        model.addAttribute("blogEntries", blogEntries);

        return "index";
    }

    @RequestMapping("/reload")
    public String reload(Model model) {
        try {
            List<BlogUpdateEntry> blogUpdateEntries = downloaderBlogService.downloadAllBlogUpdates();
            model.addAttribute("blogUpdateEntries", blogUpdateEntries);

            List<BlogEntry> blogEntries = downloaderBlogService.downloadAllBlog();
            model.addAttribute("blogEntries", blogEntries);

        } catch (BlogParsingException | IOException ex) {
            log.error(ex.toString());
        }
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
}
