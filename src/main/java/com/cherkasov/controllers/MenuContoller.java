package com.cherkasov.controllers;


import com.cherkasov.dto.CreationPizzaDto;
import com.cherkasov.dto.DefaultPizzaMainInfoDto;
import com.cherkasov.dto.DrinksMainInfoForMenuDto;
import com.cherkasov.models.dish.OwnPizza;
import com.cherkasov.models.pizzaParts.Ingredient;
import com.cherkasov.models.pizzaParts.Souse;
import com.cherkasov.models.user.User;
import com.cherkasov.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/menu")
public class MenuContoller {

    private final DefaultPizzaService defaultPizzaService;
    private final DrinksService drinksService;
    private final OwnPizzaService ownPizzaService;
    private final SouseService souceService;
    private final IngredientService ingredientService;
    private final UserService userService;
    private static final int PAGE_SIZE = 8;

    @Autowired
    public MenuContoller(DefaultPizzaService defaultPizzaService,
                         DrinksService drinksService, SouseService souceService,
                         IngredientService ingredientService, OwnPizzaService ownPizzaService, UserService userService) {
        this.defaultPizzaService = defaultPizzaService;
        this.drinksService = drinksService;
        this.souceService = souceService;
        this.ingredientService = ingredientService;
        this.ownPizzaService = ownPizzaService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getMenu(ModelAndView modelAndView) {
        List<DefaultPizzaMainInfoDto> pizzas =
                defaultPizzaService.getDefaultPizzaInfoForMenu();
        modelAndView.addObject("pizzas", pizzas);
        modelAndView.setViewName("menu/general");
        return modelAndView;
    }

    @GetMapping("/pizza")
    public ModelAndView getMenuPizza(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "name") String order,
                                     @RequestParam(defaultValue = "asc") String dir,
                                     ModelAndView modelAndView) {
        List<DefaultPizzaMainInfoDto> pizzas;
        int totalPizzas;
        pizzas = defaultPizzaService.getSortedDefaultPizzaInfoForMenu(order, dir);
        totalPizzas = pizzas.size();
        int startIndex = (page - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalPizzas);
        List<DefaultPizzaMainInfoDto> pizzasForPage = pizzas.subList(startIndex, endIndex);
        modelAndView.addObject("pizzas", pizzasForPage);
        modelAndView.addObject("totalPages", (int) Math.ceil((double) totalPizzas / PAGE_SIZE));
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("order", order);
        modelAndView.addObject("dir", dir);
        modelAndView.setViewName("menu/pizza");
        return modelAndView;
    }

    @GetMapping("/pizza/{id}")
    public ModelAndView getPizzaById(@PathVariable String id, ModelAndView modelAndView) {
            defaultPizzaService.getDefaultPizzaInfoById(id).ifPresentOrElse(
                    pizza -> {
                        modelAndView.addObject("pizza", pizza);
                        modelAndView.setViewName("menu/pizza-by-id");
                    },
                    () -> modelAndView.setViewName("error"));
        return modelAndView;
    }

    @GetMapping("/drinks/check")
    public String checkAgeUser(HttpServletRequest httpServletRequest, HttpServletResponse response,
                               Principal principal) {
        int ageUser = userService.ageUserByCookie(httpServletRequest.getCookies());
        if (ageUser >= 18) {
            return "redirect:http://localhost:8080/menu/drinks/alcohol";
        } else if (ageUser != 0) {
            return "redirect:http://localhost:8080/menu/drinks/forbidden";
        } else {
            if (principal == null) {
                return "redirect:http://localhost:8080/menu/drinks/check-age";
            } else {
                Optional<User> userByInfo = userService.findUserInfoByEmail(principal.getName());
                if (userByInfo.isPresent()) {
                    int age = userByInfo.get().getAge();
                    response.addCookie(userService.createAgeCookie(age));
                    if (age >= 18) {
                        return "redirect:http://localhost:8080/menu/drinks/alcohol";
                    } else {
                        return "redirect:http://localhost:8080/menu/drinks/forbidden";
                    }
                } else {
                    return "redirect:http://localhost:8080/menu/drinks/check-age";
                }
            }
        }
    }

