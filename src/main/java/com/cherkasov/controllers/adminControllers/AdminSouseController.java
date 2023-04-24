package com.cherkasov.controllers.adminControllers;

import com.cherkasov.dto.SouseMainInfoDto;
import com.cherkasov.models.pizzaParts.PizzaPartsType;
import com.cherkasov.models.pizzaParts.Souse;
import com.cherkasov.services.SouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/souse")
@PreAuthorize("hasAuthority('MANAGER')")
public class AdminSouseController {
    private final SouseService souseService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDefaultPizzaController.class);

    @Autowired
    public AdminSouseController(SouseService souseService) {
        this.souseService = souseService;
    }

    @GetMapping
    public ModelAndView getPanelSouse(ModelAndView modelAndView) {
        modelAndView.setViewName("adminSouse/souse");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getPanelSouseCreation(ModelAndView modelAndView) {
        modelAndView.setViewName("adminSouse/souse-create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createSouse(@ModelAttribute Souse souse, ModelAndView modelAndView) {
        if (souse != null) {
            souse.setPizzaPartsType(PizzaPartsType.SOUSE);
            souseService.save(souse);
            LOGGER.info("New souse {} created", souse.getName());
            modelAndView.setViewName("adminSouse/souse-create");
        } else {
            LOGGER.error("Error while creating new souse");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/delete-update")
    public ModelAndView getListSouse(ModelAndView modelAndView) {
        List<SouseMainInfoDto> souseInfo = souseService.getSouseInfo();
        modelAndView.addObject("souses", souseInfo);
        modelAndView.setViewName("adminSouse/souse-delete-update");
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSouse(@PathVariable final String id) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while deletion souse");
            return "redirect:http://localhost:8080/error";
        }
        souseService.deleteById(id);
        LOGGER.info("Souse with id {} was deleted", id);
        return "redirect:http://localhost:8080/admin/souse/delete-update";
    }

    @GetMapping("/update/{id}")
    public ModelAndView getSouseUpdate(@PathVariable final String id, ModelAndView modelAndView) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while updating souse");
            modelAndView.setViewName("error");
        } else {
            Optional<Souse> souseById = souseService.getById(id);
            souseById.ifPresent(souse -> modelAndView.addObject("souse", souse));
            modelAndView.setViewName("adminSouse/souse-update");
        }
        return modelAndView;
    }

    @PutMapping("/update")
    public String deleteSouseByName(@ModelAttribute Souse souse) {
        if (souse != null) {
            souse.setPizzaPartsType(PizzaPartsType.SOUSE);
            souseService.save(souse);
            LOGGER.info("Ingredient with id {} was updated", souse.getId());
            return "redirect:http://localhost:8080/admin/souse/delete-update";
        } else {
            LOGGER.error("Error while updating souse");
            return "redirect:http://localhost:8080/error";
        }
    }
}