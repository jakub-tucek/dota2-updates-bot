package cvut.fit.controllers;

import cvut.fit.task.BlogScheduler;
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

    private final BlogScheduler blogScheduler;

    private final SchedulerStatusConfig schedulerStatusConfig;

    @Autowired
    public ConfigController(BlogScheduler blogScheduler, SchedulerStatusConfig schedulerStatusConfig) {
        this.blogScheduler = blogScheduler;
        this.schedulerStatusConfig = schedulerStatusConfig;
    }


    @RequestMapping("")
    String config(Model model) {

        model.addAttribute("blogSchedulerStatus", schedulerStatusConfig.isBlogSchedulerStatus());

        return "config";
    }


    @RequestMapping("toggleTimer")
    String toggleTimer() {
        schedulerStatusConfig.setBlogSchedulerStatus(!schedulerStatusConfig.isBlogSchedulerStatus());

        return "redirect:/config";
    }
}
