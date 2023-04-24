package com.cherkasov.repositories.ordersRepository;

import com.cherkasov.dto.OrderMainInfoDto;
import com.cherkasov.dto.OrderStatisticDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomizedOrderRepositoryImpl implements CustomizedOrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderMainInfoDto> getOrdersInfo() {
        return entityManager.createQuery(
                        """
                                SELECT new com.cherkasov.dto.OrderMainInfoDto
                                (o.id, o.dateCreation, o.price, COUNT(d),
                                p.receiverName, p.receiverPhone, p.deliveryTime, p.deliveryAddress)
                                FROM com.cherkasov.models.order.Order o
                                JOIN o.dishes d
                                JOIN o.delivery p
                                GROUP BY o.id, o.dateCreation, o.price, p.receiverName, p.receiverPhone, p.deliveryTime, p.deliveryAddress
                                ORDER BY o.dateCreation DESC""",
                        OrderMainInfoDto.class)
                .getResultList();
    }

    @Override
    public List<OrderMainInfoDto> findOrdersByUserId(String id) {
        String query = String.format("""
                SELECT new com.cherkasov.dto.OrderMainInfoDto 
                (o.id, o.dateCreation, o.price, COUNT(d), 
                p.receiverName, p.receiverPhone, p.deliveryTime, p.deliveryAddress)
                FROM Order o
                JOIN o.dishes d
                JOIN o.delivery p 
                WHERE o.user.id = '%s'
                GROUP BY o.id, o.dateCreation, o.price, p.receiverName, p.receiverPhone, p.deliveryTime, p.deliveryAddress
                ORDER BY o.dateCreation DESC""", id);
        return entityManager.createQuery(query, OrderMainInfoDto.class)
                .getResultList();
    }

    @Override
    public Optional<OrderStatisticDto> getStatistic(LocalDate date) {
        String query = "SELECT new com.cherkasov.dto.OrderStatisticDto " +
                "(COUNT(o.id), SUM(o.price)) " +
                "FROM Order o " +
                "WHERE o.dateCreation >= :date";
        return entityManager.createQuery(query, OrderStatisticDto.class)
                .setParameter("date", date)
                .getResultStream().findAny();
    }


}
