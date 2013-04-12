package es.jtestme.collector.web;

import es.jtestme.collector.domain.Environment;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/environments")
@Controller
@RooWebScaffold(path = "environments", formBackingObject = Environment.class)
public class EnvironmentController {
}
