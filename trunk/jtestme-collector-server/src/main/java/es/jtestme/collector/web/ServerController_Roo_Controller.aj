// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.web;

import es.jtestme.collector.domain.Server;
import es.jtestme.collector.domain.ServerState;
import es.jtestme.collector.domain.reference.ServerType;
import es.jtestme.collector.service.JTestMeCollectorService;
import es.jtestme.collector.web.ServerController;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ServerController_Roo_Controller {
    
    @Autowired
    JTestMeCollectorService ServerController.jTestMeCollectorService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ServerController.create(@Valid Server server, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, server);
            return "servers/create";
        }
        uiModel.asMap().clear();
        server.persist();
        return "redirect:/servers/" + encodeUrlPathSegment(server.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ServerController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Server());
        return "servers/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String ServerController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("server", Server.findServer(id));
        uiModel.addAttribute("itemId", id);
        return "servers/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ServerController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("servers", Server.findServerEntries(firstResult, sizeNo));
            float nrOfPages = (float) Server.countServers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("servers", Server.findAllServers());
        }
        return "servers/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ServerController.update(@Valid Server server, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, server);
            return "servers/update";
        }
        uiModel.asMap().clear();
        server.merge();
        return "redirect:/servers/" + encodeUrlPathSegment(server.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String ServerController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Server.findServer(id));
        return "servers/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String ServerController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Server server = Server.findServer(id);
        server.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/servers";
    }
    
    void ServerController.populateEditForm(Model uiModel, Server server) {
        uiModel.addAttribute("server", server);
        uiModel.addAttribute("environments", jTestMeCollectorService.findAllEnvironments());
        uiModel.addAttribute("serverstates", ServerState.findAllServerStates());
        uiModel.addAttribute("servertypes", Arrays.asList(ServerType.values()));
    }
    
    String ServerController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