    @GetMapping("/drinks/check-age")
    public ModelAndView getCheckAgePanel(ModelAndView modelAndView) {
        modelAndView.setViewName("menu/check-age");
        return modelAndView;
    }

    @GetMapping("/drinks/check-age/result")
    public String getUserAge(@RequestParam int age, HttpServletResponse response) {
        response.addCookie(userService.createAgeCookie(age));
        if (age >= 18) {
            return "redirect:http://localhost:8080/menu/drinks/alcohol";
        } else {
            return "redirect:http://localhost:8080/menu/drinks/forbidden";
        }
    }

    @GetMapping("/drinks/forbidden")
    public ModelAndView accessForbidden(ModelAndView modelAndView) {
        modelAndView.setViewName("menu/forbidden");
        return modelAndView;
    }

    @GetMapping("/drinks/alcohol")
    public ModelAndView getMenuAlcoholDrinks(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "name") String order,
                                             @RequestParam(defaultValue = "asc") String dir,
                                             ModelAndView modelAndView) {
        List<DrinksMainInfoForMenuDto> drinks;
        int totalDrinks;
        drinks = drinksService.getSortedAlcoholDrinksInfoForMenu(order, dir);
        totalDrinks = drinks.size();
        int startIndex = (page - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalDrinks);
        List<DrinksMainInfoForMenuDto> drinksForPage = drinks.subList(startIndex, endIndex);
        modelAndView.addObject("drinks", drinksForPage);
        modelAndView.addObject("totalPages", (int) Math.ceil((double) totalDrinks / PAGE_SIZE));
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("order", order);
        modelAndView.addObject("dir", dir);
        modelAndView.setViewName("menu/alcohol-drinks");
        return modelAndView;
    }

    @GetMapping("/drinks/non-alcohol")
    public ModelAndView getMenuNonAlcoholDrinks(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "name") String order,
                                                @RequestParam(defaultValue = "asc") String dir,
                                                ModelAndView modelAndView) {
        List<DrinksMainInfoForMenuDto> drinks;
        int totalDrinks;
        drinks = drinksService.getSortedNonAlcoholDrinksInfoForMenu(order, dir);
        totalDrinks = drinks.size();
        int startIndex = (page - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalDrinks);
        List<DrinksMainInfoForMenuDto> drinksForPage = drinks.subList(startIndex, endIndex);
        modelAndView.addObject("drinks", drinksForPage);
        modelAndView.addObject("totalPages", (int) Math.ceil((double) totalDrinks / PAGE_SIZE));
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("order", order);
        modelAndView.addObject("dir", dir);
        modelAndView.setViewName("menu/non-alcohol-drinks");
        return modelAndView;
    }

    @GetMapping("/drinks/{id}")
    public ModelAndView getDrinksById(@PathVariable String id, ModelAndView modelAndView) {
            Optional.ofNullable(id).ifPresentOrElse(
                    ids -> drinksService.getDrinksInfoById(ids).ifPresentOrElse(
                            drinks -> {
                                modelAndView.addObject("drinks", drinks);
                                modelAndView.setViewName("menu/drinks-by-id");
                            },
                            () -> modelAndView.setViewName("error")),
                    () -> modelAndView.setViewName("error"));
        return modelAndView;
    }


    @GetMapping("/construction")
    public ModelAndView getConstructionPanel(ModelAndView modelAndView) {
        Iterable<Souse> souses = souceService.getAll();
        Iterable<Ingredient> ingredients = ingredientService.getAll();
        modelAndView.addObject("souses", souses);
        modelAndView.addObject("ingredients", ingredients);
        modelAndView.setViewName("menu/construction");
        return modelAndView;
    }

    @PostMapping("/construction/created")
    public ModelAndView createOwnPizza(@ModelAttribute CreationPizzaDto ownPizzaInfo, ModelAndView modelAndView) {
        if (ownPizzaInfo == null) {
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            OwnPizza ownPizza = ownPizzaService.save(ownPizzaInfo.getSize(),
                    ownPizzaInfo.getSouse(), ownPizzaInfo.getIngredients());
            modelAndView.addObject("pizza", ownPizza);
            modelAndView.setViewName("menu/created-pizza");
        }
        return modelAndView;
    }

}
