package com.cherkasov.controllers.adminControllers;

import com.cherkasov.dto.IngredientMainInfoDto;
import com.cherkasov.models.pizzaParts.Ingredient;
import com.cherkasov.models.pizzaParts.PizzaPartsType;
import com.cherkasov.services.IngredientService;
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
@RequestMapping("/admin/ingredient")
@PreAuthorize("hasAuthority('MANAGER')")
public class AdminIngredientController {
    private final IngredientService ingredientService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDefaultPizzaController.class);

    @Autowired
    public AdminIngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ModelAndView getPanelIngredient(ModelAndView modelAndView) {
        modelAndView.setViewName("adminIngredient/ingredient");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getPanelIngredientCreation(ModelAndView modelAndView) {
        modelAndView.setViewName("adminIngredient/ingredient-create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createIngredient(@ModelAttribute Ingredient ingredient, ModelAndView modelAndView) {
        if (ingredient != null) {
            ingredient.setPizzaPartsType(PizzaPartsType.INGREDIENT);
            ingredientService.save(ingredient);
            LOGGER.info("New ingredient {} created", ingredient.getName());
            modelAndView.setViewName("adminIngredient/ingredient-create");
        } else {
            LOGGER.error("Error while creating new ingredient");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/delete-update")
    public ModelAndView getListIngredient(ModelAndView modelAndView) {
        List<IngredientMainInfoDto> ingredientInfo = ingredientService.getIngredientInfo();
        modelAndView.addObject("ingredients", ingredientInfo);
        modelAndView.setViewName("adminIngredient/ingredient-delete-update");
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable final String id) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while deletion ingredient");
            return "redirect:http://localhost:8080/error";
        }
        ingredientService.deleteById(id);
        LOGGER.info("Ingredient with id {} was deleted", id);
        return "redirect:http://localhost:8080/admin/ingredient/delete-update";
    }

    @GetMapping("/update/{id}")
    public ModelAndView getIngredientUpdate(@PathVariable final String id, ModelAndView modelAndView) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("Error while updating ingredient");
            modelAndView.setViewName("error");
        } else {
            Optional<Ingredient> ingredientById = ingredientService.getById(id);
            ingredientById.ifPresent(ingredient -> modelAndView.addObject("ingredient", ingredient));
            modelAndView.setViewName("adminIngredient/ingredient-update");
        }
        return modelAndView;
    }

    @PutMapping("/update")
    public String deleteIngredientByName(@ModelAttribute Ingredient ingredient) {
        if (ingredient != null) {
            ingredient.setPizzaPartsType(PizzaPartsType.INGREDIENT);
            ingredientService.save(ingredient);
            LOGGER.info("Ingredient with id {} was updated", ingredient.getId());
            return "redirect:http://localhost:8080/admin/ingredient/delete-update";
        } else {
            LOGGER.error("Error while updating ingredient");
            return "redirect:http://localhost:8080/error";
        }
    }


}