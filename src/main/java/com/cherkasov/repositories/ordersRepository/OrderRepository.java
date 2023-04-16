package com.cherkasov.repositories.ordersRepository;

import com.cherkasov.dto.OrderMainInfoDto;
import com.cherkasov.models.dish.OwnPizza;
import com.cherkasov.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, String>,CustomizedOrderRepository {

    @Override
    <S extends Order> S saveAndFlush(S entity);

}
