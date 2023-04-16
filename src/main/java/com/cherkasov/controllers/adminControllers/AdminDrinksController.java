package com.cherkasov.controllers.adminControllers;

import com.cherkasov.dto.DrinksMainInfoDto;
import com.cherkasov.models.dish.DishType;
import com.cherkasov.models.dish.Drinks;
import com.cherkasov.services.DrinksService;
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
@RequestMapping("/admin/drinks")
@PreAuthorize("hasAuthority('MANAGER')")
public class AdminDrinksController {
    private final DrinksService drinksService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDefaultPizzaController.class);

    @Autowired
    public AdminDrinksController(DrinksService drinksService) {
        this.drinksService = drinksService;
    }


    @GetMapping()
    public ModelAndView getPanelDrinks(ModelAndView modelAndView) {
        modelAndView.setViewName("adminDrinks/drinks");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getPanelDrinksCreation(ModelAndView modelAndView) {
        modelAndView.setViewName("adminDrinks/drinks-create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createDrinks(@ModelAttribute Drinks drinks, ModelAndView modelAndView) {
        if (drinks != null) {
            drinks.setDishType(DishType.DRINKS);
            drinksService.save(drinks);
            modelAndView.setViewName("adminDrinks/drinks-create");
            LOGGER.info("New drinks {} created", drinks.getName());
        } else {
            LOGGER.error("Error while creating new drinks");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/delete-update")
    public ModelAndView getListDrinks(ModelAndView modelAndView) {
        List<DrinksMainInfoDto> drinksInfo = drinksService.getDrinksInfo();
        modelAndView.addObject("drinks", drinksInfo);
        modelAndView.setViewName("adminDrinks/drinks-delete-update");
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDrinks(@PathVariable final String id, ModelAndView modelAndView) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while deletion drinks");
            return "redirect:http://localhost:8080/error";
        }
        drinksService.deleteById(id);
        LOGGER.info("Drinks with id {} was deleted", id);
        return "redirect:http://localhost:8080/admin/drinks/delete-update";
    }

    @GetMapping("/update/{id}")
    public ModelAndView getDrinksUpdate(@PathVariable final String id, ModelAndView modelAndView) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while updating drinks");
            modelAndView.setViewName("error");
        } else {
            Optional<Drinks> drinksById = drinksService.getById(id);
            drinksById.ifPresent(drinks -> modelAndView.addObject("drinks", drinks));
            modelAndView.setViewName("adminDrinks/drinks-update");
        }
        return modelAndView;
    }

    @PutMapping("/update")
    public String deleteDrinksByName(@ModelAttribute Drinks drinks) {
        if (drinks != null) {
            drinks.setDishType(DishType.DRINKS);
            drinksService.save(drinks);
            LOGGER.info("Drinks with id {} was updated", drinks.getId());
            return "redirect:http://localhost:8080/admin/drinks/delete-update";
        } else {
            LOGGER.error("Error while updating drinks");
            return "redirect:http://localhost:8080/error";
        }
    }


}


