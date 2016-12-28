package com.pynguins.controllers;

import com.pynguins.models.Page;
import com.pynguins.models.PageDao;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PageController {
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class ResourceNotFoundException extends RuntimeException {
    }

    @Autowired
    private PageDao pageDao;

    private ModelAndView makeMav(String content, String displayName) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        TextProcessor processor = BBProcessorFactory.getInstance().create();
        mav.addObject("content", processor.process(content));
        mav.addObject("displayName", displayName);
        return mav;
    }

    @RequestMapping(value = "/article/item/{pageName}", method = RequestMethod.GET)
    public ModelAndView pageRead(@PathVariable("pageName") String pageName) {
        Page page = pageDao.findByName(pageName);
        if (page == null) {
            return this.makeMav("Page Not Found", "Error");
        } else {
            ModelAndView mav = this.makeMav(page.getContent(), page.getDisplayName());
            mav.addObject("pageName", pageName);
            return mav;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return this.pageRead("Front_Page");
    }

    @RequestMapping(value = "/article/list", method = RequestMethod.GET)
    public ModelAndView pageList() {
        Iterable<Page> pages = pageDao.findAll();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("list");
        mav.addObject("listItems", pages);
        return mav;
    }

    @RequestMapping(value = "/article/update/{pageName}", method = RequestMethod.GET)
    public ModelAndView pageEdit(@PathVariable("pageName") String pageName) {
        Page page = pageDao.findByName(pageName);
        if (page == null) {
            return this.makeMav("Page Not Found", "Error");
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("edit_create");
        mav.addObject("edit", true);
        mav.addObject("name", page.getName());
        mav.addObject("content", page.getContent());
        mav.addObject("displayName", page.getDisplayName());
        return mav;
    }

    @RequestMapping(value = "/article/update/{pageName}", method = RequestMethod.POST)
    @Transactional
    public String pageUpdatePost(@PathVariable("pageName") String pageName,
                                 @RequestParam("displayName") String displayName,
                                 @RequestParam("content") String content) {
        Page page = pageDao.findByName(pageName);
        if (page == null) {
            throw new ResourceNotFoundException();
        }
        page.setDisplayName(displayName);
        page.setContent(content);
        return "redirect:/article/item/" +  pageName;
    }

    @RequestMapping(value = "/article/create/{pageName}", method = RequestMethod.GET)
    public ModelAndView pageCreate(@PathVariable("pageName") String pageName) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("edit_create");
        mav.addObject("edit", false);
        mav.addObject("name", pageName);
        mav.addObject("content", "");
        mav.addObject("displayName", "");
        return mav;
    }

    @RequestMapping(value = "/article/create/{pageName}", method = RequestMethod.POST)
    @Transactional
    public String pageCreatePost(@PathVariable("pageName") String pageName,
                                 @RequestParam("displayName") String displayName,
                                 @RequestParam("content") String content) {
        Page page = new Page(pageName);
        page.setDisplayName(displayName);
        page.setContent(content);
        pageDao.save(page);
        return "redirect:/article/item/" +  pageName;
    }

    @RequestMapping(value = "/redirect/{pageName}", method = RequestMethod.POST)
    @Transactional
    public String pageRedirect(@PathVariable("pageName") String pageName,
                               @RequestParam("pageId") String pageId) {
        System.out.println(pageName);
        System.out.println(pageId);
        return "redirect:/" + pageName.replace("_", "/") + "/" + pageId;
    }
}
