package cvut.fit.controllers;

import cvut.fit.config.SchedulerStatusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jakub Tuček on 29.8.2016.
 */
@Controller
@RequestMapping("/config")
public class ConfigController {
    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    private final SchedulerStatusConfig schedulerStatusConfig;

    @Autowired
    public ConfigController(SchedulerStatusConfig schedulerStatusConfig) {
        this.schedulerStatusConfig = schedulerStatusConfig;
    }


    @RequestMapping("")
    public String config(Model model) {
        model.addAttribute("blogSchedulerStatus", schedulerStatusConfig.isBlogSchedulerStatus());
        model.addAttribute("redditSchedulerStatus", schedulerStatusConfig.isRedditSchedulerStatus());

        return "config";
    }


    @RequestMapping("toggleBlogScheduler")
    public String toggleBlogScheduler() {
        schedulerStatusConfig.setBlogSchedulerStatus(!schedulerStatusConfig.isBlogSchedulerStatus());

        return "redirect:/config";
    }

    @RequestMapping("toggleRedditScheduler")
    public String toggleRedditScheduler() {
        schedulerStatusConfig.setRedditSchedulerStatus(!schedulerStatusConfig.isRedditSchedulerStatus());

        return "redirect:/config";
    }
}
