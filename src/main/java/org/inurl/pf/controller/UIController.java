package org.inurl.pf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author raylax
 */
@Controller
@RequestMapping("/")
public class UIController {

    @RequestMapping
    public String index() {
        return "index";
    }

}
