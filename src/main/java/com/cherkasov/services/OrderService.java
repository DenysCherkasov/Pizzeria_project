package com.cherkasov.services;

import com.cherkasov.dto.CreationOrderDto;
import com.cherkasov.dto.DishesFromCookieDto;
import com.cherkasov.dto.OrderMainInfoDto;
import com.cherkasov.dto.OrderStatisticDto;
import com.cherkasov.models.delivery.Delivery;
import com.cherkasov.models.dish.DefaultPizza;
import com.cherkasov.models.dish.Dish;
import com.cherkasov.models.dish.Drinks;
import com.cherkasov.models.dish.OwnPizza;
import com.cherkasov.models.order.Order;
import com.cherkasov.models.user.User;
import com.cherkasov.repositories.OwnPizzaRepository;
import com.cherkasov.repositories.defaultPizzaRepository.DefaultPizzaRepository;
import com.cherkasov.repositories.drinksRepository.DrinksRepository;
import com.cherkasov.repositories.ordersRepository.OrderRepository;
import com.cherkasov.repositories.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DefaultPizzaRepository defaultPizzaRepository;
    private final DrinksRepository drinksRepository;
    private final OwnPizzaRepository ownPizzaRepository;

    private final UserRepository userRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, DefaultPizzaRepository defaultPizzaRepository,
                        DrinksRepository drinksRepository, OwnPizzaRepository ownPizzaRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.defaultPizzaRepository = defaultPizzaRepository;
        this.drinksRepository = drinksRepository;
        this.ownPizzaRepository = ownPizzaRepository;
        this.userRepository = userRepository;
    }

    public void save(final Order order) {
        orderRepository.save(order);
    }

    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> getById(final String id) {
        return orderRepository.findById(id);
    }

    public DishesFromCookieDto getDishesFromCookies(final Cookie[] cookies) {
        List<DefaultPizza> defaultPizzaList = new ArrayList<>();
        List<OwnPizza> ownPizzaList = new ArrayList<>();
        List<Drinks> drinksList = new ArrayList<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("OrderDefaultPizza")) {
                    defaultPizzaRepository.findById(cookie.getValue())
                            .ifPresent(defaultPizzaList::add);
                }
                if (cookie.getName().startsWith("OrderOwnPizza")) {
                    ownPizzaRepository.findById(cookie.getValue())
                            .ifPresent(ownPizzaList::add);
                }
                if (cookie.getName().startsWith("OrderDrinks")) {
                    drinksRepository.findById(cookie.getValue())
                            .ifPresent(drinksList::add);
                }
            }
        }
        int price = sumPrices(defaultPizzaList, ownPizzaList, drinksList);
        return new DishesFromCookieDto(defaultPizzaList, ownPizzaList, drinksList, price);
    }

    private int sumPrices(final List<DefaultPizza> defaultPizzaList,
                          final List<OwnPizza> ownPizzaList, final List<Drinks> drinksList) {
        return Stream.concat(Stream.concat(defaultPizzaList.stream(),
                        ownPizzaList.stream()), drinksList.stream())
                .mapToInt(Dish::getPrice)
                .sum();
    }

    public Cookie createCookieForDeletion(final Cookie[] cookies, final String id) {
        String cookieName = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contains(id)) {
                    cookieName = cookie.getName();
                }
            }
        }
        return new Cookie(cookieName, id);
    }

    public Cookie createCookieForOrder(final Cookie[] cookies, final String id, final String dishType) {
        int count = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().contains(id)) {
                    count++;
                }
            }
        }
        String cookieName = String.format("%s_v.%d_%s", dishType, count, id);
        return new Cookie(cookieName, id);
    }

    public Order createOrder(final CreationOrderDto creationOrderDto, final Principal principal) {
        Order order = new Order();
        order.setPrice(creationOrderDto.getPrice());
        List<Dish> dishes = createDishes(creationOrderDto.getDefaultPizzas(),
                creationOrderDto.getOwnPizzas(), creationOrderDto.getDrinks());
        order.setDishes(dishes);
        String dateTimeDelivery = creationOrderDto.getDeliveryTime().replace("T", " ");
        order.setDelivery(new Delivery(creationOrderDto.getReceiverName(),
                creationOrderDto.getReceiverPhone(), creationOrderDto.getDeliveryAddress(),
                dateTimeDelivery));
        if (principal != null && principal.getName() != null) {
            userRepository.findUserByEmail(principal.getName()).ifPresent(order::setUser);
        }
        order.setDateCreation(LocalDate.now());
        return orderRepository.saveAndFlush(order);
    }

    private List<Dish> createDishes(final List<DefaultPizza> defaultPizzas, final List<OwnPizza> ownPizzas, final List<Drinks> drinks) {
        return Stream.concat(Stream.concat(defaultPizzas.stream(),
                        ownPizzas.stream()), drinks.stream())
                .toList();
    }

    public List<OrderMainInfoDto> getOrdersInfo() {
        return orderRepository.getOrdersInfo();
    }

    public Optional<OrderStatisticDto> getStatistic(final int days) {
        LocalDate date = LocalDate.now().minusDays(days);
        return orderRepository.getStatistic(date);
    }


    public List<OrderMainInfoDto> findOrdersByUserEmail(final String email) {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        String id = null;
        if (userByEmail.isPresent()) {
            id = userByEmail.get().getId();
        }
        return orderRepository.findOrdersByUserId(id);
    }

}
