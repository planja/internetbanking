package webui.controller.service;

import domain.entity.services.Service;
import infrastructure.service.service.IServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webui.viewmodel.common.InfoViewModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Никита on 30.12.2016.
 */
@Controller
public class ServiceController {

    @Autowired
    private IServiceService serviceService;

    @RequestMapping(value = {"/services"}, method = RequestMethod.GET)
    public String services() {
        return "service/servicePage";
    }

    @RequestMapping(value = "/getServicesInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InfoViewModel> getServicesInfo() {
        return serviceService.findAll().stream().map(o -> new InfoViewModel(o.getName(), o.getId().intValue())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getServices", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<Service> getServices() {
        return serviceService.findAll();
    }

    @RequestMapping(value = "/saveService",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    Service saveService(@RequestBody Service service) {
        service.setName(service.getName().toUpperCase());
        return serviceService.save(service);
    }

    @RequestMapping(value = "/updateService",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    Service updateService(@RequestBody Service service) {
        service.setName(service.getName().toUpperCase());
        return serviceService.update(service);
    }

    @RequestMapping(value = "/deleteService/{id}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
