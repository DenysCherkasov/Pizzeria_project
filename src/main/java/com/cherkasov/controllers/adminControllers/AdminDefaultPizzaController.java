package com.cherkasov.controllers.adminControllers;

import com.cherkasov.dto.DefaultPizzaMainInfoDto;
import com.cherkasov.models.dish.DefaultPizza;
import com.cherkasov.models.dish.DishType;
import com.cherkasov.models.dish.PizzaType;
import com.cherkasov.services.DefaultPizzaService;
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
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('MANAGER')")
public class AdminDefaultPizzaController {
    private final DefaultPizzaService defaultPizzaService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDefaultPizzaController.class);

    @Autowired
    public AdminDefaultPizzaController(DefaultPizzaService defaultPizzaService) {
        this.defaultPizzaService = defaultPizzaService;
    }

    @GetMapping
    public ModelAndView getAdminPanel(ModelAndView modelAndView) {
        modelAndView.setViewName("adminDefaultPizza/admin");
        return modelAndView;
    }

    @GetMapping("/default-pizza")
    public ModelAndView getPanelDefaultPizza(ModelAndView modelAndView) {
        modelAndView.setViewName("adminDefaultPizza/default-pizza");
        return modelAndView;
    }

    @GetMapping("/default-pizza/create")
    public ModelAndView getPanelDefaultPizzaCreation(ModelAndView modelAndView) {
        modelAndView.setViewName("adminDefaultPizza/default-pizza-create");
        return modelAndView;
    }

    @PostMapping("/default-pizza/create")
    public ModelAndView createDefaultPizza(@ModelAttribute DefaultPizza defaultPizza, ModelAndView modelAndView) {
        if (defaultPizza != null) {
            defaultPizza.setPizzaType(PizzaType.DEFAULT);
            defaultPizza.setDishType(DishType.PIZZA);
            defaultPizzaService.save(defaultPizza);
            modelAndView.setViewName("adminDefaultPizza/default-pizza-create");
            LOGGER.info("New default pizza created");
        } else {
            LOGGER.error("Error while creating default pizza");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/default-pizza/delete-update")
    public ModelAndView getListDefaultPizza(ModelAndView modelAndView) {
        List<DefaultPizzaMainInfoDto> defaultPizzasInfo = defaultPizzaService.getDefaultPizzaInfo();
        modelAndView.addObject("pizzas", defaultPizzasInfo);
        modelAndView.setViewName("adminDefaultPizza/default-pizza-delete-update");
        return modelAndView;
    }

    @DeleteMapping("/default-pizza/delete/{id}")
    public String deletePizza(@PathVariable final String id, ModelAndView modelAndView) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while deletion the default pizza");
            return "redirect:http://localhost:8080/error";
        }
        defaultPizzaService.deleteById(id);
        LOGGER.info("Default pizza with id {} was deleted", id);
        return "redirect:http://localhost:8080/admin/default-pizza/delete-update";
    }

    @GetMapping("/default-pizza/update/{id}")
    public ModelAndView getDefaultPizzaUpdate(@PathVariable final String id, ModelAndView modelAndView) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while updating a default pizza");
            modelAndView.setViewName("error");
        } else {
            Optional<DefaultPizza> byId = defaultPizzaService.getById(id);
            byId.ifPresent(defaultPizza -> modelAndView.addObject("pizza", defaultPizza));
            modelAndView.setViewName("adminDefaultPizza/default-pizza-update");
        }
        return modelAndView;
    }

    @PutMapping("/default-pizza/update")
    public String deletePizzaByName(@ModelAttribute DefaultPizza defaultPizza, ModelAndView modelAndView) {
        if (defaultPizza != null) {
            defaultPizza.setPizzaType(PizzaType.DEFAULT);
            defaultPizza.setDishType(DishType.PIZZA);
            defaultPizzaService.save(defaultPizza);
            LOGGER.info("Default pizza with id {} was updated", defaultPizza.getId());
            return "redirect:http://localhost:8080/admin/default-pizza/delete-update";
        } else {
            LOGGER.error("Error while updating a default pizza");
            return "redirect:http://localhost:8080/error";
        }
    }

}

