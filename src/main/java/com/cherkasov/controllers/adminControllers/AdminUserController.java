package com.cherkasov.controllers.adminControllers;

import com.cherkasov.dto.UserMainInfoDto;
import com.cherkasov.models.user.User;
import com.cherkasov.services.UserService;
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
@RequestMapping("/admin/user")
@PreAuthorize("hasAuthority('MANAGER')")
public class AdminUserController {
    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDefaultPizzaController.class);

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getPanelUser(ModelAndView modelAndView) {
        modelAndView.setViewName("adminUser/user");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView getListUsers(ModelAndView modelAndView) {
        List<UserMainInfoDto> userInfo = userService.getUsersInfo();
        modelAndView.addObject("users", userInfo);
        modelAndView.setViewName("adminUser/all-users");
        return modelAndView;
    }

    @PutMapping("/users")
    public String changeUserRole(@ModelAttribute User user) {
        if (user != null && user.getRole() != null && user.getId() != null) {
            userService.setTypeById(user.getId(), user.getRole());
        }
        return "redirect:http://localhost:8080/admin/user/users";

    }

    @GetMapping("/search/{email}")
    public ModelAndView findUser(@RequestParam String email, ModelAndView modelAndView) {
        if (email != null && !email.isEmpty()) {
            Optional<User> userInfo = userService.findUserInfoByEmail(email);
            if (userInfo.isPresent()) {
                User user = userInfo.get();
                modelAndView.addObject("user", user);
                modelAndView.setViewName("adminUser/result-search");
                return modelAndView;
            }
        }
        LOGGER.error("Error while searching the user");
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @PutMapping("/search")
    public String changeRole(@ModelAttribute User user) {
        if (user != null && user.getRole() != null && user.getId() != null) {
            userService.setTypeById(user.getId(), user.getRole());
            LOGGER.info("Role of user with email {} was changed", user.getEmail());
        }
        return "redirect:http://localhost:8080/admin/user/users";
    }
}
