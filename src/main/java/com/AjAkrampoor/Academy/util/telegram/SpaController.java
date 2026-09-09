package com.AjAkrampoor.Academy.util.telegram;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    // Catch all paths without a file extension (like .js, .css, .png)
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
