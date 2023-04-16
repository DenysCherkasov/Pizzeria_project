package com.cherkasov.controllers;

import com.cherkasov.controllers.adminControllers.AdminDefaultPizzaController;
import com.cherkasov.models.user.User;
import com.cherkasov.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDefaultPizzaController.class);

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ModelAndView getRegistrationPanel(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration/registration");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView registrationUser(@ModelAttribute User user, ModelAndView modelAndView) {
        if (user != null) {
            userService.save(user);
            modelAndView.setViewName("index");
            LOGGER.info("User with email {} have registered", user.getEmail());
        } else {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }


}
