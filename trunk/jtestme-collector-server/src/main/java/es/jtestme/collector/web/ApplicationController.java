package es.jtestme.collector.web;

import es.jtestme.collector.domain.Application;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/applications")
@Controller
@RooWebScaffold(path = "applications", formBackingObject = Application.class)
public class ApplicationController {
}
