package es.jtestme.collector.web;

import es.jtestme.collector.domain.Owner;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
@RooWebScaffold(path = "owners", formBackingObject = Owner.class)
public class OwnerController {
}
