package com.cherkasov.controllers;


import com.cherkasov.controllers.adminControllers.AdminDefaultPizzaController;
import com.cherkasov.dto.CreationOrderDto;
import com.cherkasov.dto.DishesFromCookieDto;
import com.cherkasov.models.order.Order;
import com.cherkasov.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping()
public class OrderController {
    private final OrderService orderService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminDefaultPizzaController.class);


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public ModelAndView getOrderPanel(HttpServletRequest request, ModelAndView modelAndView) {
        Cookie[] cookies = request.getCookies();
        DishesFromCookieDto dishes = orderService.getDishesFromCookies(cookies);
        modelAndView.addObject("dishes", dishes);
        modelAndView.setViewName("order/order");
        return modelAndView;
    }

    @GetMapping("/order/delete")
    public String DeleteDishFromOrder(@RequestParam String id, HttpServletRequest httpServletRequest,
                                      HttpServletResponse response) {
        if (id != null && !id.isEmpty()) {
            Cookie cookie = orderService.createCookieForDeletion(httpServletRequest.getCookies(), id);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "redirect:http://localhost:8080/order";
        } else {
            return "redirect:http://localhost:8080/error";
        }
    }

    @PostMapping("/order/create")
    public ModelAndView createOrder(@ModelAttribute CreationOrderDto creationOrderDto,
                                    Principal principal, ModelAndView modelAndView) {
        if (creationOrderDto != null) {
            Order order = orderService.createOrder(creationOrderDto, principal);
            modelAndView.addObject(order);
            modelAndView.setViewName("order/succes");
            LOGGER.info("New order created");
        } else {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/menu/pizza/add-to-order")
    public String addDefaultPizzaToOrder(@RequestParam String id, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        Cookie cookie = orderService.createCookieForOrder(httpServletRequest.getCookies(), id, "OrderDefaultPizza");
        cookie.setPath("/");
        cookie.setMaxAge(1800);
        response.addCookie(cookie);
        return "redirect:http://localhost:8080/menu/pizza/";
    }

    @GetMapping("/menu/drinks/add-to-order")
    public String addDrinksToOrder(@RequestParam String id, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        if (id != null && !id.isEmpty()) {
            Cookie cookie = orderService.createCookieForOrder(httpServletRequest.getCookies(), id, "OrderDrinks");
            cookie.setPath("/");
            cookie.setMaxAge(1800);
            response.addCookie(cookie);
            return "redirect:http://localhost:8080/menu/drinks/non-alcohol";
        } else {
            return "redirect:http://localhost:8080/menu/error/";
        }
    }

    @GetMapping("/menu/construction/created/add-to-order")
    public String addOwnPizzaToOrder(@RequestParam String id, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        if (id != null && !id.isEmpty()) {
            Cookie cookie = orderService.createCookieForOrder(httpServletRequest.getCookies(), id, "OrderOwnPizza");
            cookie.setPath("/");
            cookie.setMaxAge(1800);
            response.addCookie(cookie);
            return "redirect:http://localhost:8080/menu";
        } else {
            return "redirect:http://localhost:8080/menu/error/";
        }
    }

}
