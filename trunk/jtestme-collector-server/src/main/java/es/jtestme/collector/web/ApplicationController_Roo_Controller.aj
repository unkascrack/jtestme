// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.web;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.ApplicationState;
import es.jtestme.collector.domain.Owner;
import es.jtestme.collector.domain.reference.EnvironmentType;
import es.jtestme.collector.web.ApplicationController;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ApplicationController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ApplicationController.create(@Valid Application application, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, application);
            return "applications/create";
        }
        uiModel.asMap().clear();
        application.persist();
        return "redirect:/applications/" + encodeUrlPathSegment(application.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ApplicationController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Application());
        return "applications/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String ApplicationController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("application", Application.findApplication(id));
        uiModel.addAttribute("itemId", id);
        return "applications/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ApplicationController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("applications", Application.findApplicationEntries(firstResult, sizeNo));
            float nrOfPages = (float) Application.countApplications() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("applications", Application.findAllApplications());
        }
        return "applications/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ApplicationController.update(@Valid Application application, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, application);
            return "applications/update";
        }
        uiModel.asMap().clear();
        application.merge();
        return "redirect:/applications/" + encodeUrlPathSegment(application.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String ApplicationController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Application.findApplication(id));
        return "applications/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String ApplicationController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Application application = Application.findApplication(id);
        application.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/applications";
    }
    
    void ApplicationController.populateEditForm(Model uiModel, Application application) {
        uiModel.addAttribute("application", application);
        uiModel.addAttribute("applicationstates", ApplicationState.findAllApplicationStates());
        uiModel.addAttribute("owners", Owner.findAllOwners());
        uiModel.addAttribute("environmenttypes", Arrays.asList(EnvironmentType.values()));
    }
    
    String ApplicationController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
