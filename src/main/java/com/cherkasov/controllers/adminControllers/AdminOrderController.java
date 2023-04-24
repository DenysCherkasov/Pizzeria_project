package com.cherkasov.controllers.adminControllers;

import com.cherkasov.dto.OrderMainInfoDto;
import com.cherkasov.dto.OrderStatisticDto;
import com.cherkasov.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
@PreAuthorize("hasAuthority('MANAGER')")
public class AdminOrderController {
    private final OrderService orderService;
    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ModelAndView getPanelUser(ModelAndView modelAndView) {
        modelAndView.setViewName("adminOrder/order");
        return modelAndView;
    }

    @GetMapping("/all-orders")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<OrderMainInfoDto> ordersInfo = orderService.getOrdersInfo();
        modelAndView.addObject("orders", ordersInfo);
        modelAndView.setViewName("adminOrder/all-orders");
        return modelAndView;
    }

    @GetMapping("/statistic")
    public ModelAndView getStatisticForm(ModelAndView modelAndView) {
        modelAndView.setViewName("adminOrder/statistic");
        return modelAndView;
    }

    @GetMapping("/statistic/get-statistic")
    public ModelAndView getStatistic(@RequestParam(defaultValue = "1") int days, ModelAndView modelAndView) {
        Optional<OrderStatisticDto> statistic = orderService.getStatistic(days);
        statistic.ifPresent(orderStatisticDto ->
                modelAndView.addObject("statistics", orderStatisticDto));
        modelAndView.setViewName("adminOrder/statistic");
        return modelAndView;
    }

    @GetMapping("/search{email}")
    public ModelAndView findOrdersByUserEmail(@RequestParam String email, ModelAndView modelAndView) {
        if (email != null && !email.isEmpty()) {
            List<OrderMainInfoDto> ordersInfo = orderService.findOrdersByUserEmail(email);
            modelAndView.addObject("orders", ordersInfo);
            modelAndView.setViewName("adminOrder/all-orders");
        } else {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

}




